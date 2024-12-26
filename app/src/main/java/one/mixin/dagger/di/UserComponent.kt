package one.mixin.dagger.di

import dagger.BindsInstance
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent
import one.mixin.dagger.db.entity.User

@UserScope
@DefineComponent(parent = SingletonComponent::class)
interface UserComponent {
    @DefineComponent.Builder
    interface Builder {
        fun feedUser(@BindsInstance user: User?): Builder
        fun build(): UserComponent
    }
}