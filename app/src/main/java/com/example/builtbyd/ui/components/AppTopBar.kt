package com.example.builtbyd.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.builtbyd.model.ScreenRoute
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltLine

@Composable
fun AppTopBar(
    currentRoute: ScreenRoute,
    isVoicePlaying: Boolean,
    onToggleVoice: () -> Unit,
    onNavigate: (ScreenRoute) -> Unit,
    onOpenConsultation: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BuiltBlack.copy(alpha = 0.95f))
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Monogram
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onNavigate(ScreenRoute.HOME) }
                    .testTag("brand_logo_button")
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, BuiltGold),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "D",
                        color = BuiltGoldLight,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "BUILT BY D",
                        color = BuiltIvory,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Precision Physique",
                        color = BuiltGoldLight,
                        fontFamily = FontFamily.Serif,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Actions: Voice Pill + Consultation + Menu
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VoicePlayerPill(
                    isPlaying = isVoicePlaying,
                    onToggle = onToggleVoice
                )

                Box {
                    IconButton(
                        onClick = { menuExpanded = !menuExpanded },
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("menu_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (menuExpanded) Icons.Default.Close else Icons.Default.Menu,
                            contentDescription = "Navigation Menu",
                            tint = BuiltIvory
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier
                            .background(BuiltBlack)
                            .border(1.dp, BuiltLine)
                    ) {
                        ScreenRoute.values().forEach { route ->
                            val isSelected = currentRoute == route
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        if (isSelected) {
                                            Text("◆", color = BuiltGold, fontSize = 10.sp)
                                        }
                                        Text(
                                            text = route.title,
                                            color = if (isSelected) BuiltGoldLight else BuiltIvory,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 14.sp
                                        )
                                    }
                                },
                                onClick = {
                                    menuExpanded = false
                                    onNavigate(route)
                                }
                            )
                        }

                        HorizontalDivider(color = BuiltLine, modifier = Modifier.padding(vertical = 4.dp))

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Book a Consultation",
                                    color = BuiltGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onOpenConsultation()
                            }
                        )
                    }
                }
            }
        }

        HorizontalDivider(color = BuiltLine.copy(alpha = 0.5f))
    }
}
