package org.ymkm.android.kmvp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner

fun ViewModelProviders.of(application: Application, viewModelStoreOwner: ViewModelStoreOwner) =
    of(application, viewModelStoreOwner, null)

fun ViewModelProviders.of(
    application: Application,
    viewModelStoreOwner: ViewModelStoreOwner,
    factory: ViewModelProvider.Factory?
): ViewModelProvider =
    ViewModelProvider(
        viewModelStoreOwner,
        factory ?: ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )