package one.mixin.dagger.data

import android.content.Context
import androidx.room.Room
import java.io.File
import javax.inject.Inject

class DatabaseProvider @Inject constructor(
    private val context: Context
) {
    private var currentDatabase: AppDatabase? = null
    
    @Synchronized
    fun getDatabase(): AppDatabase {
        return currentDatabase ?: throw IllegalStateException("数据库未初始化，请先登录")
    }
    
    @Synchronized
    fun initDatabase(username: String) {
        currentDatabase?.close()
        
        // 创建 database 目录
        val baseDir = File(context.filesDir.parent, "database")
        // 创建用户目录
        val userDir = File(baseDir, username)
        if (!userDir.exists()) {
            userDir.mkdirs()
        }
        
        // 在用户目录下创建数据库文件
        currentDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            userDir.absolutePath + File.separator + "user.db"
        ).build()
    }
    
    @Synchronized
    fun closeDatabase() {
        currentDatabase?.close()
        currentDatabase = null
    }
} 