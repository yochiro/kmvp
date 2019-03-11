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

package org.ymkm.android.kmvp.ui.mvp

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import org.ymkm.android.kmvp.Presenter
import org.ymkm.android.kmvp.PresenterView
import org.ymkm.android.kmvp.ui.mvp.lifecycle.LifecycleDelegate
import java.lang.ref.WeakReference

abstract class LifecycleAwareBasePresenter<PV : PresenterView, P : Parcelable> :
    LifecycleDelegate(), Presenter<PV, P>, ViewModelStoreOwner {

    override fun setArgs(params: P?): Boolean = false

    override fun injectPresenterView(presenterView: PV) {
        presenterViews.put(presenterView.hashCode(), WeakReference(presenterView))
    }

    override fun resetPresenterView(presenterView: PV) {
        presenterViews.remove(presenterView.hashCode())
    }

    override fun onRestore(savedBundle: Bundle) = Unit

    override fun onSave(outState: Bundle) = Unit

    override fun getViewModelStore(): ViewModelStore {
        check(lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            "Presenter isn't ready yet. You can't request ViewModel before onCreate call."
        }
        return mViewModelStore ?: ViewModelStore()
    }


    protected fun sendView(viewBlock: PV.() -> Unit) {
        for (i in 0 until presenterViews.size()) {
            val wpv = presenterViews.get(presenterViews.keyAt(i))
            wpv.get()?.viewBlock()
        }
    }


    private val presenterViews = SparseArray<WeakReference<PV>>()
    private var mViewModelStore: ViewModelStore? = null
}