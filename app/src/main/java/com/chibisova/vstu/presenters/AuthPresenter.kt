package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.common.EmptyFields
import com.chibisova.vstu.data.dto.network.LoginUserRequest
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.common.exceptions.NETWORK_EXCEPTIONS
import com.chibisova.vstu.views.AuthView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AuthPresenter @Inject constructor(
    private val userRepository: UserRepository
) : BasePresenter<AuthView>() {

    companion object {
        private val LENGTH_PASSWORD = 6
    }

    private var login: String = ""
    private var password: String = ""

    private var isStateErrorInputFieldLogin: Boolean = false
    private var isStateErrorInputFieldPassword: Boolean = false

    private var isPasswordVisible: Boolean = false

    fun updateLogin(loginUser: String) {
        login = loginUser
        if (isStateErrorInputFieldLogin) {
            viewState.hideMessageErrorInputField(EmptyFields.LOGIN)
        }
    }

    fun updatePassword(passwordUser: String) {
        password = passwordUser
        if (isStateErrorInputFieldPassword) {
            viewState.hideMessageErrorInputField(EmptyFields.PASSWORD)
        }
        if (password.length <= 6) {
            viewState.showPasswordHelper(LENGTH_PASSWORD)
        } else {
            viewState.hidePasswordHelper()
        }
    }


    fun enableCheckPasswordField() {
        viewState.enablePasswordField(isPasswordVisible)
        viewState.showPasswordHelper(LENGTH_PASSWORD)
    }

    fun disableCheckPasswordField() {
        if (password.isEmpty()) {
            viewState.disablePasswordField()
        }
        if (password.length >= LENGTH_PASSWORD) viewState.hidePasswordHelper()
    }


    fun changeVisiblePassword() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            viewState.showPassword()
        } else {
            viewState.hidePassword()
        }
    }


    //Авторизуем юзера
    fun authUser() {
        if (checkFields()) {
            val userAuth = LoginUserRequest(login, password)
            userRepository.getUser(userAuth)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showAuthProgressBar() }
                .doFinally { viewState.hideAuthProgressBar() }
                .subscribe({
                    viewState.openContentFragment()
                }, {
                    if (NETWORK_EXCEPTIONS.contains(it.javaClass)) {
                        viewState.showErrorSnackBar(
                            "Отсутствует подключение к интернету " +
                                    "\nПодключитесь к сети и попробуйте снова"
                        )
                    } else {
                        viewState.showErrorSnackBar(
                            "Вы ввели неверные данные." +
                                    "\nПопробуйте еще раз"
                        )
                    }
                })

        }
    }

    //Проверяем наши введенные данные
    //Весьма сложная логика, да? Да
    private fun checkFields(): Boolean {
        if (login.isEmpty() || password.isEmpty()) {
            val messageError = "Поля не должны быть пустыми"
            if (login.isEmpty() && password.isEmpty()) {
                isStateErrorInputFieldLogin = true
                isStateErrorInputFieldPassword = true
                viewState.showMessageErrorInputField(EmptyFields.ALL, messageError)
            } else {
                if (login.isEmpty()) {
                    isStateErrorInputFieldLogin = true
                    viewState.showMessageErrorInputField(EmptyFields.LOGIN, messageError)
                }else if (login.isEmpty() && password.length < LENGTH_PASSWORD){
                    isStateErrorInputFieldPassword = true
                    isStateErrorInputFieldLogin = true
                    viewState.showMessageErrorInputField(
                        EmptyFields.PASSWORD,
                        "Пароль должен быть более чем из $LENGTH_PASSWORD символов"
                    )
                    viewState.showMessageErrorInputField(EmptyFields.PASSWORD, messageError)
                }else {
                    isStateErrorInputFieldPassword = true
                    viewState.showMessageErrorInputField(EmptyFields.PASSWORD, messageError)
                }
            }
            return false
        }
        if (password.length < LENGTH_PASSWORD) {
            isStateErrorInputFieldPassword = true
            viewState.showMessageErrorInputField(
                EmptyFields.PASSWORD,
                "Пароль должен быть более чем из $LENGTH_PASSWORD символов"
            )
            return false
        }
        isStateErrorInputFieldLogin = false
        isStateErrorInputFieldPassword = false
        return true
    }

}
