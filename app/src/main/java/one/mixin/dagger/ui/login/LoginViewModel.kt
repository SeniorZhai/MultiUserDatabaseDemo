package one.mixin.dagger.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import one.mixin.dagger.db.repository.DataRepository
import one.mixin.dagger.utils.Session
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    suspend fun login(identityNumber: Long): Boolean {
        try {
            Session.login(identityNumber)
            repository.saveUserId(identityNumber)
             return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}