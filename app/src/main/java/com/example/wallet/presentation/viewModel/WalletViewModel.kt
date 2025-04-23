package com.example.wallet.presentation.viewModel

import WalletUseCases
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallet.presentation.WalletUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletViewModel(
    private val useCases: WalletUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    init {
        loadWalletData()
    }
    private fun loadWalletData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val wallet = useCases.getWallet()
                val cards = useCases.getCards()
                Log.d("API_DEBUG", "Cards fetched: ${cards.size} -> $cards")
                _uiState.update {
                    it.copy(
                        balance = wallet.balance,
                        activeMethod = wallet.activeMethod,
                    activeCardId = wallet.activeCardId,
                    cards = cards,
                    isLoading = false
                    )
                }
                println("Cards received: $cards")
                Log.d("Cards received: ", cards.toString())
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }
                Log.e("API_DEBUG", "Error fetching cards", e)
            }
        }
    }
    fun onMethodChange(method: String, cardId: Int? = null) {
        viewModelScope.launch {
            Log.d("PaymentMethod", "Changing method to: $method, cardId: $cardId")
            try {
                useCases.updatePaymentMethod(method, cardId)
                _uiState.update {
                    it.copy(activeMethod = method, activeCardId = if (method == "card") cardId else null)
                }
                Log.d("PaymentMethod", "Update successful")
            } catch (e: Exception) {
                Log.e("PaymentMethod", "Error updating method", e)
                _uiState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }


    fun onAddCard(number: String, expireDate: String) {
        Log.d("AddCardDebug", "onAddCard called with: $number, $expireDate")
        viewModelScope.launch {
            try {
                val card = useCases.addCard( number, expireDate)
                Log.d("AddCardDebug", "Card added successfully: $card")
                _uiState.update {
                    it.copy(cards = it.cards + card,
                        isNewCardAdded = true, error = null)

                }
            } catch (e: Exception) {
                Log.e("AddCardDebug", "Add card failed: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Something went wrong",
                        isNewCardAdded = false)
                }
            }
        }
    }
    fun onActivatePromo(code: String) {
        viewModelScope.launch {
            try {
                useCases.activatePromoCode( code)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message)
                }
            }
        }
    }

}