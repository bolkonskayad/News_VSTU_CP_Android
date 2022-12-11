package com.chibisova.vstu.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.REQUEST_CODE_PERMISSION_CAMERA
import com.chibisova.vstu.common.REQUEST_CODE_PERMISSION_GALLERY
import com.chibisova.vstu.common.base_view.BaseFragment
import com.chibisova.vstu.common.managers.BottomBarVisible
import com.chibisova.vstu.common.managers.FileManager
import com.chibisova.vstu.common.managers.PermissionManager
import com.chibisova.vstu.common.managers.SnackBarManager
import com.chibisova.vstu.common.params.EXTRA_WAY_GET_IMG
import com.chibisova.vstu.navigation.NavigationBackPressed
import com.chibisova.vstu.presenters.AddNewsPresenter
import com.chibisova.vstu.ui.dialogs.AddImgDialog
import com.chibisova.vstu.views.AddNewView
import kotlinx.android.synthetic.main.fragment_add_news.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


/**
 * Фрагмент добавления новости
 */
class AddNewsFragment : BaseFragment(), AddNewView, View.OnClickListener {

    companion object {
        private const val REQUEST_DIALOG_WAY_GET_IMG = 100
        private const val REQUEST_CODE_CAMERA = 101
        private const val REQUEST_CODE_GALLERY = 102
    }

    @Inject
    lateinit var presenterProvider: Provider<AddNewsPresenter>
    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    @Inject
    lateinit var snackBarManager: SnackBarManager

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var fileManager: FileManager

    @Inject
    lateinit var bottomBarVisible: BottomBarVisible

    @Inject
    lateinit var navBack: NavigationBackPressed

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentContentComponentOrCreateIfNull().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initToolbar(view)
    }

    private fun initToolbar(view: View) {
        add_New_toolbar.navigationIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_close) }
        add_New_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(add_New_toolbar)
        add_New_toolbar.setNavigationOnClickListener { navBack.back() }
    }

    private fun initView(view: View) {
        create_New_btn.setOnClickListener(this)
        img_close_ibtn.setOnClickListener(this)
        add_img_ibtn.setOnClickListener(this)

        input_title_New_et.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
                {}

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.updateTitle(input_title_New_et.text.toString())
                }
            }
        )
        input_description_New_et.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
                {}

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.updateDescription(input_description_New_et.text.toString())
                }
            }
        )
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.img_close_ibtn -> presenter.deleteImg()
                R.id.create_New_btn -> presenter.createNew()
                R.id.add_img_ibtn -> showAddImgDialog()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bottomBarVisible.hideBottomNavigationBar()
    }

    override fun onStop() {
        super.onStop()
        bottomBarVisible.showBottomNavigationBar()
    }

    private fun gemImgFromGallery() {
        if (permissionManager.requestPermissionGallery()) {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY)
        }
    }

    private fun getImgFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    override fun showImg(url: String) {
        img_new_container.visibility = View.VISIBLE
        Glide.with(this).load(url).into(img_add_new_iv)
    }

    override fun hideImg() {
        img_new_container.visibility = View.GONE
    }

    override fun disableCreateNewBtn() {
        create_New_btn.isEnabled = false
    }

    override fun enableCreateNewBtn() {
        create_New_btn.isEnabled = true
    }

    override fun showAddImgDialog() {
        val addImgDialog = AddImgDialog()
        addImgDialog.setTargetFragment(this, REQUEST_DIALOG_WAY_GET_IMG)
        fragmentManager?.let {
            addImgDialog.show(it, EXTRA_WAY_GET_IMG)
        }
    }

    override fun clearFieldsAndImg() {
        input_title_New_et.text = null
        input_description_New_et.text = null
    }

    override fun showErrorSnackBar(messageError: String) {
        snackBarManager.showErrorMessage(messageError)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_DIALOG_WAY_GET_IMG -> {
                    //Получаем выбор нашего пользователя из диалога
                    val action = data?.getIntExtra(EXTRA_WAY_GET_IMG, 3)
                    if (action == AddImgDialog.CAMERA_MESSAGE) {
                        getImgFromCamera()
                    } else {
                        gemImgFromGallery()
                    }
                }
                REQUEST_CODE_CAMERA -> {
                    val imgBtmp = data?.extras?.get("data") as Bitmap
                    imgBtmp.let {
                        val url = fileManager.saveImg(it)
                        if (url != null) presenter.updateImg(url)
                    }
                }
                REQUEST_CODE_GALLERY -> {
                    data?.let {
                        presenter.updateImg(it.data.toString())
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION_CAMERA -> {
                if (grantResults.size > 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    getImgFromCamera()
                }
            }
            REQUEST_CODE_PERMISSION_GALLERY -> {
                if (grantResults.size > 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    gemImgFromGallery()
                }
            }
        }
    }

    override fun getActionBar(): ActionBar? = (activity as AppCompatActivity).supportActionBar
}
