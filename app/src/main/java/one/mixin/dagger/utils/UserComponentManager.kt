package one.mixin.dagger.utils

import one.mixin.dagger.db.entity.User
import one.mixin.dagger.di.UserComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserComponentManager @Inject constructor(
    private val builder: UserComponent.Builder,
    private val preferencesHelper: PreferencesHelper
) {

    companion object {
        lateinit var instance: UserComponentManager
    }

    @Volatile
    private var userComponent: UserComponent? = null

    @Volatile
    private var currentUser : User? = null

    init {
        currentUser = preferencesHelper.getUser()
        currentUser?.let{
            userComponent = builder.feedUser(it).build()
        }
    }

    fun onLogin(user: User) {
        currentUser = user
        userComponent = builder.feedUser(user).build()
        preferencesHelper.saveUser(user)
    }

    fun onLogout() {
        currentUser = null
        userComponent = builder.feedUser(null).build()
        preferencesHelper.clearUser()
    }

    fun generatedComponent(): UserComponent? {
        return userComponent
    }

    fun getUser() : User? {
        return  currentUser
    }

    fun isLogin(): Boolean = userComponent != null
}
