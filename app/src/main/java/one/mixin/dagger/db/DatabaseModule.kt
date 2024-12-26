package one.mixin.dagger.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import one.mixin.dagger.db.dao.MessageDao
import one.mixin.dagger.utils.dbDir
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "mixin.db"

     @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
     ): MixinDatabase {

         val dir = dbDir(context)
         val builder =
             Room.databaseBuilder(context, MixinDatabase::class.java, File(dir, DB_NAME).absolutePath)
         return builder.build()
     }


     @Provides
     fun provideMessageDao(database: MixinDatabase): MessageDao {
         return database.messageDao()
     }
 }