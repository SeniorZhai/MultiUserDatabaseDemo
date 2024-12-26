package one.mixin.dagger.network

import retrofit2.http.GET

interface ApiService {
    @GET("/todos/1")  // 示例 API
    suspend fun getTodo(): String
}