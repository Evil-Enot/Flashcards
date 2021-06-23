package com.example.flashcards.logic.adapters.find_groups

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.api.WebClient
import com.example.flashcards.model.groups.GroupInfo
import com.example.flashcards.databinding.ItemAddNewGroupFindBinding
import com.example.flashcards.model.Token
import com.example.flashcards.model.groups_find.AddGroupRequest
import com.example.flashcards.model.groups_find.AddGroupStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindNewGroupViewHolder(
    binding: ItemAddNewGroupFindBinding,
    private val context: Context?

) : RecyclerView.ViewHolder(binding.root) {

    private val webClient = WebClient().getApi()

    var groupName: TextView? = binding.groupNameItem
    var groupAuthor: TextView? = binding.groupAuthorItem
    var groupTags: TextView? = binding.groupFlagsItem
    var groupMaxPoints: TextView? = binding.groupMaxPointsItem
    var addGroup: ImageView? = binding.addGroup

    fun initializeFindNewGroup(item: GroupInfo) {

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
        // Обработка нажатия кнопки добавления группы
        addGroup?.setOnClickListener {
            val addGroup = AddGroupRequest(
                Token(userId.toLong(), userToken),
                item.id
            )

            val callAddGroup = webClient.addFindGroup(addGroup)
            callAddGroup.enqueue(object : Callback<AddGroupStatus> {
                override fun onResponse(
                    call: Call<AddGroupStatus>,
                    response: Response<AddGroupStatus>
                ) {
                    Log.i("test", response.body().toString())
                    if (response.body()?.status!!.success) {
                        Toast.makeText(itemView.context, "Group added", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            itemView.context,
                            response.body()?.status?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AddGroupStatus>, t: Throwable) {
                    Log.i("test", "error $t")
                }
            })
        }
        //--------------------------------------------//
    }
}
