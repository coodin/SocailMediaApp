package com.example.domain.model

import java.util.*

data class RecentMessage(
    val messageText: String,
    val readBy: ReadBy
)

data class ReadBy(
    val sentAt: Date,
    val sentBy: String
)