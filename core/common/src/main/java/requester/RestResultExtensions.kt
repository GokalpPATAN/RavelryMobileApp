package requester

import com.patan.core.common.model.BaseError
import com.patan.core.common.model.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<RestResult<T>>.onSuccess(action: suspend (T, totalCount: Int?, filteredCount: Int?) -> Unit): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Success) {
            action.invoke(restResult.result, restResult.totalCount, restResult.filterCount)
        }
        emit(restResult)
    }
}

fun <T> Flow<RestResult<T>>.onError(action: suspend (BaseError) -> Unit): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Error) {
            action.invoke(restResult.error)
        }
        emit(restResult)
    }
}

fun <T> Flow<RestResult<T>>.onLoading(action: suspend () -> Unit): Flow<RestResult<T>> {
    return transform { restResult ->
        if (restResult is RestResult.Loading) {
            action.invoke()
        }
        emit(restResult)
    }
}
