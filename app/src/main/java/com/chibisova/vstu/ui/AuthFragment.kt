package com.chibisova.vstu.ui

import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.base_view.BaseFragment
import com.chibisova.vstu.common.EmptyFields
import com.chibisova.vstu.common.managers.SnackBarManager
import com.chibisova.vstu.navigation.NavigationContent
import com.chibisova.vstu.presenters.AuthPresenter
import com.chibisova.vstu.views.AuthView
import kotlinx.android.synthetic.main.fragment_auth.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


/**
 * Фрагмент авторизации
 */
class AuthFragment : BaseFragment(), AuthView {

    @Inject
    lateinit var presenterProvider: Provider<AuthPresenter>
    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    @Inject
    lateinit var snackBarManager: SnackBarManager

    @Inject
    lateinit var navigation: NavigationContent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentAuthComponentOrCreateIfNull().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        input_login_fb.setSimpleTextChangeWatcher { theNewText, _ ->
            presenter.updateLogin(theNewText)
        }

        input_password_fb.setSimpleTextChangeWatcher { theNewText, _ ->
            presenter.updatePassword(theNewText)
        }

        password_et.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                presenter.enableCheckPasswordField()
            } else {
                presenter.disableCheckPasswordField()
            }
        }

        auth_user_btn.setOnClickListener { authUser() }
        input_password_fb.endIconImageButton.setOnClickListener {
            presenter.changeVisiblePassword()
        }
    }

    private fun authUser() {
        presenter.authUser()
    }

    override fun enablePasswordField(isPasswordVisible: Boolean) {
        if (isPasswordVisible) {
            input_password_fb.setEndIcon(R.drawable.ic_eye_on)
        } else {
            input_password_fb.setEndIcon(R.drawable.ic_eye_off)
        }
    }

    override fun disablePasswordField() {
        input_password_fb.removeEndIcon()
    }

    override fun openContentFragment() {
        navigation.startContentScreen();
    }

    override fun showPassword() {
        input_password_fb.setEndIcon(R.drawable.ic_eye_on)
        password_et.transformationMethod = HideReturnsTransformationMethod.getInstance()
    }

    override fun hidePassword() {
        input_password_fb.setEndIcon(R.drawable.ic_eye_off)
        password_et.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    override fun showErrorSnackBar(messageError: String) {
        snackBarManager.showErrorMessage(messageError)
    }

    override fun showMessageErrorInputField(emptyFields: EmptyFields, messageError: String) {
        when (emptyFields) {
            EmptyFields.LOGIN -> {
                input_login_fb.setError(messageError, false)
            }
            EmptyFields.PASSWORD -> {
                input_password_fb.setError(messageError, false)
            }
            EmptyFields.ALL -> {
                input_login_fb.setError(messageError, false)
                input_password_fb.setError(messageError, false)
            }
        }
    }

    override fun hideMessageErrorInputField(emptyFields: EmptyFields) {
        when (emptyFields) {
            EmptyFields.LOGIN -> {
                input_login_fb.removeError()
            }
            EmptyFields.PASSWORD -> {
                input_password_fb.removeError()
            }
            EmptyFields.ALL -> {
                input_login_fb.removeError()
                input_password_fb.removeError()
            }
        }
    }

    override fun showPasswordHelper(lengthPassword: Int) {
        input_password_fb.helperText = "Длина пароля должна состоять из $lengthPassword символов"
    }

    override fun hidePasswordHelper() {
        input_password_fb.helperText = null
    }

    override fun showAuthProgressBar() {
        auth_user_btn.visibility = View.GONE
        auth_pb.visibility = View.VISIBLE
    }

    override fun hideAuthProgressBar() {
        auth_user_btn.visibility = View.VISIBLE
        auth_pb.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        App.instance.clearFragmentAuthComponent()
    }
    override fun getActionBar(): ActionBar? = null

}