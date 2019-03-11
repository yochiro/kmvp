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

package org.ymkm.android.kmvp.ui.mvp.lifecycle.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import java.lang.ref.WeakReference

abstract class LifecycleAwareApplication : Application(), LifecycleOwner, LifecycleObserver,
    Application.ActivityLifecycleCallbacks {

    var currentActivity: WeakReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        lifecycle.addObserver(this)
    }

    override fun getLifecycle(): Lifecycle {
        return ProcessLifecycleOwner.get().lifecycle
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        currentActivity = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onSentToBackground() {
        currentActivity = null
    }
}