package com.example.wallet.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethodDto(
    @SerialName("active_method") val active_method: String,
    @SerialName("active_card_id")val active_card_id: Int? = null
)
