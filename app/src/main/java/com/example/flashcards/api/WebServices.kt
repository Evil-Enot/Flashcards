package com.example.flashcards.api

import com.example.flashcards.model.Status
import com.example.flashcards.model.auth.AuthApiResponse
import com.example.flashcards.model.auth.AuthRequest
import com.example.flashcards.model.auth.AuthResponse
import com.example.flashcards.model.cards.CardsResponse
import com.example.flashcards.model.groups.GroupResponse
import com.example.flashcards.model.history.UserGroupsResponse
import com.example.flashcards.model.history.UserHistoryRequest
import com.example.flashcards.model.history.UserHistoryResponse
import com.example.flashcards.model.history.UserHistoryStatus
import com.example.flashcards.model.registration.RegistrationRequest
import com.example.flashcards.model.user.UserInfoResponse
import com.example.flashcards.model.user.UserRequest
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

    // Получение группы
    @POST("restapi/v1/groups/")
    fun getGroupInfo(
        @Body body: UserRequest
    ): Call<GroupResponse>

    // Получение карт группы
    @POST("restapi/v1/groups/cards/")
    fun getGroupCards(
        @Body body: UserRequest
    ): Call<CardsResponse>

    // Отправка истории
    @POST("restapi/v1/users/commit")
    fun postUserHistory(
        @Body body: UserHistoryRequest
    ): Call<UserHistoryStatus>
}
