package com.deadzon.app.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val AmoledBlack = Color(0xFF040404)
val GlassRed = Color(0x33FF3131)
val GlassStroke = Color(0x66FF6666)

@Composable
fun DeadZonBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF030303), Color(0xFF0A0303), Color(0xFF030303))
                )
            )
    ) {
        content()
    }
}

@Composable
fun DeadZonLogoMark(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(88.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x88FF3D3D), Color(0x00FF3D3D))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(76.dp)) {
            val gradient = Brush.linearGradient(listOf(Color(0xFFFF2E2E), Color(0xFFAA0000)))
            drawRoundRect(
                brush = gradient,
                topLeft = Offset(size.width * 0.15f, size.height * 0.1f),
                size = androidx.compose.ui.geometry.Size(size.width * 0.7f, size.height * 0.8f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(26f, 26f)
            )
            drawRoundRect(
                color = AmoledBlack,
                topLeft = Offset(size.width * 0.32f, size.height * 0.26f),
                size = androidx.compose.ui.geometry.Size(size.width * 0.36f, size.height * 0.48f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f, 20f)
            )
            drawLine(
                color = AmoledBlack,
                start = Offset(size.width * 0.1f, size.height * 0.86f),
                end = Offset(size.width * 0.9f, size.height * 0.42f),
                strokeWidth = 11f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, GlassStroke, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GlassRed)
    ) {
        Box(
            Modifier.background(
                Brush.verticalGradient(
                    listOf(Color(0x26FFFFFF), Color.Transparent, Color(0x22B00000))
                )
            )
        ) {
            Column(modifier = Modifier.padding(contentPadding), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                content()
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String? = null) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        subtitle?.let {
            Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun MetricChip(icon: ImageVector, title: String, value: String) {
    GlassCard(contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color(0xFFFF6B6B), modifier = Modifier.size(18.dp))
            Column {
                Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(value, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun BottomActionBar(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(Color(0xE0100808))
            .border(1.dp, GlassStroke, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .padding(16.dp)
    ) {
        content()
    }
}
