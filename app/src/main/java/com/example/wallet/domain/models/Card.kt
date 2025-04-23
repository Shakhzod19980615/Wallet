package com.example.wallet.domain.models

data class Card(
    val id: Int = 0,
    val number: String = "",
    val expireDate: String = "",
    val userId: Int = 0
)