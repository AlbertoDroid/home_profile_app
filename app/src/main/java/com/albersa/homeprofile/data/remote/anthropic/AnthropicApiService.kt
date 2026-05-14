package com.albersa.homeprofile.data.remote.anthropic

import retrofit2.http.Body
import retrofit2.http.POST

interface AnthropicApiService {
    @POST("v1/messages")
    suspend fun createMessage(@Body request: AnthropicRequest): AnthropicResponse
}
