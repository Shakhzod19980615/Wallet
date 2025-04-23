package com.example.wallet.presentation

import com.example.wallet.data.CardDto
import com.example.wallet.domain.models.Card

data class WalletUiState(
    val balance: Double = 0.0,
    val activeMethod: String = "cash",
    val activeCardId: Int? = null,
    val cards: List<CardDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNewCardAdded: Boolean = false
)
