package one.mixin.dagger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import one.mixin.dagger.data.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long
    
    @Query("SELECT * FROM users WHERE user_name = :userName LIMIT 1")
    suspend fun getUserByName(userName: String): User?
    
    @Query("SELECT * FROM users ORDER BY created_at DESC LIMIT 1")
    suspend fun getCurrentUser(): User?
} 