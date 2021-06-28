package com.dariusz.fakegpsdetector.presentation.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.dariusz.fakegpsdetector.domain.model.CellTowerModel
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel

private val textColumnStyle = SpanStyle(fontWeight = FontWeight.SemiBold)

@Composable
fun CellTowerDetailBox(cellInfoData: CellTowerModel) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(IntrinsicSize.Max)
                    .width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BaseDetail("Cell ID: ", cellInfoData.cellId)
                BaseDetail("Location Area Code: ", cellInfoData.locationAreaCode.toString())
                BaseDetail("Mobile Country Code: ", cellInfoData.mobileCountryCode.toString())
                BaseDetail("Mobile Network Code: ", cellInfoData.mobileNetworkCode.toString())
                BaseDetail("Signal Strength: ", cellInfoData.signalStrength.toString())
                Button(onClick = { openDialog.value = false }) {
                    Text("Ok")
                }
            }
        }
    }
}

@Composable
fun WifiNodeDetailBox(wifiNodeData: RoutersListModel) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(IntrinsicSize.Max)
                    .width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BaseDetail("SSID: ", wifiNodeData.ssid ?: "")
                BaseDetail("MAC Address: ", wifiNodeData.macAddress)
                BaseDetail("Frequency: ", wifiNodeData.frequency.toString())
                BaseDetail("Level: ", wifiNodeData.level.toString())
                BaseDetail("Channel: ", wifiNodeData.channel.toString())
                BaseDetail("Quality: ", wifiNodeData.qualityOfConnection ?: "N/A")
                Button(onClick = { openDialog.value = false }) {
                    Text("Ok")
                }
            }
        }
    }
}

@Composable
fun BaseDetail(
    title: String,
    content: String,
    style: SpanStyle = textColumnStyle
) {
    Card {
        Column {
            Text(
                buildAnnotatedString {
                    append(title + "\n")
                    withStyle(style) {
                        append(content + "\n")
                    }
                }
            )
        }
    }
}