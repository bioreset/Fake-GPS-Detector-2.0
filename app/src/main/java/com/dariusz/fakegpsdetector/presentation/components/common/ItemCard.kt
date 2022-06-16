package com.dariusz.fakegpsdetector.presentation.components.common

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.dariusz.fakegpsdetector.domain.model.CellTowerModel
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CellTowerItemCard(cellTowerModel: CellTowerModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        ExpandableContents(
            initialContent = {
                BaseDetail("Cell ID: ", cellTowerModel.cellId)
            },
            fullContent = {
                Column {
                    BaseDetail("Cell ID: ", cellTowerModel.cellId)
                    BaseDetail("Location Area Code: ", cellTowerModel.locationAreaCode.toString())
                    BaseDetail("Mobile Country Code: ", cellTowerModel.mobileCountryCode.toString())
                    BaseDetail("Mobile Network Code: ", cellTowerModel.mobileNetworkCode.toString())
                    BaseDetail("Signal Strength: ", cellTowerModel.signalStrength.toString())
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiNodeItemCard(routersListModel: RoutersListModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        ExpandableContents(
            initialContent = {
                BaseDetail("SSID: ", routersListModel.ssid ?: "")
            },
            fullContent = {
                Column {
                    BaseDetail("SSID: ", routersListModel.ssid ?: "")
                    BaseDetail("MAC Address: ", routersListModel.macAddress)
                    BaseDetail("Frequency: ", routersListModel.frequency.toString())
                    BaseDetail("Level: ", routersListModel.level.toString())
                    BaseDetail("Channel: ", routersListModel.channel.toString())
                    BaseDetail("Quality: ", routersListModel.qualityOfConnection ?: "N/A")
                }
            }
        )
    }
}

@Composable
fun BaseDetail(
    title: String,
    content: String,
    style: TextStyle = TextStyle(fontWeight = FontWeight.SemiBold)
) {
    Row(
        modifier = Modifier.padding(10.dp, 1.dp, 10.dp, 1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Text(text = content, style = style)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContents(
    initialContent: @Composable () -> Unit,
    fullContent: @Composable () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Surface(modifier = Modifier
        .clickable { expanded = !expanded }
        .fillMaxWidth()
        .padding(if (!expanded) 0.dp else 5.dp)
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    IntSize(targetSize.width, initialSize.height) at 175
                                    durationMillis = 350
                                }
                            } else {
                                keyframes {
                                    IntSize(initialSize.width, targetSize.height) at 175
                                    durationMillis = 350
                                }
                            }
                        }
            }
        ) { expanded ->
            if (expanded) fullContent() else initialContent()
        }
    }
}
