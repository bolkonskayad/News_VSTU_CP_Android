package com.chibisova.vstu.ui.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.chibisova.vstu.R
import com.chibisova.vstu.utils.KeyboardUtil
import kotlinx.android.synthetic.main.view_serach_toolbar.view.*


/**
 * Кастомная вью, которая представляет собой тулбар с поиском
 */
class ToolbarSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : Toolbar(context, attrs, defStyle) {

    var onChangeSearchMode: OnChangeSearchModeListener? = null

    var onChangeSearchText: ((String) -> Unit)? = null

    interface OnChangeSearchModeListener {
        fun onStartSearch()

        fun onStopSearch()
    }

    init {
        inflate(context, R.layout.view_serach_toolbar, this)
        val attr = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ToolbarSearchView, 0, 0
        )
        title = attr.getString(R.styleable.ToolbarSearchView_titleToolbar)
        title_search_tv.text = title
        attr.recycle()
        initLogicToolbar()
        initChangeSearchTextListener()
    }



    private fun initLogicToolbar() {
        search_Ibtn.setOnClickListener {
            onChangeSearchMode?.onStartSearch()
        }
        close_search_Ibtn.setOnClickListener {
            onChangeSearchMode?.onStopSearch()
        }
        clear_text_Ibtn.setOnClickListener {
            if (input_title_New_et.text.toString().isEmpty()) {
                onChangeSearchMode?.onStopSearch()
            } else {
                input_title_New_et.text?.clear()
            }
        }
    }

    fun clearSearchText(){
        input_title_New_et.text?.clear()
    }


    fun enableSearchMode() {
        openSearch()
    }

    fun disableSearchMode() {
        closeSearch()
    }

    private fun openSearch() {
        title_container.visibility = View.GONE
        search_container.visibility = View.VISIBLE
        input_title_New_et.requestFocus()
        KeyboardUtil.showKeyboard(input_title_New_et)
    }

    private fun closeSearch() {
        search_container.visibility = View.GONE
        title_container.visibility = View.VISIBLE
        KeyboardUtil.hideSoftKeyboard(this)
    }

    private fun initChangeSearchTextListener() {
        input_title_New_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(e: Editable?) {
                onChangeSearchText?.invoke(input_title_New_et.text.toString())
            }
        })
    }

}