package com.example.wallet.data

import com.example.wallet.domain.Wallet

interface WalletApiService {
    suspend fun getWallet(): WalletDto
    suspend fun getCards(): List<CardDto>
    suspend fun updatePaymentMethod( method: String, cardId: Int? = null)
    suspend fun addCard( number: String, expireDate: String): CardDto
    suspend fun activatePromoCode( code: String)
}