package website.ndlam.zalo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.navigation.AppNavigation
import website.ndlam.zalo.ui.theme.ZolaApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        IAuthTokenRepository.initKey()

        setContent {
            ZolaApplicationTheme {
                AppNavigation()
            }
        }
    }
}