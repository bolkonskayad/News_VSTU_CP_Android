package com.chibisova.vstu.common.base_view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import moxy.MvpAppCompatFragment

/**
 * базовый фрагмент, унаследоанный от MvpAppCompatFragment
 * вызывает метод setHasOptionsMenu для показа меню(три точки на экране профиля)
 */
abstract class BaseFragment: MvpAppCompatFragment()  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    abstract fun getActionBar(): ActionBar?

}