package com.dariusz.fakegpsdetector.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    listOfWifiNodes: List<RoutersListModel>
) {
    val emptyList =
        listOf(
            RoutersListModel("", "", 0, 0, 0, "")
        )
    val openBox = remember { mutableStateOf(false) }
    val currentItemToShow = remember { mutableStateOf(emptyList[0]) }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 55.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(items = listOfWifiNodes) { listContent ->
            BaseCard("SSID: ", listContent.ssid ?: "")
            BaseCard("MAC Address: ", listContent.macAddress)
            BaseCard("Frequency: ", listContent.frequency.toString())
            BaseCard("Level: ", listContent.level.toString())
            BaseCard("Channel: ", listContent.channel.toString())
            BaseCard("Quality: ", listContent.qualityOfConnection ?: "N/A")
            Button(
                onClick = {
                    openBox.value = true
                    currentItemToShow.value = listContent
                },
                modifier = Modifier.padding(
                    end = 10.dp, bottom = 10.dp, top = 10.dp
                )
            ) {
                Text("Info")
            }
        }
    }
    if (openBox.value) WifiNodeDetailBox(currentItemToShow.value)
}

@Composable
fun CardCellTowers(
    listOfCellTowers: List<CellTowerModel>
) {
    val emptyList = listOf(
        CellTowerModel("", 0, "", "", 0)
    )
    val openBox = remember { mutableStateOf(false) }
    val currentItemToShow = remember { mutableStateOf(emptyList[0]) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 55.dp
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
    ) {
        items(items = listOfCellTowers) { listContent ->
            BaseCard("Cell ID: ", listContent.cellId)
            BaseCard("Location Area Code: ", listContent.locationAreaCode.toString())
            BaseCard("Mobile Country Code: ", listContent.mobileCountryCode.toString())
            BaseCard("Mobile Network Code: ", listContent.mobileNetworkCode.toString())
            BaseCard("Signal Strength: ", listContent.signalStrength.toString())
            Button(
                onClick = {
                    openBox.value = true
                    currentItemToShow.value = listContent
                },
                modifier = Modifier.padding(
                    end = 10.dp, bottom = 10.dp, top = 10.dp
                )
            ) {
                Text("Info")
            }
        }
    }
    if (openBox.value) CellTowerDetailBox(currentItemToShow.value)
}

@Composable
fun BaseCard(
    title: String,
    content: String,
    style: SpanStyle = textColumnStyle
) {
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
