package one.mixin.dagger.db.repository

import one.mixin.dagger.db.dao.MessageDao
import one.mixin.dagger.db.entity.Message
import one.mixin.dagger.network.ApiService
import one.mixin.dagger.preference.UserPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun getMessagesByUserId(userId: Long): List<Message> {
        return messageDao.getAllMessagesByUserId(userId)
    }

    suspend fun insertMessage(message: Message) {
        messageDao.insert(message)
    }

    suspend fun addMessages() :String{
        // return apiService.getTodo()
        for (i in 1..(1..100).random()) {
            val message = Message(
                userId = getUserId() ?: return "User not logged in",
                content = "Random message $i",
            )
            messageDao.insert(message)
        }
        return "messages added"
    }

     fun saveUserId(userId: Long) {
         userPreference.saveUserId(userId)
     }

    fun getUserId(): Long? {
        return userPreference.getUserId()
    }

    fun clearUserId(){
         userPreference.clearUserId()
     }
}