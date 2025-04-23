package com.example.wallet.di

import ActivatePromoCodeUseCase
import AddCardUseCase
import GetCardsUseCase
import GetWalletUseCase
import UpdatePaymentMethodUseCase
import WalletUseCases
import com.example.wallet.common.ApiConstants
import com.example.wallet.data.WalletApiService
import com.example.wallet.data.WalletApiServiceImpl
import com.example.wallet.presentation.viewModel.WalletViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val appModule = module {

        single {
            HttpClient {
                defaultRequest {
                    header(ApiConstants.HEADER_PHONE_KEY, ApiConstants.DEFAULT_PHONE)
                    contentType(ContentType.Application.Json)
                }
                install(ContentNegotiation) {
                    json()
                }
            }
        }

        single<WalletApiService> {
            WalletApiServiceImpl(get())
        }
        single {
            WalletUseCases(
                getWallet = GetWalletUseCase(get()),
                getCards = GetCardsUseCase(get()),
                updatePaymentMethod = UpdatePaymentMethodUseCase(get()),
                addCard = AddCardUseCase(get()),
                activatePromoCode = ActivatePromoCodeUseCase(get())
            )
        }
        viewModel { WalletViewModel( useCases = get()) }
    }

}