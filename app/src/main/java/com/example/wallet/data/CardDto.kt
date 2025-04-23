package com.example.wallet.data

import com.example.wallet.domain.models.Card
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardDto (
    val id: Int,
    val number: String,
    @SerialName("expire_date") val expireDate: String,
    @SerialName("user_id") val userId: Int
)

fun CardDto.toCard() = Card(
    id = id,
    number = number,
    expireDate = expireDate,
    userId = userId
)