package one.mixin.dagger.db.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import one.mixin.dagger.db.dao.MessageDao
import one.mixin.dagger.network.ApiService
import one.mixin.dagger.preference.UserPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providerDataRepository(
        messageDao: MessageDao,
        apiService: ApiService,
        userPreference: UserPreference
    ): DataRepository {
        return DataRepository(messageDao, apiService, userPreference)
    }
}