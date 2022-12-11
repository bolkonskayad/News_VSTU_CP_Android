package com.chibisova.vstu.views

import com.chibisova.vstu.common.EmptyFields
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(value = AddToEndStrategy::class)
interface AuthView: MvpView {

    fun showPassword()

    fun hidePassword()

    fun showAuthProgressBar()

    fun hideAuthProgressBar()

    fun showPasswordHelper(lengthPassword: Int)

    fun hidePasswordHelper()

    fun showMessageErrorInputField(emptyFields: EmptyFields, messageError: String)

    fun hideMessageErrorInputField(emptyFields: EmptyFields)

    fun enablePasswordField(isPasswordVisible: Boolean)

    fun disablePasswordField()

    @Skip
    fun showErrorSnackBar(messageError: String)

    @Skip
    fun openContentFragment()


}