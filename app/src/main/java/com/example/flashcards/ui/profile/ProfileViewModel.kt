package com.example.flashcards.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.flashcards.api.WebClient
import com.example.flashcards.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel() : ViewModel() {
    private val webClient = WebClient().getApi()

    // Не работает
    // Разобраться и сделать по-нормальному
    fun getUserInfo(userId: String, userToken: String): UserInfo {
        val getUser = UserRequest(
            Token(userId.toLong(), userToken),
            userId.toLong(),
        )

        val callUser = webClient.getUser(getUser)

        var userInfo = UserInfo(null, null, null, null, null, null)

        callUser.enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                val userName = response.body()?.user?.name.toString()
                val userTimestamp = response.body()?.user?.regTimestamp
                val userEmail = response.body()?.user?.email
                val userStatus = response.body()?.user?.status
                val userLevel = response.body()?.user?.level
                userInfo = UserInfo(
                    userId.toLong(),
                    userName,
                    userEmail,
                    userTimestamp!!,
                    userStatus,
                    userLevel
                )
                Log.i("test", "success $userInfo")
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })

        return userInfo

    }
}
