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

package org.ymkm.android.kmvp.ui

import android.app.Activity
import android.content.Context
import android.os.Build

import androidx.fragment.app.DialogFragment

// Make all fragments potential dialogs
abstract class BaseAndroidXFragment : DialogFragment() {

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttached()
        }
        super.onAttach(activity)
    }

    override fun onAttach(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onAttached()
        }
        super.onAttach(context)
    }


    /**
     * Called during onAttach based on the SDK level.
     *
     * - For M and above, called when onAttach(context: Context) is called.
     * - For lower versions, called when onAttach(activity: Activity) is called.
     */
    protected open fun onAttached() {}
}