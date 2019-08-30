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

object PresenterInjector {

    fun <T : Presenter<V, P>, V : PresenterView, P : Parcelable> dispatchCreate(
        presenter: T,
        presenterView: PresenterView,
        params: P?,
        savedInstanceState: Bundle?
    ) {
        presenter.setArgs(params)
        savedInstanceState?.apply {
            presenter.onRestore(this)
        }
        try {
            @Suppress("UNCHECKED_CAST")
            presenter.injectPresenterView(presenterView as V)
        } catch (ignored: ClassCastException) {
            // ignored
        }
    }

    fun <T : Presenter<V, P>, V : PresenterView, P : Parcelable> dispatchDestroy(
        presenter: T,
        presenterView: PresenterView
    ) {
        try {
            @Suppress("UNCHECKED_CAST")
            presenter.resetPresenterView(presenterView as V)
        } catch (ignored: ClassCastException) {
            // ignored
        }
    }

    fun <T : Presenter<out PresenterView, out Parcelable>> newPresenter(clazz: AnnotatedElement): T {
        val usePresenter = clazz.getAnnotation(UsePresenter::class.java)
            ?: error("$clazz needs to be annotated with UsePresenter")
        val presenterClass = usePresenter.value
        try {
            @Suppress("UNCHECKED_CAST")
            return presenterClass::class.java.newInstance() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
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
