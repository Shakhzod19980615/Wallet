import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.wallet.ui.theme.buttonBgColor
import com.example.wallet.ui.theme.cardTextLineColor

@Composable
fun CardItemView(cardNumber: String, expiry: String, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            .shadow(2.dp, shape = RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(
                containerColor = buttonBgColor,
    ),
    ) {
        Column {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.5.dp, cardTextLineColor),
                colors = CardDefaults.cardColors(
                    containerColor = buttonBgColor,)
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = cardNumber)
                 }
                }


            // Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .height(42.dp)
                    .padding(top = 0.dp,  start = 16.dp, bottom = 16.dp, end = 16.dp)
                    .wrapContentWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.5.dp, cardTextLineColor),
                colors = CardDefaults.cardColors(
                    containerColor = buttonBgColor,)
            ){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)

                ){
                    Text(
                        text = expiry)
                }

            }

        }
    }
}
