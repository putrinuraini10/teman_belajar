package com.example.temanbelajar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import com.example.temanbelajar.ui.theme.TemanbelajarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemanbelajarTheme {
                DragZoomImages()
            }
        }
    }
}

@Composable
fun DragZoomImages() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    fun Offset.limitToScreen(): Offset {
        return Offset(
            x.coerceIn(0f, screenWidthPx),
            y.coerceIn(0f, screenHeightPx)
        )
    }

    var offsetLogo by remember { mutableStateOf(Offset(screenWidthPx / 2, screenHeightPx / 2)) }
    var scaleLogo by remember { mutableStateOf(1f) }

    var offsetDesainBawah by remember { mutableStateOf(Offset(screenWidthPx / 2, screenHeightPx * 0.8f)) }
    var scaleDesainBawah by remember { mutableStateOf(1f) }

    var offsetDesainKanan by remember { mutableStateOf(Offset(screenWidthPx * 0.8f, screenHeightPx / 2)) }
    var scaleDesainKanan by remember { mutableStateOf(1f) }

    var offsetDesainAtas by remember { mutableStateOf(Offset(screenWidthPx / 2, screenHeightPx * 0.2f)) }
    var scaleDesainAtas by remember { mutableStateOf(1f) }

    Box(modifier = Modifier.fillMaxSize()) {

        // desainatas
        Image(
            painter = painterResource(id = R.drawable.desainatas),
            contentDescription = "Desain Atas",
            modifier = Modifier
                .offset { IntOffset(offsetDesainAtas.x.roundToInt(), offsetDesainAtas.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newOffset = (offsetDesainAtas + pan).limitToScreen()
                        offsetDesainAtas = newOffset
                        scaleDesainAtas = (scaleDesainAtas * zoom).coerceIn(0.5f, 3f)
                    }
                }
                .size((150 * scaleDesainAtas).dp)
        )

        // desainbawah
        Image(
            painter = painterResource(id = R.drawable.desainbawah),
            contentDescription = "Desain Bawah",
            modifier = Modifier
                .offset { IntOffset(offsetDesainBawah.x.roundToInt(), offsetDesainBawah.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newOffset = (offsetDesainBawah + pan).limitToScreen()
                        offsetDesainBawah = newOffset
                        scaleDesainBawah = (scaleDesainBawah * zoom).coerceIn(0.5f, 3f)
                    }
                }
                .size((150 * scaleDesainBawah).dp)
        )

        // desainkanan
        Image(
            painter = painterResource(id = R.drawable.desainkanan),
            contentDescription = "Desain Kanan",
            modifier = Modifier
                .offset { IntOffset(offsetDesainKanan.x.roundToInt(), offsetDesainKanan.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newOffset = (offsetDesainKanan + pan).limitToScreen()
                        offsetDesainKanan = newOffset
                        scaleDesainKanan = (scaleDesainKanan * zoom).coerceIn(0.5f, 3f)
                    }
                }
                .size((150 * scaleDesainKanan).dp)
        )

        // logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .offset { IntOffset(offsetLogo.x.roundToInt(), offsetLogo.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newOffset = (offsetLogo + pan).limitToScreen()
                        offsetLogo = newOffset
                        scaleLogo = (scaleLogo * zoom).coerceIn(0.5f, 3f)
                    }
                }
                .size((120 * scaleLogo).dp)
        )
    }
}
