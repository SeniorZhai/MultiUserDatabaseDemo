package one.mixin.dagger.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import one.mixin.dagger.data.AppDatabase
import one.mixin.dagger.data.DatabaseProvider
import one.mixin.dagger.data.entity.Message
import one.mixin.dagger.data.entity.User
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: AppDatabase,
    private val databaseProvider: DatabaseProvider
) : ViewModel() {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser
    
    val messages = _currentUser.flatMapLatest { user ->
        user?.let { database.messageDao().getMessagesByUserId(it.id) }
            ?: kotlinx.coroutines.flow.flowOf(emptyList())
    }
    
    init {
        viewModelScope.launch {
            _currentUser.value = database.userDao().getCurrentUser()
        }
    }
    
    fun insertRandomMessages() {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val randomMessages = (1..10).map {
                    Message(
                        content = "随机消息 ${Random.nextInt(1000)}",
                        userId = user.id
                    )
                }
                database.messageDao().insertMessages(randomMessages)
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            databaseProvider.closeDatabase()
        }
    }
}