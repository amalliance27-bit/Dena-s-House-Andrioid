package com.example.builtbyd.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.builtbyd.ui.theme.BuiltBlack
import com.example.builtbyd.ui.theme.BuiltBurgundy
import com.example.builtbyd.ui.theme.BuiltBurgundyBright
import com.example.builtbyd.ui.theme.BuiltBurgundyDeep
import com.example.builtbyd.ui.theme.BuiltGold
import com.example.builtbyd.ui.theme.BuiltGoldLight
import com.example.builtbyd.ui.theme.BuiltGreenPulse
import com.example.builtbyd.ui.theme.BuiltIvorySoft
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Interactive3DCanvas(
    modifier: Modifier = Modifier
) {
    var rotY by remember { mutableFloatStateOf(0f) }
    var rotX by remember { mutableFloatStateOf(0.2f) }

    val infiniteTransition = rememberInfiniteTransition(label = "3d_anim")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A0809))
            .border(1.dp, BuiltGold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .testTag("interactive_3d_canvas_container")
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        rotY += dragAmount.x * 0.012f
                        rotX = (rotX + dragAmount.y * 0.008f).coerceIn(-0.6f, 0.8f)
                    }
                }
        ) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            // 1. Draw Perspective Grid (Gym Floor)
            val floorY = cy + 90f
            val gridLines = 10
            for (i in -gridLines..gridLines) {
                val xOffset = i * 40f
                val startP = project3D(xOffset, 120f, -180f, rotX, rotY, cx, floorY)
                val endP = project3D(xOffset, 120f, 220f, rotX, rotY, cx, floorY)
                drawLine(
                    color = Color(0x33D0A65A),
                    start = startP,
                    end = endP,
                    strokeWidth = 1f
                )
            }
            for (z in -4..5) {
                val zDepth = z * 45f
                val leftP = project3D(-240f, 120f, zDepth, rotX, rotY, cx, floorY)
                val rightP = project3D(240f, 120f, zDepth, rotX, rotY, cx, floorY)
                drawLine(
                    color = Color(0x2BD0A65A),
                    start = leftP,
                    end = rightP,
                    strokeWidth = 1f
                )
            }

            // 2. Gateway Portal Rings in background
            val portalZ = -120f
            val outerRadius = 140f
            val innerRadius = 115f
            val portalCenter = project3D(0f, -20f, portalZ, rotX, rotY, cx, cy)

            drawCircle(
                color = BuiltGold.copy(alpha = 0.65f),
                radius = outerRadius * (1f - portalZ / 600f),
                center = portalCenter,
                style = Stroke(width = 3f)
            )
            drawCircle(
                color = BuiltBurgundyBright.copy(alpha = 0.75f),
                radius = innerRadius * (1f - portalZ / 600f),
                center = portalCenter,
                style = Stroke(width = 2f)
            )

            // 3. Floating Energy Particles
            val particleCount = 28
            for (i in 0 until particleCount) {
                val pAngle = (i * (2 * PI / particleCount) + time * 0.4).toFloat()
                val pDist = 90f + (i % 5) * 20f
                val px = cos(pAngle) * pDist
                val py = sin(pAngle * 1.5f) * 40f - 10f
                val pz = sin(pAngle) * pDist
                val pPos = project3D(px, py, pz, rotX, rotY, cx, cy)
                val alpha = (0.3f + 0.4f * sin(time + i)).coerceIn(0.2f, 0.9f)
                drawCircle(
                    color = BuiltGoldLight.copy(alpha = alpha),
                    radius = (2.5f + (i % 3)),
                    center = pPos
                )
            }

            // 4. 3D Dumbbell (Left side)
            val dbX = -75f
            val dbY = -15f + sin(time * 2f) * 10f
            val dbZ = 20f
            val dbRot = time * 0.8f

            val dbCenter = project3D(dbX, dbY, dbZ, rotX, rotY, cx, cy)
            // Left weight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(BuiltGoldLight, Color(0xFF221A1D), Color(0xFF0F0B0D)),
                    center = dbCenter.copy(x = dbCenter.x - 30f),
                    radius = 35f
                ),
                radius = 28f,
                center = dbCenter.copy(x = dbCenter.x - 30f)
            )
            // Right weight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(BuiltGoldLight, Color(0xFF221A1D), Color(0xFF0F0B0D)),
                    center = dbCenter.copy(x = dbCenter.x + 30f),
                    radius = 35f
                ),
                radius = 28f,
                center = dbCenter.copy(x = dbCenter.x + 30f)
            )
            // Steel handle
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFE0E0E0), Color(0xFF888888), Color(0xFFDDDDDD))
                ),
                start = dbCenter.copy(x = dbCenter.x - 30f),
                end = dbCenter.copy(x = dbCenter.x + 30f),
                strokeWidth = 8f
            )

            // 5. 3D Gold Kettlebell (Right side)
            val kbX = 75f
            val kbY = -5f + cos(time * 1.8f) * 10f
            val kbZ = 30f
            val kbCenter = project3D(kbX, kbY, kbZ, rotX, rotY, cx, cy)

            // Kettlebell handle
            drawCircle(
                color = BuiltBurgundyDeep,
                radius = 18f,
                center = kbCenter.copy(y = kbCenter.y - 25f),
                style = Stroke(width = 6f)
            )
            // Kettlebell body (Golden metallic sphere)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(BuiltGoldLight, BuiltGold, BuiltBurgundyDeep, Color(0xFF140D10)),
                    center = kbCenter.copy(x = kbCenter.x - 8f, y = kbCenter.y - 8f),
                    radius = 36f
                ),
                radius = 30f,
                center = kbCenter
            )
        }

        // Overlay UI Pills
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(BuiltBlack.copy(alpha = 0.85f), RoundedCornerShape(20.dp))
                    .border(1.dp, BuiltGold.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(BuiltGreenPulse, CircleShape)
                    )
                    Text(
                        text = " 3D Loft Active",
                        color = BuiltGoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .background(BuiltBlack.copy(alpha = 0.85f), RoundedCornerShape(20.dp))
                .border(1.dp, BuiltGold.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = "Drag to Orbit 360°",
                color = BuiltIvorySoft,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = "Touch & drag to explore Dean'a House in 3D",
            color = BuiltIvorySoft.copy(alpha = 0.7f),
            fontSize = 11.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

private fun project3D(
    x: Float,
    y: Float,
    z: Float,
    rotX: Float,
    rotY: Float,
    cx: Float,
    cy: Float
): Offset {
    // Yaw (rotY around Y-axis)
    val cosY = cos(rotY)
    val sinY = sin(rotY)
    val x1 = x * cosY - z * sinY
    val z1 = z * cosY + x * sinY

    // Pitch (rotX around X-axis)
    val cosX = cos(rotX)
    val sinX = sin(rotX)
    val y2 = y * cosX - z1 * sinX
    val z2 = z1 * cosX + y * sinX

    val cameraDist = 450f
    val scale = cameraDist / (cameraDist + z2).coerceAtLeast(50f)
    return Offset(cx + x1 * scale, cy + y2 * scale)
}
