package one.mixin.dagger.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import one.mixin.dagger.db.entity.Message

@Dao
interface MessageDao {
    @Insert
    suspend fun insert(message: Message)
    @Query("SELECT * FROM messages WHERE user_id = :userId")
    suspend fun getAllMessagesByUserId(userId: Long): List<Message>
}