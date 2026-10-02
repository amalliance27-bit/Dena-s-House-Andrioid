package com.example.builtbyd.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
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
fun SocialRoomScreen(
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
            .testTag("social_room_screen_lazy_column")
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

        // Room Header
        item {
            Column(modifier = Modifier.padding(bottom = 18.dp)) {
                Text(
                    text = "THE BUILT BY D COMMUNITY",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "The Social Media Room.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Step inside Deana's daily community. Follow daily workout routines, client milestones, form breakdowns, and real-time fitness inspiration.",
                    color = BuiltIvorySoft,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Channels Grid
        items(AppContentRepository.socialChannels) { channel ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(channel.url)).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Cannot open browser", Toast.LENGTH_SHORT).show()
                        }
                    },
                colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                border = BorderStroke(1.dp, BuiltLine),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = channel.name.uppercase(),
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Follow ↗",
                            color = BuiltGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = channel.handle,
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = channel.description,
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Section: Feed Highlights & Stories
        item {
            Column(modifier = Modifier.padding(top = 24.dp, bottom = 10.dp)) {
                Text(
                    text = "FEED HIGHLIGHTS & STORIES",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Real workouts. Real community.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "A window into Deana's coaching floor, beach movement sessions, and the women lifting each other up.",
                    color = BuiltIvorySoft,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Feed items
        items(AppContentRepository.socialFeed) { post ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                border = BorderStroke(1.dp, BuiltLine),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/${post.imageAsset}")
                            .crossfade(true)
                            .build(),
                        contentDescription = post.tag,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = post.tag,
                            color = BuiltGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = post.quote,
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
                        text = "Ready to start your transformation?",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Drop a message, follow along, or book your one-on-one consultation intake with Deana.",
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Button(
                            onClick = onOpenConsultation,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BuiltGold,
                                contentColor = BuiltBlack
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Book Consultation", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:deanambrady@hotmail.com")
                                    putExtra(Intent.EXTRA_SUBJECT, "Social Media Enquiry")
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
                                }
                            },
                            border = BorderStroke(1.dp, BuiltGoldLight),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BuiltGoldLight),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Send Email")
                        }
                    }
                }
            }
        }
    }
}
