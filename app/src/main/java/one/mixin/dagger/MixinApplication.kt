package one.mixin.dagger

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import one.mixin.dagger.utils.UserComponentManager
import javax.inject.Inject

@HiltAndroidApp
class MixinApplication : Application() {

    @Inject
    lateinit var userComponentManager: UserComponentManager

    override fun onCreate() {
        super.onCreate()
        UserComponentManager.instance = userComponentManager
    }
}