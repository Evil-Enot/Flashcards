package com.example.flashcards.logic.adapters.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.ItemProfileRecyclerviewGroupBinding
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener
import com.example.flashcards.model.Records
import com.example.flashcards.model.Token
import com.example.flashcards.model.UserInfoRequest
import com.example.flashcards.model.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupsAdapter(
    private val groups: List<Records?>,
    private var clickListener: OnGroupClickListener,
    private val context: Context?
) :
    RecyclerView.Adapter<ProfileGroupViewHolder>() {

    private val webClient = WebClient().getApi()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileGroupViewHolder(
        ItemProfileRecyclerviewGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ProfileGroupViewHolder, position: Int) {
        // Вывод имени группы
        holder.groupName?.text = groups[position]?.group?.name

        // Вывод даты последнего взаимодействия
        if (groups[position]?.lastVisit?.lastAnswerTime != null) {
            holder.groupDate?.text =
                groups[position]?.lastVisit?.lastAnswerTime?.toString()?.subSequence(0..10)
        } else {
            holder.groupDate?.text = "never"
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
        if (groups[position]?.group?.authorUserId.toString() != "0") {
            val getUser = UserInfoRequest(
                Token(userId.toLong(), userToken),
                groups[position]?.group?.authorUserId!!
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
                "Community " + groups[position]?.group?.authorCommunityId.toString()
        }
        //--------------------------------------------//

        holder.initializeGroups(groups[position], clickListener)
    }

    override fun getItemCount(): Int {
        return groups.size
    }
}
