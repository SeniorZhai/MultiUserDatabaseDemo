package one.mixin.dagger.data.repository

import one.mixin.dagger.data.AppDatabase
import one.mixin.dagger.data.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun insertUser(user: User) = database.userDao().insertUser(user)
    
    suspend fun getUserByName(userName: String) = database.userDao().getUserByName(userName)
    
    suspend fun getCurrentUser() = database.userDao().getCurrentUser()
} 