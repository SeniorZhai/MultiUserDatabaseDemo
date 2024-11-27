package one.mixin.dagger.data

import androidx.room.Database
import androidx.room.RoomDatabase
import one.mixin.dagger.data.dao.MessageDao
import one.mixin.dagger.data.dao.UserDao
import one.mixin.dagger.data.entity.Message
import one.mixin.dagger.data.entity.User

@Database(
    entities = [User::class, Message::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
}