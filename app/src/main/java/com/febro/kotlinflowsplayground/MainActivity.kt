package com.febro.kotlinflowsplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.febro.kotlinflowsplayground.ui.theme.KotlinFlowsPlaygroundTheme

// ref: https://www.youtube.com/playlist?list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowsPlaygroundTheme {
                val viewModel by viewModels<MainViewModel>()
                
                val time by viewModel.countDownFlow.collectAsState(initial = 10)


                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = time.toString(),
                        fontSize =  30.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
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