package com.example.flashcards.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.data.CommunityInfo
import com.example.flashcards.data.GroupInfo
import com.example.flashcards.databinding.FragmentProfileBinding
import com.example.flashcards.logic.adapters.profile.CommunitiesAdapter
import com.example.flashcards.logic.adapters.profile.GroupsAdapter
import com.example.flashcards.logic.interfaces.profile.OnCommunityClickListener
import com.example.flashcards.logic.interfaces.profile.OnGroupClickListener

class ProfileFragment : Fragment(), OnGroupClickListener, OnCommunityClickListener {

    private var groupList: ArrayList<GroupInfo> = ArrayList()
    private var communityList: ArrayList<CommunityInfo> = ArrayList()

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addGroups()
        addCommunities()

        val userGroup: RecyclerView = binding.userGroupsRw
        userGroup.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        userGroup.adapter = GroupsAdapter(groupList, this)

        val userCommunities: RecyclerView = binding.userCommunitiesRw
        userCommunities.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        userCommunities.adapter = CommunitiesAdapter(communityList, this)

        val userName: TextView = binding.userName
        val userEmail: TextView = binding.userEmail
        val userStatus: TextView = binding.userStatus
        val userLevel: TextView = binding.userLevel

        profileViewModel.userInfo.observe(viewLifecycleOwner, {
            userName.text = it.name
            userEmail.text = it.email
            userStatus.text = it.status
            userLevel.text = it.level
        })

        return root
    }

    private fun addGroups() {
        groupList.add(GroupInfo("First", "Me", "12/03/2020", "", ""))
        groupList.add(GroupInfo("Second", "He", "10/09/2017", "", ""))
        groupList.add(GroupInfo("Third", "She", "21/12/2008", "", ""))
        groupList.add(GroupInfo("Fourth", "We", "16/01/2066", "", ""))
        groupList.add(GroupInfo("Fifth", "They", "22/07/2345", "", ""))
    }

    private fun addCommunities() {
        communityList.add(CommunityInfo("Fifth", "They", "132"))
        communityList.add(CommunityInfo("Fourth", "We", "654"))
        communityList.add(CommunityInfo("First", "Me", "79"))
        communityList.add(CommunityInfo("Third", "She", "251655"))
        communityList.add(CommunityInfo("Second", "He", "6651556520"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onGroupItemClick(item: GroupInfo, position: Int) {
        findNavController().navigate(R.id.action_to_profile_to_groupPageFragment)
//        intent.putExtra("GroupName", item.name)
//        intent.putExtra("GroupAuthor", item.author)
//        intent.putExtra("GroupTime", item.time)
    }

    override fun onCommunityItemClick(item: CommunityInfo, position: Int) {
        findNavController().navigate(R.id.action_to_profile_to_communityFragment)
    }
}
