/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann Mikami
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ymkm.android.kmvp.ui.mvp.lifecycle

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.LifecycleObserver
import org.ymkm.android.kmvp.Presenter
import org.ymkm.android.kmvp.PresenterView
import org.ymkm.android.kmvp.ui.mvp.BasePresenterActivity


abstract class BasePresenterLifecycleActivity<T : Presenter<V, P>, V : PresenterView, P : Parcelable> :
    BasePresenterActivity<T, V, P>() {

    private data class PresenterSaveInstance<T : Presenter<V, P>, V : PresenterView, P : Parcelable>(val presenter: T?)


    override fun onCreate(savedInstanceState: Bundle?) {
        createPresenter(savedInstanceState)
        if (presenter is LifecycleObserver) {
            lifecycle.addObserver(presenter as LifecycleObserver)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove after the destroy event has been dispatched
        if (presenter is LifecycleObserver) {
            lifecycle.removeObserver(presenter as LifecycleObserver)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return PresenterSaveInstance(presenter)
    }

    override fun createPresenter(savedBundle: Bundle?) {
        presenter = getSavedPresenter()
        super.createPresenter(savedBundle)
    }


    private fun getSavedPresenter(): T? {
        if (lastCustomNonConfigurationInstance == null) return null
        @Suppress("UNCHECKED_CAST")
        return (lastCustomNonConfigurationInstance as PresenterSaveInstance<T, V, P>).presenter
    }
}