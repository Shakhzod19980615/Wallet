import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiryDateField(
    expiryDate: String,
    onExpiryDateChange: (String) -> Unit
) {
    val cleaned = expiryDate.filter { it.isDigit() }.take(4)

    val formatted = when {
        cleaned.length <= 2 -> cleaned
        else -> cleaned.substring(0, 2) + "/" + cleaned.substring(2)
    }

    val isValidMonth = formatted.take(2).toIntOrNull()?.let { it in 1..12 } ?: true

    TextField(
        value = formatted,
        onValueChange = { input ->
            val digits = input.filter { it.isDigit() }.take(4)
            val masked = when {
                digits.length <= 2 -> digits
                else -> digits.substring(0, 2) + "/" + digits.substring(2)
            }
            onExpiryDateChange(masked)
        },
        placeholder = { Text("MM/YY") },
        singleLine = true,
        isError = !isValidMonth,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .width(100.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Red
        )
    )
}
