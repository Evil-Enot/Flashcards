package com.example.flashcards.api

import com.example.flashcards.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebServices {

    // Регистрация пользователя
    @POST("restapi/v1/auth/register/")
    fun reg(
        @Body body: RegistrationRequest
    ): Call<AuthApiResponse>

    // Авторизация пользователя
    @POST("restapi/v1/auth/authenticate/")
    fun auth(
        @Body body: AuthRequest
    ): Call<AuthResponse>

    // Получение информации о пользователе
    @POST("restapi/v1/users/")
    fun getUser(
        @Body body: UserRequest
    ): Call<UserInfoResponse>

    // Получение групп пользователя
    @POST("restapi/v1/users/groups")
    fun getUserGroups(
        @Body body: UserRequest
    ): Call<UserGroupsResponse>

    // Получение истории пользователя
    @POST("restapi/v1/users/history")
    fun getUserHistory(
        @Body body: UserRequest
    ): Call<UserHistoryResponse>
}
