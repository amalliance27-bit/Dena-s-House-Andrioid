package com.example.builtbyd.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.builtbyd.model.AppContentRepository
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltBurgundyDeep
import com.example.builtbyd.ui.theme.BuiltCardBg
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltIvorySoft
import com.example.builtbyd.ui.theme.BuiltLine

@Composable
fun YogaRoomScreen(
    onOpenConsultation: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BuiltBlack)
            .padding(horizontal = 18.dp)
            .testTag("yoga_room_screen_lazy_column")
    ) {
        // Return back link
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clickable { onBackToHome() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Front Room",
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

        // Room Header
        item {
            Column(modifier = Modifier.padding(bottom = 18.dp)) {
                Text(
                    text = "BUILT BY D · BEYOND THE GYM",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Yoga & Mobility.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Space to breathe. Freedom to move.",
                    color = BuiltGoldLight,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp
                )
                Text(
                    text = "Strength has a softer rhythm, too. A breath. A stretch. A moment by the water with the people who make the journey feel shared.",
                    color = BuiltIvorySoft,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // 4 Movement Pillars with Real Assets
        items(AppContentRepository.yogaPillars) { pillar ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                border = BorderStroke(1.dp, BuiltLine),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/${pillar.imageAsset}")
                            .crossfade(true)
                            .build(),
                        contentDescription = pillar.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = pillar.step,
                            color = BuiltGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = pillar.title,
                            color = BuiltIvory,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = pillar.description,
                            color = BuiltIvorySoft,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Room Close CTA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                colors = CardDefaults.cardColors(containerColor = BuiltBurgundyDeep),
                border = BorderStroke(1.dp, BuiltGold),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bring movement into your life.",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Tell Deana where you want more ease, confidence, or strength.",
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Button(
                        onClick = onOpenConsultation,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BuiltGold,
                            contentColor = BuiltBlack
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("Start Consultation Intake", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
