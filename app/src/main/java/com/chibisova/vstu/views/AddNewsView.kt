package com.chibisova.vstu.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface AddNewView: MvpView {

    @AddToEnd
    fun showImg(url: String)

    @AddToEnd
    fun hideImg()

    @AddToEnd
    fun disableCreateNewBtn()

    @AddToEnd
    fun enableCreateNewBtn()

    @AddToEndSingle
    fun showAddImgDialog()

    @Skip
    fun clearFieldsAndImg()

    @AddToEndSingle
    fun showErrorSnackBar(messageError: String)

}