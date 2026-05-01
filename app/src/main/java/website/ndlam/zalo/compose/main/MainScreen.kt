package website.ndlam.zalo.compose.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.ui.theme.LocalColorScheme

@Composable
fun MainScreen(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Column(
        modifier = Modifier
            .background(LocalColorScheme.current.onPrimary)
            .padding(paddingValues)
    ) {
        Text(text = "MainScreen")
    }
}