package website.ndlam.zalo.compose.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.data.Introduction
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.LocalAppDimens
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.White

@Composable
fun IntroductionCompose() {
    val subPager = rememberPagerState { 4 }
    val mainPager = rememberPagerState { 2 }
    val data = listOf(
        Introduction(
            R.drawable.ic_video,
            stringResource(R.string.introduction_title_1),
            stringResource(R.string.introduction_description_1)
        ),
        Introduction(
            R.drawable.ic_message,
            stringResource(R.string.introduction_title_2),
            stringResource(R.string.introduction_description_2)
        ),
        Introduction(
            R.drawable.ic_gallery,
            stringResource(R.string.introduction_title_3),
            stringResource(R.string.introduction_description_3)
        ),
        Introduction(
            R.drawable.ic_book_contacts,
            stringResource(R.string.introduction_title_4),
            stringResource(R.string.introduction_description_4)
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColorScheme.current.surface)
            .padding(LocalAppDimens.current.sizing.medium)
    ) {
        IntroductionViewPagerCompose(modifier = Modifier.weight(1f), data, mainPager, subPager)
        DotsCompose(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalAppDimens.current.sizing.xxlarge * 2),
            amount = mainPager.pageCount + subPager.pageCount - 1,
            size = LocalAppDimens.current.sizing.small - 2.dp,
            dotSelected = mainPager.currentPage + subPager.currentPage
        )
        Spacer(modifier = Modifier.height(LocalAppDimens.current.sizing.medium))
        TextButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(LocalAppDimens.current.sizing.xxlarge))
                .background(Blue800)
        ) {
            Text(
                text = stringResource(R.string.login),
                color = White
            )
        }
        Spacer(modifier = Modifier.height(LocalAppDimens.current.sizing.small))
        TextButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(LocalAppDimens.current.sizing.xxlarge))
                .background(LocalColorScheme.current.introductionColorScheme.secondaryButton)
        ) {
            Text(
                text = stringResource(R.string.create_account),
                color = LocalColorScheme.current.introductionColorScheme.onSecondaryButton
            )
        }
        Spacer(modifier = Modifier.height(LocalAppDimens.current.sizing.medium))
    }
}

@Preview
@Composable
fun IntroductionComposePreview() {
    IntroductionCompose()
}