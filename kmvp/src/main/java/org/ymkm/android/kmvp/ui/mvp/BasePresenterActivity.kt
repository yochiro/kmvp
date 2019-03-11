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

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import org.ymkm.android.kmvp.Presenter
import org.ymkm.android.kmvp.PresenterView
import org.ymkm.android.kmvp.impl.PresenterDelegate
import org.ymkm.android.kmvp.ui.BaseAndroidXActivity

@Suppress("MemberVisibilityCanBePrivate")
abstract class BasePresenterActivity<T : Presenter<V, P>, V : PresenterView, P : Parcelable> : BaseAndroidXActivity(),
    PresenterDelegate<T, P> {

    override val params: P?
        get() = handleIntentArgs(intent)


    override fun onCreate(savedInstanceState: Bundle?) {
        createPresenter(savedInstanceState)
        super.onCreate(savedInstanceState)
        PresenterDelegate.Builder.dispatchCreate(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter?.onSave(outState)
    }

    override fun onDestroy() {
        PresenterDelegate.Builder.dispatchDestroy(this)
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        presenter?.apply {
            val args = handleIntentArgs(intent)
            if (args != null) {
                // Update arguments when a new intent is sent
                setArgs(args)
            }
        }
    }


    protected open fun handleIntentArgs(intent: Intent): P? = null


    /**
     * Override if default behavior is not appropriate. MUST set the presenter variable.
     *
     * Default behavior : this class should be annotated with [org.ymkm.android.kmvp.UsePresenter] with value specifying the Presenter class to use.
     *
     * @param savedBundle previously saved bundle
     */
    protected open fun createPresenter(savedBundle: Bundle?) {
        if (presenter == null) {
            presenter = PresenterDelegate.Builder.newPresenter(javaClass, savedBundle)
        }
    }
}