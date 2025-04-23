import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wallet.WalletScreen

object Routes {
    const val WALLET = "wallet"
    const val CARD_LIST = "card_list"
    const val AddNewCard = "add_new_card"

}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.WALLET
    ) {
        composable(Routes.WALLET) {
            WalletScreen(
                onBalanceClick = { navController.navigate(Routes.CARD_LIST) },
                onAddCardClick = { navController.navigate(Routes.AddNewCard) }
            )
        }
        composable(Routes.CARD_LIST) {
            CardListScreen()
        }
        composable(Routes.AddNewCard){
            AddNewCardScreen(onBackClick = navController::popBackStack,
                onSaveClick = { navController.navigate(Routes.WALLET) })
        }
    }
}
