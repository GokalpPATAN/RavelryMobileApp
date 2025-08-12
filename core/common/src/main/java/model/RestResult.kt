package com.patan.core.common.model

sealed class RestResult<out T> {

    class Success<out T>(
        val result: T,
        val totalCount: Int? = null,
        val filterCount: Int? = null,
        val hasNext: Boolean? = null,
    ) : RestResult<T>()

    class Error(
        val error: BaseError,
    ) : RestResult<Nothing>()

    data class Progress(val progress: Int) : RestResult<Nothing>()
    data object Loading : RestResult<Nothing>()
}

inline fun <T> RestResult<T>.onSuccess(action: (T) -> Unit): RestResult<T> {
    if (this is RestResult.Success) action(result)
    return this
}

inline fun <T> RestResult<T>.onError(action: (exc: BaseError) -> Unit): RestResult<T> {
    if (this is RestResult.Error) action(error)
    return this
}

inline fun <T> RestResult<T>.onProgress(action: (progress: Int) -> Unit): RestResult<T> {
    if (this is RestResult.Progress) action(progress)
    return this
}

inline fun <T> RestResult<T>.onLoading(action: () -> Unit): RestResult<T> {
    if (this is RestResult.Loading) action()
    return this
}

inline fun <T, R> RestResult<T>.mapOnSuccess(map: (T) -> R): RestResult<R> = when (this) {
    is RestResult.Success -> RestResult.Success(map(result), totalCount, filterCount)
    is RestResult.Error -> this
    is RestResult.Progress -> this
    is RestResult.Loading -> this
}