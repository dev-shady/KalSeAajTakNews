package com.devshady

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class NewsApplication: Application() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}