package com.febro.kotlinflowsplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.febro.kotlinflowsplayground.ui.theme.KotlinFlowsPlaygroundTheme
import kotlinx.coroutines.flow.collect

// ref: https://www.youtube.com/playlist?list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowsPlaygroundTheme {
                val viewModel by viewModels<MainViewModel>()

                val count by viewModel.stateFlow.collectAsState()
                // val time by viewModel.countDownFlow.collectAsState(initial = 10)

                // exp: https://youtu.be/za-EEkqJLCQ?t=1326
                LaunchedEffect(key1 = true) {
                    viewModel.sharedFlow.collect { number ->

                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Button(onClick = { viewModel.incrementCounter() }) {
                        Text(text = "Counter: $count")
                    }
//                    Text(
//                        text = time.toString(),
//                        fontSize =  30.sp,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinFlowsPlaygroundTheme {
        Greeting("Android")
    }
}