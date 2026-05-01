package website.ndlam.zalo.compose.welcome

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.domains.dto.Introduction
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.White

fun getDataIntroduction(context: Context) = listOf(
    Introduction(
        R.drawable.ic_video,
        context.resources.getString(R.string.introduction_title_1),
        context.resources.getString((R.string.introduction_description_1))
    ),
    Introduction(
        R.drawable.ic_message,
        context.resources.getString(R.string.introduction_title_2),
        context.resources.getString(R.string.introduction_description_2)
    ),

    Introduction(
        R.drawable.ic_gallery,
        context.resources.getString(R.string.introduction_title_3),
        context.resources.getString(R.string.introduction_description_3)
    ),
    Introduction(
        R.drawable.ic_book_contacts,
        context.resources.getString(R.string.introduction_title_4),
        context.resources.getString(R.string.introduction_description_4)
    ),
)

@Composable
fun IntroductionScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    navigateToSignInScreen: () -> Unit = {},
    navigateToSignUpScreen: () -> Unit = {}
) {
    val subPager = rememberPagerState { 4 }
    val mainPager = rememberPagerState { 2 }
    val context = LocalContext.current
    val data = getDataIntroduction(context)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColorScheme.current.surface)
            .padding(paddingValues)
            .padding(LocalDimens.current.sizing.medium)
    ) {
        IntroductionViewPagerCompose(modifier = Modifier.weight(1f), data, mainPager, subPager)
        DotsCompose(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalDimens.current.sizing.xxlarge * 2),
            amount = mainPager.pageCount + subPager.pageCount - 1,
            size = LocalDimens.current.sizing.small - 2.dp,
            dotSelected = mainPager.currentPage + subPager.currentPage
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.medium))
        TextButton(
            onClick = navigateToSignInScreen,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(LocalDimens.current.sizing.xxlarge))
                .background(Blue800)
        ) {
            Text(
                text = stringResource(R.string.login),
                color = White
            )
        }
        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.small))
        TextButton(
            onClick = navigateToSignUpScreen,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(LocalDimens.current.sizing.xxlarge))
                .background(LocalColorScheme.current.introductionColorScheme.secondaryButton)
        ) {
            Text(
                text = stringResource(R.string.create_account),
                color = LocalColorScheme.current.introductionColorScheme.onSecondaryButton
            )
        }
        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.medium))
    }
}