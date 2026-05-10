package website.ndlam.zalo.compose.regioncode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.compose.textfield.LocalTextField
import website.ndlam.zalo.ui.theme.Gray200
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.viewmodels.RegionCodeViewModel

@Composable
fun RegionCodeScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onBackPress: () -> Unit = {},
    onChangeRegionCode: (String) -> Unit = {}
) {
    val viewModel = viewModel<RegionCodeViewModel>()
    val context = LocalContext.current
    val regionCodes = viewModel.regionCodes.collectAsState()
    val colorScheme = LocalColorScheme.current.regionCodeScreenColorScheme

    LaunchedEffect(Unit) {
        viewModel.loadRegionCode(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                LocalColorScheme.current.primary
            )
            .padding(paddingValues)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalDimens.current.sizing.xxlarge)
                .background(LocalColorScheme.current.surface)
                .padding(PaddingValues(horizontal = LocalDimens.current.sizing.medium)),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = null,
                tint = LocalColorScheme.current.onPrimary,
                modifier = Modifier
                    .size(LocalDimens.current.sizing.large)
                    .clickable { onBackPress() }
            )

            Text(
                text = stringResource(R.string.please_choice_country),
                fontSize = LocalDimens.current.textSize.xlarge,
                color = LocalColorScheme.current.onPrimary
            )
        }

        Column(
            modifier = Modifier.padding(
                PaddingValues(
                    start = LocalDimens.current.sizing.medium,
                    end = LocalDimens.current.sizing.medium,
                    top = LocalDimens.current.sizing.small
                )
            )
        ) {
            LocalTextField(
                viewModel,
                leftSide = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = LocalColorScheme.current.onPrimary,
                        modifier = Modifier.size(LocalDimens.current.sizing.medium)
                    )
                },
                textFieldPadding = PaddingValues(start = LocalDimens.current.sizing.small),
                placeholder = stringResource(R.string.hint_search),
                containerPadding = PaddingValues(LocalDimens.current.sizing.xsmall),
                containerHeight = LocalDimens.current.sizing.xlarge,
                borderWith = 1.dp
            )

            LazyColumn(modifier = Modifier.weight(1f), userScrollEnabled = true) {
                regionCodes.value.forEach { (key, value) ->
                    item {
                        Text(
                            text = key,
                            color = colorScheme.groupName,
                            fontSize = LocalDimens.current.textSize.xlarge,
                            modifier = Modifier.padding(PaddingValues(top = LocalDimens.current.sizing.small))
                        )
                    }

                    items(items = value) { regionCode ->
                        TextButton(
                            onClick = {
                                onChangeRegionCode(regionCode.dialCode!!)
                            },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawBehind {
                                    drawLine(
                                        color = Gray200,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 0.5.dp.toPx()
                                    )
                                },
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${regionCode.name} (${regionCode.dialCode})",
                                color = LocalColorScheme.current.onPrimary,
                                fontSize = LocalDimens.current.textSize.large
                            )
                        }
                    }
                }

            }
        }
    }
}