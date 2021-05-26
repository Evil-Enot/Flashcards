package com.example.flashcards.logic.adapters.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.ItemUserGroupsBinding
import com.example.flashcards.logic.interfaces.main.OnMainClickListener
import com.example.flashcards.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAdapter(
    private val userGroups: ArrayList<Records?>,
    private var clickListener: OnMainClickListener,
    private val context: Context?
) : RecyclerView.Adapter<MainViewHolder>() {

    private val webClient = WebClient().getApi()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemUserGroupsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        // Вывод имени группы
        holder.groupName?.text = userGroups[position]?.group?.name

        // Вывод флагов группы
        holder.groupFlags?.text = userGroups[position]?.group?.flags

        // Вывод max points группы
        holder.groupMaxPoints?.text = userGroups[position]?.group?.maxPoints.toString()

        // Вывод последнего визита
        if (userGroups[position]?.lastVisit?.lastAnswerTime.toString() != "null") {
            holder.groupLastVisit?.text =
                userGroups[position]?.lastVisit?.lastAnswerTime.toString().subSequence(0..10)
        } else {
            holder.groupLastVisit?.text = "Never"
        }

        //--------------------------------------------//
        // Получение токена и id пользователя

        val userIdSave = context?.getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = context?.getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        var userToken = ""
        var userId = ""

        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }
        if (userIdSave?.contains("UserId") == true) {
            userId = userIdSave.getString("UserId", "").toString()
        }

        //--------------------------------------------//

        //--------------------------------------------//
        // Вывод автора группы в зависимости от того, кто автор - сообщество или пользователь
        if (userGroups[position]?.group?.authorUserId.toString() != "0") {
            val getUser = UserInfoRequest(
                Token(userId.toLong(), userToken),
                userGroups[position]?.group?.authorUserId!!
            )

            val callUser = webClient.getUser(getUser)

            callUser.enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    holder.groupAuthor?.text = "User " + response.body()?.user?.name
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })
        } else {
            holder.groupAuthor?.text =
                "Community " + userGroups[position]?.group?.authorCommunityId.toString()
        }
        //--------------------------------------------//

        holder.initializeUserGroups(userGroups[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userGroups.size
    }
}
