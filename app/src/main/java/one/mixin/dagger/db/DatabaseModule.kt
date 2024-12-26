package one.mixin.dagger.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import one.mixin.dagger.db.dao.MessageDao
import one.mixin.dagger.utils.UserComponentManager
import one.mixin.dagger.utils.dbDir
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        manager: UserComponentManager
    ): MixinDatabase {
        val userId = manager.getUser()?.name ?: "temp"
        return Room.databaseBuilder(
            context,
            MixinDatabase::class.java,
            File(dbDir(context, userId), "mixin.db").absolutePath
        ).build()
    }

    @Provides
    fun provideMessageDao(database: MixinDatabase): MessageDao {
        return database.messageDao()
    }
}