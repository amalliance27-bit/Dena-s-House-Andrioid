package com.example.builtbyd.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.builtbyd.model.AppContentRepository
import com.example.builtbyd.model.ScreenRoute
import com.example.builtbyd.ui.components.MarqueeBanner
import com.example.builtbyd.ui.components.VoicePlayerPill
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltBurgundy
import com.example.builtbyd.ui.theme.BuiltBurgundyDeep
import com.example.builtbyd.ui.theme.BuiltCardBg
import com.example.builtbyd.ui.theme.BuiltCharcoal
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltIvorySoft
import com.example.builtbyd.ui.theme.BuiltLine

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    isVoicePlaying: Boolean,
    onToggleVoice: () -> Unit,
    onOpenConsultation: (String?) -> Unit,
    onNavigate: (ScreenRoute) -> Unit,
    isTestimonialsPaused: Boolean,
    onToggleTestimonials: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BuiltBlack)
            .testTag("home_screen_lazy_column")
    ) {
        // 1. HERO SECTION
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            ) {
                // Background image from assets
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("file:///android_asset/assets/media-02-f34a6a16d1.jpg")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Deana Brady training in gym",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dramatic Dark Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    BuiltBlack.copy(alpha = 0.5f),
                                    BuiltBlack.copy(alpha = 0.75f),
                                    BuiltBlack
                                )
                            )
                        )
                )

                // Hero Copy
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "Deana Brady · Hamilton, Ontario",
                        color = BuiltGoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Built by D",
                        color = BuiltIvory,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 44.sp,
                        lineHeight = 46.sp
                    )
                    Text(
                        text = "Stronger at every age.",
                        color = BuiltGoldLight,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Real training. Real women. Real results.",
                        color = BuiltIvorySoft,
                        fontSize = 13.sp,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onOpenConsultation(null) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BuiltGold,
                                contentColor = BuiltBlack
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.testTag("hero_book_consultation_button")
                        ) {
                            Text("Book Consultation", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = { onNavigate(ScreenRoute.JOURNAL) },
                            border = BorderStroke(1.dp, BuiltGoldLight),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BuiltGoldLight),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("The Journal ↘", fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Audio Intro Pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x99100D0D), RoundedCornerShape(8.dp))
                            .border(1.dp, BuiltLine, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "D's Voice Introduction",
                                    color = BuiltGoldLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                                Text(
                                    text = if (isVoicePlaying) "Listening to Deana..." else "Tap to hear Deana's welcome message",
                                    color = BuiltIvorySoft,
                                    fontSize = 12.sp
                                )
                            }
                            VoicePlayerPill(
                                isPlaying = isVoicePlaying,
                                onToggle = onToggleVoice
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("STRENGTH", color = BuiltGoldLight, fontSize = 11.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
                        Text("◆", color = BuiltGold, fontSize = 9.sp)
                        Text("MOBILITY", color = BuiltGoldLight, fontSize = 11.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
                        Text("◆", color = BuiltGold, fontSize = 9.sp)
                        Text("LONGEVITY", color = BuiltGoldLight, fontSize = 11.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 2. MANIFESTO
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BuiltBurgundyDeep)
                    .padding(24.dp)
            ) {
                Text(
                    text = "PRECISION WITH PURPOSE",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your body is built to become stronger.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Precision Physique gives women a clear training system built around strength, shape, movement, and the life they live today. Every session has a reason. Every progression earns its place.",
                    color = BuiltIvorySoft,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Whether your goal is fat loss, steadier habits through hormonal change, freer movement, or visible muscle, Deana meets you where you stand and builds forward.",
                    color = BuiltIvorySoft,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = { onOpenConsultation(null) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BuiltGold,
                        contentColor = BuiltBlack
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Tell Deana what you want to achieve →", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // 3. PHILOSOPHY / 5 PRINCIPLES
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 28.dp)
            ) {
                Text(
                    text = "THE BUILT BY D METHOD",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Train with purpose. Live with power.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Spacer(modifier = Modifier.height(18.dp))

                AppContentRepository.principles.forEach { principle ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                        border = BorderStroke(1.dp, BuiltLine),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = principle.number,
                                color = BuiltGold,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.width(36.dp)
                            )
                            Column {
                                Text(
                                    text = principle.title,
                                    color = BuiltIvory,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = principle.description,
                                    color = BuiltIvorySoft,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. SERVICES
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF140E10))
                    .padding(horizontal = 20.dp, vertical = 28.dp)
            ) {
                Text(
                    text = "CHOOSE YOUR COACHING",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Built around your life.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Text(
                    text = "Direct coaching, clear structure, and an approach made for women who are ready to feel capable in their bodies.",
                    color = BuiltIvorySoft,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
                )

                AppContentRepository.services.forEach { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (service.isPrimary) BuiltBurgundyDeep else BuiltCardBg
                        ),
                        border = BorderStroke(1.dp, BuiltGold.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column {
                            if (service.imageAsset != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("file:///android_asset/${service.imageAsset}")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = service.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                )
                            }
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = service.tag,
                                    color = BuiltGoldLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = service.title,
                                    color = BuiltIvory,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = service.description,
                                    color = BuiltIvorySoft,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Button(
                                    onClick = { onOpenConsultation(service.title) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = BuiltGold,
                                        contentColor = BuiltBlack
                                    ),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text("Inquire & Book →", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. MEET YOUR COACH (DEANA BRADY)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BuiltBurgundyDeep),
                    border = BorderStroke(1.dp, BuiltGold),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data("file:///android_asset/assets/media-06-00cdd14285.jpg")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Portrait of Deana Brady",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "MEET YOUR COACH",
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Deana Brady",
                            color = BuiltIvory,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“Strength looks different on every woman. It belongs to every woman.”",
                            color = BuiltGoldLight,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Deana created Precision Physique for women who want direct guidance and training that respects their stage of life. Her work joins progressive resistance, movement quality, nutrition habits, and steady accountability.",
                            color = BuiltIvorySoft,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "This is strength with standards: capable, feminine, disciplined, and built to last.",
                            color = BuiltIvory,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { onOpenConsultation(null) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BuiltGold,
                                contentColor = BuiltBlack
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Work with Deana", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 6. TEASER CARDS (YOGA & 3D VIRTUAL WORLD)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Yoga Teaser
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(ScreenRoute.YOGA) },
                    colors = CardDefaults.cardColors(containerColor = BuiltCardBg),
                    border = BorderStroke(1.dp, BuiltLine),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "THE YOGA & MOBILITY ROOM",
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Space to breathe. Freedom to move.",
                            color = BuiltIvory,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "A quieter side of Built by D. Beach movement, time outdoors, and the people who make the journey feel shared.",
                            color = BuiltIvorySoft,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Enter the Yoga Room →",
                            color = BuiltGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // Dean'a House Fitness 3D Teaser
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(ScreenRoute.HOUSE_3D) },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF140B10)),
                    border = BorderStroke(1.dp, BuiltGold),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "VIRTUAL FITNESS DIMENSION",
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Dean'a House Fitness 3D",
                            color = BuiltIvory,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Step inside an interactive 3D training loft with spatial fitness equipment, 360° virtual gym exploration, and metaverse portal access.",
                            color = BuiltIvorySoft,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Explore 3D Virtual Loft ↗",
                            color = BuiltGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // 7. MARQUEE BANNER
        item {
            Spacer(modifier = Modifier.height(16.dp))
            MarqueeBanner()
            Spacer(modifier = Modifier.height(20.dp))
        }

        // 8. TESTIMONIALS CAROUSEL
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A0E14))
                    .padding(vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "THE PEOPLE AROUND D",
                            color = BuiltGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Words that move with her.",
                            color = BuiltIvory,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }

                    OutlinedButton(
                        onClick = onToggleTestimonials,
                        border = BorderStroke(1.dp, BuiltLine),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (isTestimonialsPaused) "Play" else "Pause",
                            color = BuiltGoldLight,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp)
                ) {
                    items(AppContentRepository.testimonials) { testimonial ->
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .height(180.dp),
                            colors = CardDefaults.cardColors(containerColor = BuiltBlack),
                            border = BorderStroke(1.dp, BuiltLine),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "“${testimonial.quote}”",
                                    color = BuiltIvory,
                                    fontFamily = FontFamily.Serif,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 14.sp,
                                    lineHeight = 19.sp
                                )
                                Column {
                                    HorizontalDivider(color = BuiltLine.copy(alpha = 0.5f), modifier = Modifier.padding(bottom = 6.dp))
                                    Text(
                                        text = testimonial.author,
                                        color = BuiltGoldLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = testimonial.location,
                                        color = BuiltIvorySoft.copy(alpha = 0.7f),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 9. FINAL CTA & FOOTER
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "YOUR NEXT CHAPTER CAN BE STRONGER",
                    color = BuiltGoldLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tell Deana what you want to achieve.",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Choose your goal. Share your starting point. Receive a path built for you.",
                    color = BuiltIvorySoft,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onOpenConsultation(null) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BuiltGold,
                        contentColor = BuiltBlack
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.testTag("footer_open_intake_button")
                ) {
                    Text("Start Consultation Intake", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = BuiltLine.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { onNavigate(ScreenRoute.SOCIAL) }) {
                        Text("Social Room", color = BuiltGoldLight, fontSize = 12.sp)
                    }
                    TextButton(onClick = { onNavigate(ScreenRoute.DESIGN) }) {
                        Text("Designed by Jarvis-27", color = BuiltIvorySoft, fontSize = 12.sp)
                    }
                }

                Text(
                    text = "© Precision Physique — Built by D · Deana Brady\nHamilton, Ontario · In Person & Online",
                    color = BuiltIvorySoft.copy(alpha = 0.5f),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}
