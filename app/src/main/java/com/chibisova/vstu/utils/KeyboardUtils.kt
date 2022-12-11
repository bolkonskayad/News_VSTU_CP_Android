package com.chibisova.vstu.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText


object KeyboardUtil {

    fun showKeyboard(editText: TextInputEditText) {
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        editText.postDelayed({ imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT) }, 50)
    }

    /**
     * Скрыть экранную клавиатуру с вьюшки
     */
    fun hideSoftKeyboard(v: View) {
        val imm = v.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    /**
     * Скрыть экранную клавиатуру
     */
    fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}