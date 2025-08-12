package requester

import com.patan.core.common.model.RestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import requester.builder.KRequesterBuilder
import requester.builder.KRequesterFlowBuilder

fun <T> Flow<T>.createRequesterFlowBuilder(scope: CoroutineScope, useWaitMillisecond: Boolean = true): KRequesterBuilder<T> {
    return KRequesterBuilder(scope = scope, request = this, useWaitMillisecond = useWaitMillisecond)
}

// Flow
fun <T> Flow<RestResult<T>>.createRequesterFlowBuilder(scope: CoroutineScope, useWaitMillisecond: Boolean = true): KRequesterFlowBuilder<T> {
    return KRequesterFlowBuilder(
        scope = scope,
        request = this,
        useWaitMillisecond = useWaitMillisecond
    )
}
