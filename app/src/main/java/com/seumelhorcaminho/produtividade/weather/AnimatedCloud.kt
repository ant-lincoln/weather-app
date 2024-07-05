package com.seumelhorcaminho.produtividade.weather

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun AnimatedCloud(modifier: Modifier = Modifier) {
    val cloudColor = Color.LightGray

    // Animação das nuvens (movimento da esquerda para a direita)
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val cloudOffsetAnim = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // Desenhando as nuvens
    Canvas(modifier = modifier.fillMaxSize()) {
        val cloudOffset = cloudOffsetAnim.value * size.width
        drawCloud(cloudOffset, size.height, cloudColor)
    }
}

private fun DrawScope.drawCloud(offsetX: Float, height: Float, color: Color) {
    val cloudRadius = height / 4
    val cloudY = height / 4

    drawCircle(color = color, radius = cloudRadius, center = Offset(offsetX, cloudY))
    drawCircle(color = color, radius = cloudRadius, center = Offset(offsetX + cloudRadius * 2, cloudY))
    drawCircle(color = color, radius = cloudRadius, center = Offset(offsetX + cloudRadius, cloudY - cloudRadius / 2))
}


