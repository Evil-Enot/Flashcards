package com.example.flashcards.logic.adapters.find_groups

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.ItemAddNewGroupFindBinding
import com.example.flashcards.model.Token
import com.example.flashcards.model.groups.GroupInfo
import com.example.flashcards.model.user.UserInfoResponse
import com.example.flashcards.model.user.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindNewGroupAdapter(
    groups: List<GroupInfo>,
    private val context: Context?
) : RecyclerView.Adapter<FindNewGroupViewHolder>() {

    private val webClient = WebClient().getApi()

    private var groupsList: List<GroupInfo> = groups

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindNewGroupViewHolder {
        return FindNewGroupViewHolder(
            ItemAddNewGroupFindBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), context
        )
    }

    override fun onBindViewHolder(holder: FindNewGroupViewHolder, position: Int) {
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

        // Получение имени группы
        holder.groupName?.text = groupsList[position].name

        //--------------------------------------------//
        // Получение автора группы в зависимости от того, кто автор - сообщество или пользователь
        if (groupsList[position].authorUserId.toString() != "0") {
            val getUser = UserRequest(
                Token(userId.toLong(), userToken),
                groupsList[position].authorUserId!!
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
                "Community " + groupsList[position].authorCommunityId.toString()
        }
        //--------------------------------------------//

        // Получение тегов группы
        holder.groupTags?.text = groupsList[position].flags

        // Получение max points
        holder.groupMaxPoints?.text = groupsList[position].maxPoints.toString()

        holder.initializeFindNewGroup(groupsList[position])
    }

    override fun getItemCount(): Int {
        return groupsList.size
    }
}
