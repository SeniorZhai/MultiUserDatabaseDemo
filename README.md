# 多用户 Android 应用，使用 Hilt、Room、Retrofit 和 SharedPreferences

这个 Android 应用程序演示了一个多用户的设置，使用了 Hilt 进行依赖注入，Room 进行本地数据库存储，Retrofit 进行网络请求，以及 SharedPreferences 进行用户会话管理。每个用户都有一个独立的 Room 数据库，并且网络请求是在用户上下文之外执行的。

## 功能

*   **多用户支持:** 每个用户都有一个独立的 Room 数据库，确保数据隔离。
*   **用户认证:** 使用数字用户 ID 登录。
*   **数据持久化:** 使用 SharedPreferences 存储当前登录的用户 ID。
*   **本地数据库:** 使用 Room 存储用户特定的消息。
*   **网络请求:** 使用 Retrofit 和 OkHttp 进行网络请求。
*   **依赖注入:** 使用 Hilt 进行依赖注入。
*   **退出登录功能：** 用户可以退出登录并清除用户会话。

## 架构

该应用程序遵循清晰的架构模式：

*   **数据层 (Data Layer):**
    *   `data/db`: 包含 Room 数据库相关的类，包括实体类 (`Message`, `User`)，DAO (`MessageDao`)，以及数据库设置 (`MixinDatabase`)。
    *   `data/network`: 包含 Retrofit 和 OkHttp 相关的类，用于网络请求 (`ApiService`)。
    *   `data/preference`: 包含处理 SharedPreferences 的类，用于用户会话管理 (`UserPreference`)。
    *   `data/repository`: 提供一个统一的入口，用于访问来自不同来源的数据（本地数据库、网络、SharedPreferences）。
*   **UI 层 (UI Layer):**
    *   `ui/login`: 包含登录活动 (`LoginActivity`) 和它的 ViewModel (`LoginViewModel`)。
    *   `ui/main`: 包含主活动 (`MainActivity`) 和它的 ViewModel (`MainViewModel`)。
     *   `ui/components`: 包含自定义视图组件 (`MessageAdapter`)
*   **依赖注入层 (DI Layer):**
    *   `di`: 包含 Hilt 的模块，用于依赖注入。
*  **工具类 (Utils Layer):**
    *  `utils`: 包含帮助类，例如用于管理用户会话的 `Session`，以及扩展函数。

## 依赖

*   **Hilt:** 用于依赖注入。
*   **Room:** 用于本地数据库管理。
*   **Retrofit & OkHttp:** 用于网络请求。
*   **Kotlin 协程 (Coroutines):** 用于异步操作。
*   **Lifecycle:** 用于 ViewModel 的生命周期管理

## 设置

1.  **克隆仓库:**

    ```bash
    git clone [repository_url]
    ```

2.  **在 Android Studio 中打开:**

    在 Android Studio 中打开克隆的项目。

3.  **配置 Gradle:**

    确保你安装了必要的 SDK。该项目使用 Gradle 8.0 和 Kotlin 1.9。如果你遇到 Gradle 问题，请确保使用高于 7.0 的版本。

4.  **构建和运行:**

    在模拟器或物理设备上构建并运行项目。

## 如何使用

1.  **登录:**
    *   启动应用程序，你会被引导到登录页面。
    *   在输入框中输入一个数字用户 ID。
    *   点击 "登录" 按钮。
     *   首次登录，会创建新的用户数据库。

2.  **主页面:**
    *   如果登录成功，你将导航到主页面，其中会显示当前登录用户相关的消息。
    *   列表数据来源于用户特定的数据库，首次登录时会初始化 0 到 100 条测试消息。
    *   `fetch` 按钮用于请求一个模拟网络 API。

3.  **退出登录:**
      *   点击退出登录按钮，清除用户会话并返回登录页面。

## 数据库逻辑

*   **每个用户的数据库:** 每个用户都有一个独立的 Room 数据库，该数据库在用户首次登录时创建，或者在用户再次登录时打开。
*   **数据库目录:** 数据库存储在应用程序内部文件目录下的 `/databases` 目录中。
*   **文件路径生成:** 目录路径根据已登录的用户 ID 使用 `dbDir` 方法动态生成，并且用户ID存储在 sharedpreferences中
*   **消息存储:** `Message` 实体存储消息，并引用相应的 `User`。

## 关键实现细节

*   **Hilt 依赖注入:** 应用程序使用 Hilt 管理依赖。关键组件，例如 Room 数据库、Retrofit 客户端和仓库，都在需要的地方进行注入。
*   **用户会话管理:** `Session` 对象和 `UserPreference` 用于管理用户会话和持久化用户 ID。
*   **数据检索:** `DataRepository` 类提供了一个简化的接口，用于从不同的数据源获取数据。
*   **异步操作:** Kotlin 协程用于执行数据库和网络操作，以非阻塞的方式确保流畅的用户体验。
*   **OkHttp Logging 拦截器:** 添加到 OkHttpClient，可以在日志中查看 http 请求和响应。

## 进一步改进

*   **错误处理:** 添加更健壮的错误处理，以处理网络和数据库操作。
*   **UI 增强:** 改进用户界面以获得更好的体验。
*   **数据库迁移:** 为未来的模式更改实现数据库迁移。
*   **更好的加载状态:** 更准确地处理视图状态。
*   **添加单元测试:** 为 ViewModel 和仓库编写单元测试。

## 贡献

欢迎贡献！如果你有任何建议或改进，请随时提出 issue 或提交 pull request。