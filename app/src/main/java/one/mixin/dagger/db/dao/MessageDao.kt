package one.mixin.dagger.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import one.mixin.dagger.db.entity.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM Message")
    fun getAllMessages(): Flow<List<Message>>

    @Insert
    suspend fun insertMessage(message: Message)
}