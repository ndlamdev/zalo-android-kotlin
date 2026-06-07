package website.ndlam.zalo.compose.menu

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.mainScreen
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen

data class MenuProp(
    val key: ScreenOnMainScreen = ScreenOnMainScreen.MESSAGE,
    @DrawableRes val id: Int,
    @DrawableRes val idActive: Int = 0,
    @StringRes val title: Int
)

val DATA = listOf(
    MenuProp(
        key = ScreenOnMainScreen.MESSAGE,
        id = R.drawable.ic_message,
        idActive = R.drawable.ic_message_fill,
        title = R.string.message
    ),
    MenuProp(
        key = ScreenOnMainScreen.CONTACT,
        id = R.drawable.ic_contact_book,
        idActive = R.drawable.ic_contact_book_fill,
        title = R.string.contact
    ),
    MenuProp(
        key = ScreenOnMainScreen.DISCOVER,
        id = R.drawable.ic_discover,
        idActive = R.drawable.ic_discover_fill,
        title = R.string.discover
    ),
    MenuProp(
        key = ScreenOnMainScreen.NEWSFEED,
        id = R.drawable.ic_news,
        idActive = R.drawable.ic_news_fill,
        title = R.string.newsfeed
    ),
    MenuProp(
        key = ScreenOnMainScreen.SETTING,
        id = R.drawable.ic_user,
        idActive = R.drawable.ic_user_fill,
        title = R.string.personal
    ),
)


@Composable
fun MenuBar(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    state: ScreenOnMainScreen,
    onClick: (ScreenOnMainScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.mainScreen.menuBarBackground)
            .padding(paddingValues)
    ) {
        DATA.map { item ->
            MenuItem(
                id = item.id,
                idActive = item.idActive,
                title = stringResource(item.title),
                isActive = state == item.key,
                onClick = {
                    Log.d(
                        "MenuItem: ${item.key}",
                        "Recompose--------------------------------------"
                    )
                    onClick(item.key)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(MaterialTheme.dimes.sizing.xxlargeAddMedium)
            )
        }
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int,
    title: String,
    @DrawableRes idActive: Int,
    isActive: Boolean = false,
    onClick: () -> Unit = {}
) {
    val density = LocalDensity.current

    Column(
        modifier = modifier.clickable {
            if (!isActive) {
                onClick()
            }
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = isActive,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = idActive),
                    contentDescription = null,
                    modifier = Modifier.size(
                        MaterialTheme.dimes.iconSize.md
                    ),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = title,
                    fontSize = MaterialTheme.dimes.textSize.medium,
                    fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        AnimatedVisibility(
            visible = !isActive,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = null,
                modifier = Modifier.size(
                    MaterialTheme.dimes.iconSize.md
                ),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}