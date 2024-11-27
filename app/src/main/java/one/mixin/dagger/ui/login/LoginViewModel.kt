package one.mixin.dagger.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import one.mixin.dagger.data.DatabaseProvider
import one.mixin.dagger.data.entity.User
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val databaseProvider: DatabaseProvider
) : ViewModel() {
    
    fun login(username: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                databaseProvider.initDatabase(username)
                val existingUser = databaseProvider.getDatabase().userDao().getUserByName(username)
                
                if (existingUser == null) {
                    val user = User(userName = username)
                    databaseProvider.getDatabase().userDao().insertUser(user)
                }
                
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            databaseProvider.closeDatabase()
        }
    }
}