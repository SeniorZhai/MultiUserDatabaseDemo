package one.mixin.dagger.data.repository

import one.mixin.dagger.data.AppDatabase
import one.mixin.dagger.data.entity.Message
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getMessagesByUserId(userId: Long) = database.messageDao().getMessagesByUserId(userId)
    
    suspend fun insertMessages(messages: List<Message>) = database.messageDao().insertMessages(messages)
} 