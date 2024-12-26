package one.mixin.dagger.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import one.mixin.dagger.db.entity.Message
import one.mixin.dagger.db.repository.DataRepository
import one.mixin.dagger.utils.UserComponentManager
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val userComponentManager: UserComponentManager,
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    fun isLogin() = userComponentManager.isLogin()

    fun loadMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getAllMessages().collect{
                _messages.value = it
            }
        }
    }

    fun insertMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO){
            dataRepository.insertMessage(Message(content = message))
        }
    }
}