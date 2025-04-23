package com.example.wallet.domain

data class Wallet(
    val id: Int = 0,
    val balance: Double = 0.0,
    val phone: String = "",
    val activeMethod: String = "",
    val activeCardId: Int? = null
)