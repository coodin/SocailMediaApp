package com.example.domain.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.collections.ArrayList

data class UserProfile(
    var uid: String? = null,
    @ServerTimestamp
    var createdAt: Date? = null,
    @ServerTimestamp
    var updatedAt: Date? = null,
    var photoURL: String = "",
    @get:PropertyName("user_name")
    @set:PropertyName("user_name")
    var userName: String = "",
    var email: String = "",
    var groups: List<String> = listOf(""),
    var friendList: List<String> = listOf("")
    //var mobile: MobileNumber? = null
)