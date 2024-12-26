package one.mixin.dagger.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import one.mixin.dagger.db.MixinDatabase

@EntryPoint
@InstallIn(UserComponent::class)
interface UserEntryPoint {
    fun provideDatabase(): MixinDatabase
}