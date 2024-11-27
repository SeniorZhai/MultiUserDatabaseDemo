package one.mixin.dagger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import one.mixin.dagger.data.entity.Message

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: Message): Long
    
    @Query("SELECT * FROM messages WHERE user_id = :userId ORDER BY id DESC")
    fun getMessagesByUserId(userId: Long): Flow<List<Message>>
    
    @Insert
    suspend fun insertMessages(messages: List<Message>)
} 