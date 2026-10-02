package com.example.builtbyd.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight

@Composable
fun VoicePlayerPill(
    isPlaying: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "voice_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val bgColor by animateColorAsState(
        targetValue = if (isPlaying) BuiltGold else Color(0x2BD0A65A),
        label = "bg_color"
    )

    val textColor by animateColorAsState(
        targetValue = if (isPlaying) BuiltBlack else BuiltGoldLight,
        label = "text_color"
    )

    Box(
        modifier = modifier
            .testTag("voice_toggle_pill")
            .then(if (isPlaying) Modifier.scale(pulseScale) else Modifier)
            .background(bgColor, RoundedCornerShape(20.dp))
            .border(BorderStroke(1.dp, BuiltGold), RoundedCornerShape(20.dp))
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = if (isPlaying) "🔊" else "▶️",
                fontSize = 12.sp
            )
            Text(
                text = if (isPlaying) "D Speaking... (Pause)" else "Hear D's Voice",
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}
