package one.mixin.dagger.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import one.mixin.dagger.db.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("mixin_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: User?) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString("user", userJson).apply()
    }

    fun getUser(): User? {
        val userJson = sharedPreferences.getString("user", null)
        return gson.fromJson(userJson, User::class.java)
    }

    fun clearUser() {
        sharedPreferences.edit().remove("user").apply()
    }
}