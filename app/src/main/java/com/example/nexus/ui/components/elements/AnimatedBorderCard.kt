package com.example.nexus.ui.theme.components


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.NeonGreen

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 4.dp,
    borderGradient: Brush = Brush.sweepGradient(listOf(LightGreen, NeonGreen)),
    animationDuration: Int = 10000,
    shadowElevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    // Animação de rotação infinita
    val infiniteTransition = rememberInfiniteTransition(label = "Border Rotation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Degrees"
    )
    Surface(
        modifier = modifier,
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth) // Define a espessura da borda
                .drawWithContent {
                    // Rotaciona o gradiente para criar o efeito de movimento
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = borderGradient, // Aplica o gradiente
                            radius = size.width,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                    drawContent() // Desenha o conteúdo interno do card
                },
            color = DarkMediumGrey,
            shape = shape,
            shadowElevation = shadowElevation


        ) {
            content()
        }
    }
}

@Composable
@Preview
private fun AnimatedBorderCardPreview() {
    AnimatedBorderCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(2.dp),
        shape = RoundedCornerShape(30.dp),
        borderWidth = 2.dp,
        borderGradient = Brush.sweepGradient(
            listOf(
                LightGreen,
                NeonGreen
            )
        ),
        animationDuration = 10000,
    ) {

    }
}
