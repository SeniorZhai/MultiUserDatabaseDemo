package one.mixin.dagger.db.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import one.mixin.dagger.db.dao.MessageDao

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideDataRepository(messageDao: MessageDao) : DataRepository {
        return DataRepository(messageDao)
    }
}