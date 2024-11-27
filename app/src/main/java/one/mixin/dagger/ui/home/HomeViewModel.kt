package one.mixin.dagger.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import one.mixin.dagger.data.DatabaseProvider
import one.mixin.dagger.data.entity.Message
import one.mixin.dagger.data.entity.User
import one.mixin.dagger.data.repository.MessageRepository
import one.mixin.dagger.data.repository.UserRepository
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseProvider: DatabaseProvider,
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val username = checkNotNull(savedStateHandle.get<String>("username")) { "Username is required" }
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser
    
    init {
        viewModelScope.launch {
            var user = userRepository.getUserByName(username)
            if (user == null) {
                val newUser = User(userName = username)
                userRepository.insertUser(newUser)
                user = userRepository.getUserByName(username)
            }
            _currentUser.value = user
        }
    }
    
    val messages = _currentUser.flatMapLatest { user ->
        user?.let { messageRepository.getMessagesByUserId(it.id) }
            ?: kotlinx.coroutines.flow.flowOf(emptyList())
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
                messageRepository.insertMessages(randomMessages)
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            databaseProvider.closeDatabase()
        }
    }
}