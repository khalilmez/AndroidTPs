package com.nicoalex.todo.network

import com.nicoalex.todo.User.LoginForm
import com.nicoalex.todo.User.LoginResponse
import com.nicoalex.todo.User.SignUpForm
import com.nicoalex.todo.User.SignUpResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserWebService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>
    @Multipart
    @PATCH("users/update_avatar")
    suspend fun updateAvatar(@Part avatar: MultipartBody.Part): Response<UserInfo>
    @PATCH("users")
    suspend fun update(@Body user: UserInfo): Response<UserInfo>
    @POST("users/login")
    suspend fun login(@Body user: LoginForm): Response<LoginResponse>
    @POST("users/sign_up")
    suspend fun signUp(@Body user: SignUpForm): Response<SignUpResponse>

}