package com.dariusz.fakegpsdetector.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.model.RoutersListModel

private val textColumnStyle = SpanStyle(fontWeight = FontWeight.Bold)

@Composable
fun CardWifiNodes(
    list: List<RoutersListModel>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            ),
        elevation = 10.dp
    ) {
        Column {
            list.forEach { item ->
                BaseCard("SSID: ", item.ssid ?: "")
                BaseCard("MAC Address: ", item.macAddress)
                BaseCard("Frequency: ", item.frequency.toString())
                BaseCard("Level: ", item.level.toString())
            }
        }
    }
}

@Composable
fun CardCellTowers(
    list: List<CellTowerModel>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            ),
        elevation = 10.dp
    ) {
        Column {
            list.forEach { item ->
                BaseCard("Cell ID: ", item.cellId)
                BaseCard("Location Area Code: ", item.locationAreaCode.toString())
                BaseCard("Mobile Country Code: ", item.mobileCountryCode.toString())
                BaseCard("Mobile Network Code: ", item.mobileNetworkCode.toString())
                BaseCard("Signal Strength", item.signalStrength.toString())
            }
        }
    }
}

@Composable
fun BaseCard(title: String, content: String, style: SpanStyle = textColumnStyle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            ),
        elevation = 10.dp
    ) {
        Column {
            Text(
                buildAnnotatedString {
                    append(title)
                    withStyle(style) {
                        append(content)
                    }
                }
            )
        }
    }
}
