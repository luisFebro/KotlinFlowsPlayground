package com.febro.kotlinflowsplayground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue

        emit(startingValue)

        while(currentValue > 0) {
            delay(1000L)
            currentValue--

            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        viewModelScope.launch {
            // latsetLatest
            countDownFlow.collectLatest { time ->
                // prints only the last value and others are cancelled
                delay(1500L)
                println("The current time is $time")
            }
        }
    }
}