package com.naughtyenigma.intracranialmajor.api

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

    fun <R : Any> map(x: R): Result<R> {
        return when (this) {
            is Success -> Success(x)
            is Error -> this
            Loading -> Loading
        }
    }

    fun processUI(
        successBlock: (T) -> Unit,
        errorBlock: (Exception) -> Unit = {},
        noConnectivityBlock: (NoConnectivityException) -> Unit = {},
        loadingBlock: () -> Unit = {}
    ) {
        when (this) {
            is Success<T> -> successBlock.invoke(data)
            is Error -> if (exception is NoConnectivityException) {
                noConnectivityBlock.invoke(exception)
            } else {
                errorBlock.invoke(exception)
            }
            Loading -> loadingBlock.invoke()
        }
    }
}