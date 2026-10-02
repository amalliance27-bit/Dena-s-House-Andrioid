package com.example.builtbyd.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.builtbyd.ui.theme.BuiltBurgundyDeep
import com.example.builtbyd.ui.theme.BuiltGold

@Composable
fun MarqueeBanner(
    modifier: Modifier = Modifier
) {
    val items = listOf(
        "WEIGHT LOSS", "HORMONE SUPPORT", "MOBILITY",
        "MUSCLE", "STRENGTH", "CONFIDENCE"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "marquee")
    val offsetProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -600f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "marquee_offset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(BuiltGold)
            .padding(vertical = 12.dp)
            .clipToBounds(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.offset { IntOffset(offsetProgress.toInt(), 0) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Repeat to ensure seamless scrolling
            repeat(4) {
                items.forEach { item ->
                    Text(
                        text = item,
                        color = BuiltBurgundyDeep,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Text(
                        text = "◆",
                        color = BuiltBurgundyDeep.copy(alpha = 0.6f),
                        fontSize = 9.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}
