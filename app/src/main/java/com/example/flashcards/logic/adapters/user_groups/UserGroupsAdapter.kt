package com.example.flashcards.logic.adapters.user_groups

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.ItemUserGroupsBinding
import com.example.flashcards.logic.interfaces.user_groups.OnUserGroupsClickListener
import com.example.flashcards.model.*
import com.example.flashcards.model.groups.RecordsGroup
import com.example.flashcards.model.user.UserInfoResponse
import com.example.flashcards.model.user.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGroupsAdapter(
    private val userGroups: ArrayList<RecordsGroup?>,
    private var clickListener: OnUserGroupsClickListener,
    private val context: Context?
) :
    RecyclerView.Adapter<UserGroupsViewHolder>(),
    Filterable {

    private val webClient = WebClient().getApi()

    var userGroupsList: ArrayList<RecordsGroup?> = userGroups

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupsViewHolder {
        return UserGroupsViewHolder(
            ItemUserGroupsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserGroupsViewHolder, position: Int) {

        // Вывод имени группы
        holder.groupName?.text = userGroupsList[position]?.group?.name

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
        // Получение автора группы в зависимости от того, кто автор - сообщество или пользователь
        if (userGroupsList[position]?.group?.authorUserId.toString() != "0") {
            val getUser = UserRequest(
                Token(userId.toLong(), userToken),
                userGroupsList[position]?.group?.authorUserId!!
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
                "Community " + userGroupsList[position]?.group?.authorCommunityId.toString()
        }
        //--------------------------------------------//

        // Получение флагов группы
        holder.groupFlags?.text = userGroupsList[position]?.group?.flags

        // Получение max points группы
        holder.groupMaxPoints?.text = userGroupsList[position]?.group?.maxPoints.toString()

        // Получение флагов группы
        holder.groupLastVisit?.text = userGroupsList[position]?.lastVisit?.lastAnswerTime.toString()

        holder.initializeUserGroups(userGroupsList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return userGroupsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                userGroupsList = if (charSearch.isEmpty()) {
                    userGroups
                } else {
                    val resultList = ArrayList<RecordsGroup?>()
                    for (group in userGroups) {
                        if (group?.group!!.name
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(group)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userGroupsList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userGroupsList = results?.values as ArrayList<RecordsGroup?>
                notifyDataSetChanged()
            }
        }
    }
}
