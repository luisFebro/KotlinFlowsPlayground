package com.febro.kotlinflowsplayground

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue

        emit(startingValue)

        while (currentValue > 0) {
            delay(1000L)
            currentValue--

            emit(currentValue)
        }
    }

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>(5)
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        // collectFlow()

        viewModelScope.launch {
            sharedFlow.collect {
                delay(2000L)
                println("@@ First SharedFlow: The received number is $it")
            }
        }

        viewModelScope.launch {
            sharedFlow.collect {
                delay(3000L)
                println("@@ Second SharedFlow: The received number is $it")
            }
        }

        squareNumber(2)
    }

    fun squareNumber(number: Int) {
        viewModelScope.launch {
            _sharedFlow.emit(number * number)
        }
    }

    fun incrementCounter() {
        _stateFlow.value += 1
    }

    private fun collectFlow() {
//        countDownFlow.onEach {
//            println("The current time is $it (ONEACH)")
//        }.launchIn(viewModelScope)
//        val flow1 = flow<Int> {
//            emit(1)
//            delay(500L)
//            emit(2)
//        }

        // val flow1 = (1..5).asFlow()

        val flowRestaurant = flow {
            delay(50L)
            emit("Appetizer")

            delay(1000L)
            emit("Main Dish")

            delay(100L)
            emit("Dessert")

        }

        viewModelScope.launch {
            flowRestaurant.onEach {
                println("@@ $it is delivered")
            }
                .buffer()
                .collect {
                println("@@ Now eating $it")
                delay(1500L)
                println("@@ Finished eating $it")
            }
//                flow1.flatMapConcat { value ->
//                    flow {
//                        emit(value + 1)
//                        delay(500L)
//                        emit(value + 2)
//                    }
//                }.collect { value ->
//                    println("@@collected value is: $value")
//                }
//            val reduceResult = countDownFlow
//                .fold(100) { acc, curr ->
//                    acc + curr
//                }
//
//            println("@@The count is $reduceResult")
//            countDownFlow
//                .filter { time ->
//                    time % 2 == 0
//                }
//                .map { time ->
//                    time * time
//                }
//                .collect { time ->
//                println("@@The current time is $time")
//            }

//            val count = countDownFlow
//                .count {
//                    it % 2 == 0
//                }
//
//            println("@@The count is $count")

            // latsetLatest
//            countDownFlow.collectLatest { time ->
//                // prints only the last value and others are cancelled
//                delay(1500L)
//                println("The current time is $time")
//            }
        }
    }
}