package com.example.builtbyd.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.builtbyd.model.AppContentRepository
import com.example.builtbyd.ui.components.Interactive3DCanvas
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltBurgundyDeep
import com.example.builtbyd.ui.theme.BuiltCardBg
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltIvorySoft
import com.example.builtbyd.ui.theme.BuiltLine

@Composable
fun HouseFitnessScreen(
    portalUrl: String,
    onUpdatePortalUrl: (String) -> Unit,
    onOpenConsultation: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showUrlEditDialog by remember { mutableStateOf(false) }
    var tempUrlText by remember { mutableStateOf(portalUrl) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BuiltBlack)
            .padding(horizontal = 18.dp)
            .testTag("house_fitness_screen_lazy_column")
    ) {
        // Return button
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable { onBackToHome() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Return to Front Room",
                    tint = BuiltGoldLight
                )
                Text(
                    text = " Return to Built by D",
                    color = BuiltGoldLight,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Header
        item {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = "VIRTUAL TRAINING DIMENSION",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Dean'a House Fitness 3D.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Step inside an interactive 3D training loft with spatial fitness equipment, 360° virtual gym exploration, and metaverse portal access.",
                    color = BuiltIvorySoft,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // 3D Canvas
        item {
            Interactive3DCanvas(
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        // Metaverse Gateway Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                colors = CardDefaults.cardColors(containerColor = BuiltBurgundyDeep),
                border = BorderStroke(1.dp, BuiltGold),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "METAVERSE GATEWAY",
                        color = BuiltGoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Launch the 3D Fitness World",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Connect your mobile device or headset to enter Dean'a House Fitness in full 3D spatial reality. Experience live avatar coaching, group lifting rooms, and form breakdowns.",
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(portalUrl)).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Cannot open browser", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BuiltGold,
                                contentColor = BuiltBlack
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.testTag("launch_3d_portal_button")
                        ) {
                            Text("⚡ Enter 3D World ↗", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("3D World Link", portalUrl)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "3D Link Copied!", Toast.LENGTH_SHORT).show()
                            },
                            border = BorderStroke(1.dp, BuiltGoldLight),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BuiltGoldLight),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Copy Link", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                tempUrlText = portalUrl
                                showUrlEditDialog = true
                            },
                            border = BorderStroke(1.dp, BuiltLine),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BuiltIvorySoft),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit URL", modifier = Modifier.height(14.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BuiltBlack.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                            .border(1.dp, BuiltLine, RoundedCornerShape(4.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Portal URL: $portalUrl",
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // 4 Zones Header
        item {
            Text(
                text = "FOUR SPECIALIZED 3D TRAINING ZONES",
                color = BuiltGoldLight,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 18.dp, bottom = 8.dp)
            )
        }

        // 4 Zones List
        items(AppContentRepository.virtualZones) { zone ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                border = BorderStroke(1.dp, BuiltLine),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = zone.zoneNumber,
                        color = BuiltGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = zone.title,
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = zone.description,
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Bottom CTA
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onOpenConsultation,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BuiltGold,
                    contentColor = BuiltBlack
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Book 1-on-1 Consultation", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showUrlEditDialog) {
        AlertDialog(
            onDismissRequest = { showUrlEditDialog = false },
            title = {
                Text("Edit 3D World / Metaverse URL", color = BuiltIvory, fontFamily = FontFamily.Serif)
            },
            text = {
                Column {
                    Text(
                        text = "Enter your virtual loft link (Spatial, Roblox, VRChat):",
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = tempUrlText,
                        onValueChange = { tempUrlText = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdatePortalUrl(tempUrlText)
                        showUrlEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BuiltGold, contentColor = BuiltBlack)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showUrlEditDialog = false }) {
                    Text("Cancel", color = BuiltIvorySoft)
                }
            },
            containerColor = BuiltBlack
        )
    }
}
