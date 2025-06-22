package com.example.temanbelajar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
                DragLogo()
            }
        }
    }
}

@Composable
fun DragLogo() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val draggableStateX = rememberDraggableState { delta ->
        offsetX += delta
    }
    val draggableStateY = rememberDraggableState { delta ->
        offsetY += delta
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo aplikasi",
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .draggable(
                    state = draggableStateX,
                    orientation = androidx.compose.foundation.gestures.Orientation.Horizontal
                )
                .draggable(
                    state = draggableStateY,
                    orientation = androidx.compose.foundation.gestures.Orientation.Vertical
                )
                .size(120.dp)
        )
    }
}
