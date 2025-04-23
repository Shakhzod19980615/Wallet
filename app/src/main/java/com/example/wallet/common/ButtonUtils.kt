import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.wallet.ui.theme.activeButtonColor
import com.example.wallet.ui.theme.inactiveButtonColor

@Composable
fun getButtonState(cardNumber: String, expiryDate: String, expiryError: String?): Pair<Color, Color> {
    val rawCardNumber = cardNumber.replace(" ", "")
    val isCardValid = rawCardNumber.length == 16
    val isExpiryValid = expiryError == null && expiryDate.isNotBlank()
    val isFormValid = isCardValid && isExpiryValid

    /*val activeButtonColor = Color(0xFF202125)
    val inactiveButtonColor = Color(0xFFD9DBE2)*/
    val buttonColor = if (isFormValid) activeButtonColor else inactiveButtonColor
    val textColor = if (isFormValid) Color.White else Color.Black.copy(alpha = 0.6f)

    return buttonColor to textColor
}
