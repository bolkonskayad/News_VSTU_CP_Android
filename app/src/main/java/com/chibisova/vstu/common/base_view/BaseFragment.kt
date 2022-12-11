package com.chibisova.vstu.common.base_view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import moxy.MvpAppCompatFragment


//Если ты, дорогой путник сюда добрался, то знай. этот фрагмент я создал, чтобы очищать потоки в презентере
abstract class BaseFragment: MvpAppCompatFragment()  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    abstract fun getActionBar(): ActionBar?

}