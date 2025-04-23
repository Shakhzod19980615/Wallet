//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CardNumberField(
//    cardNumber: String,
//    onCardNumberChange: (String) -> Unit
//) {
//    TextField(
//        value = cardNumberField,
//        onValueChange = { newValue ->
//            val digits = newValue.text.filter { it.isDigit() }.take(16)
//
//            // Apply formatting (space after every 4 digits)
//            val formatted = digits.chunked(4).joinToString(" ")
//
//            // Calculate new cursor position
//            var selectionIndex = newValue.selection.end
//
//            val numSpacesBefore = newValue.text.take(selectionIndex).count { it == ' ' }
//            val digitsBeforeCursor = newValue.text.take(selectionIndex).filter { it.isDigit() }
//            val formattedCursorPosition = digitsBeforeCursor.length +
//                    (digitsBeforeCursor.length / 4).coerceAtMost(3)
//
//            cardNumberField = TextFieldValue(
//                text = formatted,
//                selection = TextRange(formattedCursorPosition)
//            )
//        },
//        placeholder = { Text("1234 5678 9012 3456") },
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp),
//        colors = TextFieldDefaults.textFieldColors(
//            containerColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent
//        )
//    )
//
//}
//
