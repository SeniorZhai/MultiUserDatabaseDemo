package one.mixin.dagger.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String
)