package com.example.builtbyd.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltIvory
import com.example.builtbyd.ui.theme.BuiltIvorySoft

@Composable
fun WelcomeSplashScreen(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AnimatedVisibility(
        visible = visible,
        exit = fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BuiltBlack)
                .clickable { onDismiss() }
                .testTag("welcome_splash_screen")
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("file:///android_asset/assets/media-02-f34a6a16d1.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                BuiltBlack.copy(alpha = 0.85f),
                                BuiltBlack.copy(alpha = 0.65f),
                                BuiltBlack.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Precision Physique · Deana Brady",
                    color = BuiltGoldLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "BUILT BY D",
                    color = BuiltIvory,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 52.sp,
                    letterSpacing = (-1).sp
                )
                Text(
                    text = "Stronger at every age.",
                    color = BuiltGoldLight,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(2.dp)
                        .background(BuiltGold)
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Tap anywhere to enter & hear Deana's voice",
                    color = BuiltIvorySoft,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
