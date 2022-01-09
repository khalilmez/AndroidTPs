package com.nicoalex.todo.User

import com.nicoalex.todo.network.Api
import com.nicoalex.todo.network.UserInfo
import okhttp3.MultipartBody

class UserInfoRepository {
    private val userWebService = Api.userWebService

    suspend fun refresh(): UserInfo? {
        var response = userWebService.getInfo()

        if(response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun updateAvatar(avatar: MultipartBody.Part): UserInfo? {
        var response = userWebService.updateAvatar(avatar)
        if(response.isSuccessful) {
            return response.body()
        }
        return null;
    }
    suspend fun updateUserInfo(user: UserInfo): UserInfo? {
        var response = userWebService.update(user)
        if(response.isSuccessful) {
            return response.body()
        }
        return null;
    }
}