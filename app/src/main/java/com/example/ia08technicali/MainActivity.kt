package com.example.ia08technicali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import android.R.color



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                DrawingScreenThingy()
            }
        }
    }
}

// I put these here cuz my "a" key is broken so i got to use copy & paste to type "a"
// My intelligence is astounding
// a
// A

@Composable
fun DrawingScreenThingy() {
    val lines = remember { mutableStateListOf<Line>() }

    // Brush size state
    val strokeWidthState = remember { mutableStateOf(8f) }

    //Brush color state
    val selectedColor = remember { mutableStateOf(Color.Black) }
    Column(modifier = Modifier.fillMaxSize()) {

        // TOOL PANEL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Brush size: ${strokeWidthState.value.toInt()}")

            Slider(
                value = strokeWidthState.value,
                onValueChange = { strokeWidthState.value = it },
                valueRange = 1f..40f,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            Button(onClick = { lines.clear() }) {
                Text("Clear")
            }
        }

        // Color portion
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Color: ")

            Button(onClick = { selectedColor.value = Color.Black }) {
                Text("Black")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { selectedColor.value = Color.Red }) {
                Text("Red")
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { selectedColor.value = Color.Blue }) {
                Text("Blue")
            }
        }
        // DRAWING AREA
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        val line = Line(
                            start = change.position - dragAmount,
                            end = change.position,
                            strokeWidth = strokeWidthState.value.dp,
                            color = selectedColor.value
                        )

                        lines.add(line)
                    }
                }
        ) {
            lines.forEach { line ->
                drawLine(
                    color = line.color,
                    start = line.start,
                    end = line.end,
                    strokeWidth = line.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp
)
