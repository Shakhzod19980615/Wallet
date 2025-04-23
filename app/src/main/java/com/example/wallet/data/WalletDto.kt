package com.example.wallet.data

import com.example.wallet.domain.Wallet
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletDto(
    @SerialName("id") val id: Int,
    @SerialName("balance") val balance: Double,
    @SerialName("phone") val phone: String,
    @SerialName("active_method") val activeMethod: String,
    @SerialName("active_card_id") val activeCardId: Int? = null
)
 fun WalletDto.toWallet() = Wallet(
    id = id,
    balance = balance,
    phone = phone,
    activeMethod = activeMethod,
    activeCardId = activeCardId
 )
