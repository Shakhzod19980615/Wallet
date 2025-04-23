import com.example.wallet.data.WalletApiService

// WalletUseCases.kt
data class WalletUseCases(
    val getWallet: GetWalletUseCase,
    val getCards: GetCardsUseCase,
    val updatePaymentMethod: UpdatePaymentMethodUseCase,
    val addCard: AddCardUseCase,
    val activatePromoCode: ActivatePromoCodeUseCase
)

class GetWalletUseCase(private val api: WalletApiService) {
    suspend operator fun invoke() = api.getWallet()
}

class GetCardsUseCase(private val api: WalletApiService) {
    suspend operator fun invoke() = api.getCards()
}

class UpdatePaymentMethodUseCase(private val api: WalletApiService) {
    suspend operator fun invoke( method: String, cardId: Int? = null) =
        api.updatePaymentMethod( method, cardId)
}

class AddCardUseCase(private val api: WalletApiService) {
    suspend operator fun invoke( number: String, expireDate: String) =
        api.addCard(number, expireDate)
}

class ActivatePromoCodeUseCase(private val api: WalletApiService) {
    suspend operator fun invoke( code: String)
    = api.activatePromoCode( code)
}
