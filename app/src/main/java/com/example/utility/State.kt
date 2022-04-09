package com.example.utility

sealed class State<out T> {
    class Loading<T> : State<T>()
    class Success<T>(val data: T) : State<T>()
    class Failed<T>(val message: String) : State<T>()
    class Idle<T> : State<T>()
    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
        fun <T> idle() = Idle<T>()
    }
}