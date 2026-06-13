package website.ndlam.zalo.ui.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.ZolaApplicationTheme
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.searchScreen

@Composable
fun SearchScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onBackPress: () -> Unit = {},
) {
    var searchText by remember { mutableStateOf("0949253545") }
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(paddingValues)
    ) {
        SearchTopBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onBackClick = onBackPress
        )

        SearchTabs(
            selectedTab = selectedTab
        ) { selectedTab = it }


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                SectionHeader(
                    title = stringResource(R.string.find_friend_by_phone_number_count, 1),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }

            items(2) { index ->
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                    FriendItem(
                        name = "Tuấn Vt Tuy Phong $index",
                        phoneNumber = "0949253545",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
            }

            item {
                SectionHeader(
                    title = stringResource(R.string.messages_count, 2),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FilterChip(
                            text = stringResource(
                                R.string.filter_link
                            )
                        )
                        FilterChip(
                            text = stringResource(
                                R.string.filter_file
                            )
                        )
                    }
                }
            }

            items(sampleMessages) { message ->
                MessageItem(message = message)
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = MaterialTheme.dimes.textSize.medium,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
    }
}


@Composable
fun FilterChip(
    text: String
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.searchScreen.filterChipBackground)
            .border(
                1.dp,
                Color.Gray.copy(alpha = 0.5f),
                RoundedCornerShape(MaterialTheme.dimes.sizing.medium)
            )
            .padding(
                horizontal = MaterialTheme.dimes.sizing.smallAddXsmall,
                vertical = MaterialTheme.dimes.sizing.xsmall
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.dimes.sizing.smallAddXsmall)
                .border(1.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp
        )
    }
}

data class SearchMessage(
    val senderName: String,
    val date: String,
    val quotedName: String,
    val quotedPhone: String
)

val sampleMessages = listOf(
    SearchMessage(
        senderName = "Quốc Tân V N P T Tuy Phong",
        date = "12/03",
        quotedName = "Tuấn Vt Tuy Phong",
        quotedPhone = "0949253545"
    ),
    SearchMessage(
        senderName = "Dương Tín",
        date = "12/03",
        quotedName = "Tuấn Vt Tuy Phong",
        quotedPhone = "0949253545"
    )
)

@Preview
@Composable
fun SearchScreenPreview() {
    ZolaApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(
                innerPadding
            )
        }
    }
}
