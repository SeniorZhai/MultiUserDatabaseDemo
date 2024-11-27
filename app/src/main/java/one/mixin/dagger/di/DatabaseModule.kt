package one.mixin.dagger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import one.mixin.dagger.data.AppDatabase
import one.mixin.dagger.data.DatabaseProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabaseProvider(
        @ApplicationContext context: Context
    ): DatabaseProvider {
        return DatabaseProvider(context)
    }
    
    @Provides
    fun provideAppDatabase(
        databaseProvider: DatabaseProvider
    ): AppDatabase {
        return databaseProvider.getDatabase()
    }
}