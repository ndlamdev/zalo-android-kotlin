package website.ndlam.zalo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import website.ndlam.zalo.domain.repository.IAuthRepository
import website.ndlam.zalo.navigation.AppNavigation
import website.ndlam.zalo.ui.theme.ZolaApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        IAuthRepository.initKey()

        setContent {
            ZolaApplicationTheme {
                Scaffold(
                    // topBar = { TopAppBar(title = { Text("Screen Title") }) },
                    // bottomBar = { BottomAppBar { /* Bottom bar items */ } }
                ) { innerPadding -> // This contains the correct top and bottom padding values
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavigation()
                    }
                }

            }
        }
    }
}