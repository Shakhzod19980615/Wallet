package com.example.wallet

import AppNavGraph
import BalanceCard
import PromoCodeBottomSheetContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.wallet.common.formatBalance
import com.example.wallet.presentation.viewModel.WalletViewModel
import com.example.wallet.ui.theme.WalletTheme
import com.example.wallet.ui.theme.black
import com.example.wallet.ui.theme.buttonBgColor
import com.example.wallet.ui.theme.textNormalColor
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.core.view.WindowCompat
import com.example.wallet.ui.theme.white


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WalletTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    viewModel: WalletViewModel = koinViewModel(),
    onBalanceClick:() -> Unit,
    onAddCardClick:() -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val formattedBalance = remember(state.balance) {
        formatBalance(state.balance)
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.ime)
                .padding(bottom = bottomPadding)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                PromoCodeBottomSheetContent(
                    onApply = {
                        showBottomSheet = false
                    }
                )
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = white),
                title = {
                    Text(
                        text = "Wallet",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = black
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Wallet Balance Card
           BalanceCard(
               formattedBalance = formattedBalance,
               onCardClick = onBalanceClick
           )
            Spacer(Modifier.height(16.dp))

            // Identification Required Button
            OutlinedButton(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonBgColor,
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_warning),
                    contentDescription = "Warning icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Identification required", fontWeight = FontWeight.SemiBold, color = textNormalColor)
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.icon_arrow_up_right),
                    contentDescription = "Arrow icon",
                    modifier = Modifier.size(24.dp)
                )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Wallet Options
            WalletOptionItem(
                icon = R.drawable.icon_promo,
                title = "Add Promo code",
                onClick = {showBottomSheet = true })
            WalletOptionSwitch(
                icon = R.drawable.icon_cash,
                title = "Cash",
                isChecked = state.activeMethod == "cash",
                onCheckedChange = {
                    viewModel.onMethodChange( method = "cash")
                }
            )

            WalletOptionSwitch(
                icon = R.drawable.icon_card,
                title = "Card **** 7777",
                isChecked = state.activeMethod == "card",
                onCheckedChange = {
                   val selectedCardId = state.cards.firstOrNull()?.id
                    viewModel.onMethodChange( method = "card", cardId = selectedCardId)
                }
            )
            WalletOptionItem(
                icon = R.drawable.icon_add_card, title = "Add new card",
                onClick = onAddCardClick)
        }
    }
}

@Composable
fun WalletOptionItem(icon: Int, title: String, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(vertical = 4.dp)
            .shadow(2.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = buttonBgColor,
        ),
        border = BorderStroke(1.dp, buttonBgColor),
        onClick = { onClick?.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.icon_arrow_right),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun WalletOptionSwitch(icon: Int, title: String, isChecked: Boolean,onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(vertical = 4.dp)
            .shadow(2.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = buttonBgColor,
        ),
        border = BorderStroke(1.dp, buttonBgColor),
        onClick = { onCheckedChange(!isChecked)  }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(title, fontSize = 16.sp)
            Spacer(Modifier.weight(1f))
            Switch(modifier = Modifier.width(60.dp), checked = isChecked, onCheckedChange = {
                onCheckedChange(it)
            })
        }
    }
}
