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

package org.ymkm.android.kmvp.impl

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import org.ymkm.android.kmvp.Presenter
import org.ymkm.android.kmvp.PresenterView
import org.ymkm.android.kmvp.UsePresenter
import java.lang.ref.WeakReference
import java.lang.reflect.AnnotatedElement

interface PresenterDelegate<T : Presenter<out PresenterView, P>, P : Parcelable> {

    /**
     * For activities, guaranteed to be non null after [android.app.Activity.onCreate] and until after [android.app.Activity.onDestroy].
     */
    var presenter: T?

    val params: P?

    @Suppress("UNCHECKED_CAST")
    object Builder {

        fun <T : Presenter<V, P>, V : PresenterView, P : Parcelable> dispatchCreate(presenterDelegate: PresenterDelegate<T, P>, savedInstanceState: Bundle?) {
            val presenter = presenterDelegate.presenter ?: return
            val params = presenterDelegate.params
            presenter.setArgs(params)
            if (savedInstanceState != null) {
                presenter.onRestore(savedInstanceState)
            }
            if (PresenterView::class.java.isAssignableFrom(presenterDelegate.javaClass)) {
                try {
                    Builder.injectPresenterView(presenterDelegate, presenterDelegate as V)
                } catch (cce: ClassCastException) {
                    // Not a subclass of V
                }

            }
        }

        fun <T : Presenter<V, P>, V : PresenterView, P : Parcelable> dispatchDestroy(presenterDelegate: PresenterDelegate<T, P>) {
            if (PresenterView::class.java.isAssignableFrom(presenterDelegate.javaClass)) {
                try {
                    Builder.resetPresenterView(presenterDelegate, presenterDelegate as V)
                } catch (cce: ClassCastException) {
                    // Not a subclass of V
                }

            }
        }

        fun <T : Presenter<V, P>, V : PresenterView, P : Parcelable> injectPresenterView(presenter: T?, presenterView: V?) {
            if (presenter != null && presenterView != null) {
                presenter.injectPresenterView(presenterView)
            }
        }

        fun <D : PresenterDelegate<T, P>, T : Presenter<V, P>, V : PresenterView, P : Parcelable> injectPresenterView(delegate: D?, presenterView: V) {
            if (delegate != null) {
                injectPresenterView(delegate.presenter, presenterView)
            }
        }

        fun <T : Presenter<V, out Parcelable>, V : PresenterView> resetPresenterView(presenter: T?, presenterView: V?) {
            if (presenter != null && presenterView != null) {
                presenter.resetPresenterView(presenterView)
            }
        }

        fun <D : PresenterDelegate<T, P>, T : Presenter<V, P>, V : PresenterView, P : Parcelable> resetPresenterView(delegate: D?, presenterView: V) {
            if (delegate != null) {
                resetPresenterView(delegate.presenter, presenterView)
            }
        }

        fun <T : Presenter<out PresenterView, out Parcelable>> newPresenter(clazz: AnnotatedElement, savedBundle: Bundle?): T? {
            val usePresenter = clazz.getAnnotation(UsePresenter::class.java) ?: return null
            val presenterClass = usePresenter.value
            try {
                val p = presenterClass::class.java.newInstance() as T
                if (savedBundle != null) {
                    p.onRestore(savedBundle)
                }
                return p
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
    }
}

abstract class BasePresenter<PV : PresenterView, P : Parcelable> : Presenter<PV, P> {

    override fun setArgs(params: P?): Boolean = false

    override fun injectPresenterView(presenterView: PV) {
        presenterViews.put(presenterView.hashCode(), WeakReference(presenterView))
    }

    override fun resetPresenterView(presenterView: PV) {
        presenterViews.remove(presenterView.hashCode())
    }

    override fun onRestore(savedBundle: Bundle) = Unit

    override fun onSave(outState: Bundle) = Unit


    protected fun sendView(viewBlock: PV.() -> Unit) {
        for (i in 0 until presenterViews.size()) {
            val wpv = presenterViews.get(presenterViews.keyAt(i))
            wpv.get()?.viewBlock()
        }
    }


    private val presenterViews = SparseArray<WeakReference<PV>>()
}