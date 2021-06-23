package com.example.flashcards.model.groups

data class GroupInfo(
    val id: Long,
    val name: String,
    val parentId: Long,
    val maxPoints: Long,
    val visibility: Long,
    val flags: String,
    val isLeaf: Boolean,
    val authorUserId: Long,
    val authorCommunityId: Long,
)
