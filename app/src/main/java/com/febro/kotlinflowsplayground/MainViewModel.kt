package com.febro.kotlinflowsplayground

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
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

    private val flow1 = (1..10).asFlow().onEach { delay(1000L) }
    private val flow2 = (10..20).asFlow().onEach { delay(500L) }

    var numberString by mutableStateOf("")
        private set


    init {
        // collectFlow()

//        viewModelScope.launch {
//            sharedFlow.collect {
//                delay(2000L)
//                println("@@ First SharedFlow: The received number is $it")
//            }
//        }
//
//        viewModelScope.launch {
//            sharedFlow.collect {
//                delay(3000L)
//                println("@@ Second SharedFlow: The received number is $it")
//            }
//        }
//
//        squareNumber(2)

//        flow1.zip(flow2) { number1, number2 ->
//            numberString += "($number1, $number2)\n"
//        }.launchIn(viewModelScope)

        merge(flow1, flow2).onEach {
            numberString += "$it\n"
        }.launchIn(viewModelScope)
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