# Android Multi-User DB

一个演示 Android 多用户数据库隔离的示例项目。每个用户都拥有独立的数据库实例，实现了完全的数据隔离。

## 项目说明

这个项目演示了如何在 Android 应用中实现多用户数据库隔离。每个用户都有自己独立的数据库文件，存储在独立的目录中，确保数据完全隔离。

主要解决的问题：
1. 多用户数据隔离需求
2. 动态数据库切换
3. 内存优化（只保持当前用户的数据库连接）
4. 安全的数据库管理

## 核心特性

- **多用户数据隔离**: 
  - 每个用户独立的数据库目录
  - 独立的数据库文件
  - 完全的数据隔离

- **动态数据库切换**: 
  - 使用 Hilt 实现依赖注入
  - 登录时自动切换数据库实例
  - 安全的数据库关闭机制

- **现代技术栈**: 
  - Jetpack Compose UI
  - Hilt 依赖注入
  - Room 数据库
  - Kotlin Coroutines & Flow
  - MVVM 架构

## 实现原理

1. **数据库隔离**
   - 在应用私有目录下为每个用户创建独立目录
   - 数据库文件存储在用户专属目录中
   - 路径格式：`/data/data/one.mixin.dagger/database/<username>/user.db`

2. **数据库管理**
   - DatabaseProvider 负责数据库生命周期管理
   - 使用 Hilt 注入实现依赖管理
   - 支持动态切换数据库实例

3. **用户系统**
   - 简单的用户名登录机制
   - 用户信息持久化
   - 支持用户消息管理

4. **消息系统**
   - 每个用户独立的消息列表
   - 支持批量消息插入
   - 使用 Flow 实现实时更新

## 项目结构

### 数据库文件结构

## 数据库切换与重新注入实现

### 1. 核心组件

#### DatabaseProvider

```kotlin
class DatabaseProvider @Inject constructor(context: Context) {
    private var currentDatabase: AppDatabase? = null
    
    @Synchronized
    fun initDatabase(username: String) {
        currentDatabase?.close()
        val baseDir = File(context.filesDir.parent, "database")
        val userDir = File(baseDir, username)
        userDir.mkdirs()
        currentDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            userDir.absolutePath + File.separator + "user.db"
        ).build()
    }
}
//DatabaseModule
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabaseProvider(
        @ApplicationContext context: Context
    ): DatabaseProvider

    @Provides
    fun provideAppDatabase(
        databaseProvider: DatabaseProvider
    ): AppDatabase
}

//登录时切换数据库
class LoginViewModel @Inject constructor(
    private val databaseProvider: DatabaseProvider
) : ViewModel() {
    fun login(username: String) {
        viewModelScope.launch {
            databaseProvider.initDatabase(username)
            // 后续操作...
        }
    }
}

class HomeViewModel @Inject constructor(
    private val database: AppDatabase
) : ViewModel() {
    val messages = database.messageDao()
        .getMessagesByUserId(userId)
        .stateIn(viewModelScope, ...)
}
```

### 2. 工作流程

1. 初始启动
    - DatabaseProvider 作为单例被创建
    - 初始状态无活跃数据库连接

2. 用户登录
    - 调用 initDatabase() 创建新数据库
    - 旧数据库连接自动关闭
    - 新数据库实例被创建

3. 数据库使用
    - 通过 Hilt 注入 AppDatabase
    - 每次注入都获取当前活跃的数据库实例
    - ViewModel 中直接使用注入的数据库

4.切换用户
    - 退出时关闭当前数据库
    - 登录新用户时创建新数据库
    - 所有注入点自动获取新数据库

### 3. 关键实现细节

1. 单例与非单例
    - DatabaseProvider: 单例，管理数据库生命周期
    - AppDatabase: 非单例，每次注入获取当前实例

2. 线程安全
    - 关键方法使用 @Synchronized
    - 数据库操作在协程中执行
    - 安全的数据库切换机制

3. 内存管理
    - 及时关闭旧数据库连接
    - 避免数据库连接泄漏
    - 单一活跃数据库实例