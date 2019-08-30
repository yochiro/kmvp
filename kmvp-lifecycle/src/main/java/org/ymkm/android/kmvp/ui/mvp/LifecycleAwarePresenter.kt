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

import android.os.Parcelable
import androidx.lifecycle.*
import org.ymkm.android.kmvp.Presenter
import org.ymkm.android.kmvp.PresenterView
import org.ymkm.android.kmvp.ui.mvp.lifecycle.LifecycleDelegate
import org.ymkm.android.kmvp.ui.mvp.lifecycle.LifecycleDelegateImpl

abstract class LifecycleAwarePresenter<PV : PresenterView, P : Parcelable>(
    private val presenter: Presenter<PV, P>,
    lifecycleDelegate: LifecycleDelegate = LifecycleDelegateImpl()
) :
    Presenter<PV, P> by presenter,
    LifecycleOwner by lifecycleDelegate,
    LifecycleObserver by lifecycleDelegate,
    ViewModelStoreOwner {

    override fun getViewModelStore(): ViewModelStore {
        check(lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            "Presenter isn't ready yet. You can't request ViewModel before onCreate call."
        }
        return if (presenter is ViewModelStoreOwner) {
            presenter.viewModelStore
        } else {
            mViewModelStore
        }
    }

    private val mViewModelStore by lazy {
        ViewModelStore()
    }
}
