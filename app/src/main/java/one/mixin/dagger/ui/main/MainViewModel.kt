package one.mixin.dagger.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import one.mixin.dagger.db.entity.Message
import one.mixin.dagger.db.repository.DataRepository
import one.mixin.dagger.utils.Session
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    suspend fun loadMessages(): List<Message> {
       val userId =  repository.getUserId()
        if (userId == null) return emptyList()

        return repository.getMessagesByUserId(userId)
    }

    suspend fun addMessages(): String {
        return repository.addMessages()
    }


    suspend fun logout() {
        repository.clearUserId()
        Session.logout()
    }

    fun isLoggedIn(): Boolean {
        return repository.getUserId() != null
    }
}