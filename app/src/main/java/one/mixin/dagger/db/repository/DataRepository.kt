package one.mixin.dagger.db.repository

import kotlinx.coroutines.flow.Flow
import one.mixin.dagger.db.dao.MessageDao
import one.mixin.dagger.db.entity.Message
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val messageDao: MessageDao
){
    fun getAllMessages(): Flow<List<Message>> {
        return  messageDao.getAllMessages()
    }

    suspend fun insertMessage(message: Message){
        messageDao.insertMessage(message)
    }
}