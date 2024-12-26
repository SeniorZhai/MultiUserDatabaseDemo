package one.mixin.dagger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import one.mixin.dagger.db.MixinDatabase
import one.mixin.dagger.utils.UserComponentManager

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {
    @Provides
    fun provideDatabase(
        manager: UserComponentManager,
        @ApplicationContext context: Context
    ): MixinDatabase? {
        manager.generatedComponent() ?: return null

        return EntryPointAccessors.fromApplication(context, UserEntryPoint::class.java)
            .provideDatabase()
    }
}