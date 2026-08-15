package website.ndlam.zalo.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    isLoading: Boolean,
    message: String = "Đang tải..."
) {
    if (isLoading) {
        Dialog(
            // Giữ trống lambda này để ngăn tắt dialog khi bấm ra ngoài vùng trống
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,   // Ngăn tắt khi bấm phím quay lại (Back button)
                dismissOnClickOutside = false // Ngăn tắt khi bấm ra ngoài hộp thoại
            )
        ) {
            // Thiết kế giao diện bên trong hộp thoại loading
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface, // Tự động đổi theo Dark/Light Mode
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
            ) {
                // Vòng xoay tiến trình (Progress Bar)
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dòng văn bản thông báo
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}