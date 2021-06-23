package com.dariusz.fakegpsdetector.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    listContent: List<RoutersListModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 55.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(listContent) { listContent ->
            BaseCard("SSID: ", listContent.ssid ?: "")
            BaseCard("MAC Address: ", listContent.macAddress)
            BaseCard("Frequency: ", listContent.frequency.toString())
            BaseCard("Level: ", listContent.level.toString())
            BaseCard("Channel: ", listContent.channel.toString())
            BaseCard("Quality: ", listContent.qualityOfConnection ?: "N/A")
        }
    }
}

@Composable
fun CardCellTowers(
    listContent: List<CellTowerModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 55.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(listContent) { listContent ->
            BaseCard("Cell ID: ", listContent.cellId)
            BaseCard("Location Area Code: ", listContent.locationAreaCode.toString())
            BaseCard("Mobile Country Code: ", listContent.mobileCountryCode.toString())
            BaseCard("Mobile Network Code: ", listContent.mobileNetworkCode.toString())
            BaseCard("Signal Strength", listContent.signalStrength.toString())
        }
    }
}

@Composable
fun BaseCard(title: String, content: String, style: SpanStyle = textColumnStyle) {
    Card {
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
