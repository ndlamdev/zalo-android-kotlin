package com.lamnguyen.zalo.utils.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(
    private val delayMillis: Long
) {
    private var debounceJob: Job? = null

    fun submit(action: () -> Unit) {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main)
            .launch {
                delay(delayMillis)
                action()
            }
    }

    fun submitSuspend(action: suspend () -> Unit) {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main)
            .launch {
                delay(delayMillis)
                action()
            }
    }
}
