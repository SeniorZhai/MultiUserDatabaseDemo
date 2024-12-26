package one.mixin.dagger.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
    }

    fun saveUserId(userId: Long) {
        prefs.edit {
            putLong(KEY_USER_ID, userId)
        }
    }

    fun getUserId(): Long? {
        val userId = prefs.getLong(KEY_USER_ID,-1L)
        return if (userId == -1L) null else userId
    }

    fun clearUserId(){
        prefs.edit {
            remove(KEY_USER_ID)
        }
    }
}