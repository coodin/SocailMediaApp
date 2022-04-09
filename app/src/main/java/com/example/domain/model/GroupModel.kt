package com.example.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class GroupModel(
    @ServerTimestamp
    var createdAt: Date? = null,
    var createdBy: String? = null,
    var id: String,
    var members: List<String> = listOf(""),
    var modifiedAt: Date? = null,
    var name: String? = null,
    var recentMessage: RecentMessage,
    var type:Int,
)
