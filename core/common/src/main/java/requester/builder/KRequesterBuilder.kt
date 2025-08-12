package requester.builder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class KRequesterBuilder<T>(
    private val scope: CoroutineScope,
    private val request: Flow<T>,
    private val useWaitMillisecond: Boolean,
) {
    private var onLoading: (suspend () -> Unit)? = null
    private var onError: (suspend (Throwable) -> Unit)? = null
    private var onSuccess: (suspend (T) -> Unit)? = null

    fun onLoading(onLoading: suspend () -> Unit): KRequesterBuilder<T> {
        this.onLoading = onLoading
        return this
    }

    fun onError(onError: suspend (Throwable) -> Unit): KRequesterBuilder<T> {
        this.onError = onError
        return this
    }

    fun callWithSuccess(onSuccess: suspend (T) -> Unit) = scope.launch {
        this@KRequesterBuilder.onSuccess = onSuccess
        call()
    }

    private fun call() = scope.launch {
        request
            .onStart {
                onLoading?.invoke()
            }
            .catch {
                delay(if (useWaitMillisecond) REQUEST_RESPONSE_WAIT_MILLISECOND else 0)
                onError?.invoke(it)
            }
            .onEach {
                delay(if (useWaitMillisecond) REQUEST_RESPONSE_WAIT_MILLISECOND else 0)
                onSuccess?.invoke(it)
            }
            .launchIn(this)
    }

    companion object {
        private const val ONE_SECOND = 1000L
        private const val REQUEST_RESPONSE_WAIT_MILLISECOND = (ONE_SECOND * 0.5).toLong()
    }
}