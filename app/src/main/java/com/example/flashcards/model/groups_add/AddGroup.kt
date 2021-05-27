package com.example.flashcards.model.groups_add

data class AddGroup(
    val name: String,
    val parentId: Long?,
    val maxPoints: Long,
    val visibility: Long,
    val flags: String,
    val isLeaf: Boolean,
    val authorUserId: Long
)
