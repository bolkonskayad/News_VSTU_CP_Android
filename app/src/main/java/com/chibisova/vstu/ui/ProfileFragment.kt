package com.chibisova.vstu.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.base_view.BaseFragment
import com.chibisova.vstu.common.managers.SnackBarManager
import com.chibisova.vstu.ui.controllers.NewController
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.domain.model.User
import com.chibisova.vstu.navigation.NavigationAuth
import com.chibisova.vstu.navigation.NavigationNewDetails
import com.chibisova.vstu.presenters.ProfilePresenter
import com.chibisova.vstu.views.ProfileView
import kotlinx.android.synthetic.main.fragment_profile.*
import moxy.ktx.moxyPresenter
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import javax.inject.Inject
import javax.inject.Provider


class ProfileFragment : BaseFragment(), ProfileView {

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>
    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    @Inject
    lateinit var snackBarManager: SnackBarManager

    @Inject
    lateinit var navLogout: NavigationAuth

    @Inject
    lateinit var navNewDetailsFragment: NavigationNewDetails

    @Inject
    lateinit var easyAdapter: EasyAdapter

    @Inject
    lateinit var NewController: NewController

    private var dialogLogout: AlertDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentContentComponentOrCreateIfNull().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(profile_toolbar)
        getActionBar()?.title = ""
    }

    private fun initView() {
        NewController.NewDetailsClickListener = { presenter.openDetails(it) }
        NewController.shareClickListener = {
            presenter.shareNewInSocialNetwork(it)
        }
        with(news_list_profile_rv) {
            adapter = easyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bindProfile()
        presenter.loadNews()
    }


    override fun onDestroy() {
        super.onDestroy()
        dialogLogout?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                presenter.startLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showNews(newsList: List<News>) {
        val itemList = ItemList.create().apply {
            addAll(newsList, NewController)
        }
        easyAdapter.setItems(itemList)
    }

    override fun exitAccount() {
        navLogout.startAuthScreen()
    }

    override fun showProfile(user: User) {
        Glide.with(this)
            .load("https://img.pngio.com/avatar-1-length-of-human-face-hd-png-download-6648260-free-human-face-png-840_640.png")
            .circleCrop()
            .into(avatars_iv)
        nickname_tv.text = user.firstName
        description_profile_tv.text = user.descriptionProfile
    }

    override fun showDialog() {
        context?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            dialogLogout = builder.setTitle(R.string.dialogLogout_exit_btn)
                .setCancelable(false)
                .setMessage("")
                .setPositiveButton(
                    R.string.dialogLogout_exitAccount_btn
                ) { dialog, _ ->
                    dialog.dismiss()
                    presenter.logout()
                }
                .setNegativeButton(
                    R.string.dialogLogout_cancel_btn
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialogLogout?.show()
        }
    }

    override fun showErrorSnackBarDownloadProfile(message: String) {
        snackBarManager.showErrorMessage(message)
    }

    override fun showLoadState() {
        load_news_pb.visibility = View.VISIBLE
        news_list_profile_rv.visibility = View.GONE
    }


    override fun hideLoadState() {
        news_list_profile_rv.visibility = View.VISIBLE
        load_news_pb.visibility = View.GONE
    }

    override fun openNewDetails(data: News) {
        navNewDetailsFragment.startNewDetailsScreen(data)
    }

    override fun shareNew(News: News) {
        val shareNew = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, News.title)
            putExtra(Intent.EXTRA_STREAM, News.photoUrl)
            type = "image/*"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, null)
        startActivity(shareNew)
    }

    override fun getActionBar(): ActionBar? =
        (activity as AppCompatActivity).supportActionBar
}
