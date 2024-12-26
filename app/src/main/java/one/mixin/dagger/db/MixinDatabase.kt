package one.mixin.dagger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import one.mixin.dagger.db.dao.MessageDao
import one.mixin.dagger.db.entity.Message
import one.mixin.dagger.db.entity.User

@Database(entities = [Message::class, User::class], version = 1)
abstract class MixinDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}