package website.ndlam.zalo.compose.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import website.ndlam.zalo.R
import website.ndlam.zalo.domains.dto.Introduction
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.introductionColorScheme

@Composable
fun IntroductionViewPagerCompose(
    modifier: Modifier,
    data: List<Introduction>,
    mainPager: PagerState,
    subPager: PagerState,
) {
    HorizontalPager(
        mainPager,
        modifier = modifier
    ) { page ->
        when (page) {
            0 -> Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(),
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = MaterialTheme.dimes.textSize.h1,
                    textAlign = TextAlign.Center,
                    color = Blue800,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = Bold
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.xxlarge))
                HorizontalPager(subPager) { localPage ->
                    IntroductionContentCompose(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimes.sizing.medium),
                        data = data[localPage]
                    )
                }
            }


            else -> {
                Image(
                    painter = painterResource(R.drawable.ic_launcher),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun IntroductionContentCompose(modifier: Modifier = Modifier, data: Introduction) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(data.image),
            contentDescription = data.title,
            modifier = Modifier.size(MaterialTheme.dimes.sizing.xxlarge * 3),
            tint = MaterialTheme.colorScheme.introductionColorScheme.tintICon
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.large))
        Text(
            text = data.title,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            fontSize = MaterialTheme.dimes.textSize.xlarge,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.xsmall))
        Text(
            text = data.description,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            fontWeight = Bold,
            fontSize = MaterialTheme.dimes.textSize.medium,
            color = MaterialTheme.colorScheme.introductionColorScheme.description,
            textAlign = TextAlign.Center
        )
    }
}