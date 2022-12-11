package com.chibisova.vstu.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface AddMemeView: MvpView {

    @AddToEnd
    fun showImg(url: String)

    @AddToEnd
    fun hideImg()

    @AddToEnd
    fun disableCreateMemeBtn()

    @AddToEnd
    fun enableCreateMemeBtn()

    @AddToEndSingle
    fun showAddImgDialog()

    @Skip
    fun clearFieldsAndImg()

    @AddToEndSingle
    fun showErrorSnackBar(messageError: String)

}