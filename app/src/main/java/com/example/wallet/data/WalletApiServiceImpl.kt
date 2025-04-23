package com.example.wallet.data

import android.util.Log
import com.example.wallet.common.ApiConstants
import com.example.wallet.domain.Wallet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class WalletApiServiceImpl(
    private val client : HttpClient
):WalletApiService {
    override suspend fun getWallet(): WalletDto {
        return client.get("${ApiConstants.BASE_URL}/wallet").body()
    }

    override suspend fun getCards(): List<CardDto> {
        return client.get("${ApiConstants.BASE_URL}/cards").body<List<CardDto>>()
    }

    override suspend fun addCard(number: String, expireDate: String): CardDto {
        val response = client.post("${ApiConstants.BASE_URL}/cards") {
            setBody(
                mapOf(
                    "number" to number,
                    "expire_date" to expireDate
                )
            )
        }

        if (!response.status.isSuccess()) {
            throw Exception("Failed to add card: ${response.status}")
        }

        return response.body()
    }


    override suspend fun updatePaymentMethod(method: String, cardId: Int?) {
        val TAG = "PaymentMethodAPI"
        val request = PaymentMethodDto(method, cardId)

        try {
             client.put("${ApiConstants.BASE_URL}/wallet/method") {
                contentType(ContentType.Application.Json)
                setBody(request)

            }

        } catch (e: Exception) {
            Log.e(TAG, "Exception during request", e)
        }
    }

    override suspend fun activatePromoCode(code: String) {
        return client.post( "${ApiConstants.BASE_URL}/promo").body()
    }
}