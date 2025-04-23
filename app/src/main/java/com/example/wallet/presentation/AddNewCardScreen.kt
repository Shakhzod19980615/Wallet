import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.fragment.app.viewModels
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallet.R
import com.example.wallet.presentation.viewModel.WalletViewModel
import com.example.wallet.ui.theme.buttonBgColor
import com.example.wallet.ui.theme.cardTextLineColor
import com.example.wallet.ui.theme.hintColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCardScreen(
    viewModel: WalletViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = { }
) {
    var cardNumberField by remember { mutableStateOf(TextFieldValue("")) }
    var expiryDateField by remember { mutableStateOf(TextFieldValue("")) }
    var expiryDateError by remember { mutableStateOf<String?>(null) }
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Show snackbar if there's an error
    LaunchedEffect(key1 = uiState.error) {
        Log.d("AddCardDebug", "LaunchedEffect error: ${uiState.error}")
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(key1 = uiState.isNewCardAdded) {
        Log.d("AddCardDebug", "LaunchedEffect isNewCardAdded: ${uiState.isNewCardAdded}")
        if (uiState.isNewCardAdded) {
            onSaveClick()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Add New Card") },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                            .padding(end = 16.dp)
                            .clickable { onBackClick() }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Card Display Area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, shape = RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = buttonBgColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Card Number Field
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp, bottom = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(0.5.dp, cardTextLineColor),
                            colors = CardDefaults.cardColors(containerColor = buttonBgColor)
                        ) {
                            TextField(
                                value = cardNumberField,
                                onValueChange = { newValue ->
                                    val digits = newValue.text.filter { it.isDigit() }.take(16)
                                    val formatted = digits.chunked(4).joinToString(" ")

                                    val digitsBeforeCursor = newValue.text
                                        .take(newValue.selection.end)
                                        .filter { it.isDigit() }
                                    val cardNumber = digits.take(16).toIntOrNull()
                                    cardNumberError = when {
                                        digits.isEmpty() -> "Card number cannot be empty"
                                        cardNumber != 16 -> "Card number must be 16 digits"
                                        else -> null
                                    }
                                    val newCursorPosition = digitsBeforeCursor.length +
                                            (digitsBeforeCursor.length / 4).coerceAtMost(3)

                                    cardNumberField = TextFieldValue(
                                        text = formatted,
                                        selection = TextRange(newCursorPosition)
                                    )
                                    if (cardNumberError != null && digits.length == 16) {
                                        cardNumberError = null
                                    }
                                },
                                placeholder = { Text("1234 5678 9012 3456") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedPlaceholderColor = hintColor,
                                    focusedPlaceholderColor = hintColor
                                )
                            )
                        }
                        // Show error if card number is empty or invalid
                        cardNumberError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                        }
                    }

                    // Expiry Date Field
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .wrapContentWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(0.5.dp, cardTextLineColor),
                            colors = CardDefaults.cardColors(containerColor = buttonBgColor)
                        ) {
                            TextField(
                                value = expiryDateField,
                                onValueChange = { newValue ->
                                    val digits = newValue.text.filter { it.isDigit() }.take(4)

                                    val formatted = when {
                                        digits.length <= 2 -> digits
                                        else -> digits.substring(0, 2) + "/" + digits.substring(2)
                                    }

                                    val cursorPos = newValue.selection.end
                                    val digitsBeforeCursor = newValue.text
                                        .take(cursorPos)
                                        .filter { it.isDigit() }

                                    val newCursorPos = if (digitsBeforeCursor.length <= 2) {
                                        digitsBeforeCursor.length
                                    } else {
                                        digitsBeforeCursor.length + 1 // account for slash
                                    }

                                    expiryDateField = TextFieldValue(
                                        text = formatted,
                                        selection = TextRange(newCursorPos.coerceAtMost(formatted.length))
                                    )

                                    // Validate Expiry Date
                                    val month = digits.take(2).toIntOrNull()
                                    expiryDateError = when {
                                        digits.isEmpty() -> "Expiry date cannot be empty"
                                        month == null -> "Month is invalid"
                                        month < 1 || month > 12 -> "Month must be between 01 and 12"
                                        else -> null
                                    }
                                },
                                placeholder = { Text("MM/YY") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .width(100.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedPlaceholderColor = hintColor,
                                    focusedPlaceholderColor = hintColor

                                )
                            )
                        }
                        // Show error message if validation fails
                        expiryDateError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            val (buttonColor, textColor) = getButtonState(
                cardNumber = cardNumberField.text,
                expiryDate = expiryDateField.text,
                expiryError = expiryDateError
            )

            Button(
                onClick = {
                    val rawCardNumber = cardNumberField.text.replace(" ", "")
                    val rawExpiry = expiryDateField.text

                    val isCardValid = rawCardNumber.length == 16
                    val isExpiryValid = expiryDateError == null && rawExpiry.isNotBlank()

                    if (isCardValid && isExpiryValid) {
                        viewModel.onAddCard(cardNumberField.text, rawExpiry)
                    } else {
                        if (rawCardNumber.isBlank()) {
                            cardNumberError = "Card number cannot be empty"
                        } else if (rawCardNumber.length < 16) {
                            cardNumberError = "Card number must be 16 digits"
                        }

                        if (rawExpiry.isBlank()) {
                            expiryDateError = "Expiry date cannot be empty"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                contentColor = textColor
            )
            ) {
                Text("Save")
            }
        }
    }
}
