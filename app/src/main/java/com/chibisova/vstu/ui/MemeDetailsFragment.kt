package com.chibisova.vstu.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.base_view.BaseFragment
import com.chibisova.vstu.common.managers.BottomBarVisible
import com.chibisova.vstu.common.managers.StyleManager
import com.chibisova.vstu.navigation.NavigationBackPressed
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.domain.model.User
import com.chibisova.vstu.presenters.MemeDetailsPresenter
import com.chibisova.vstu.utils.getPostCreateDate
import com.chibisova.vstu.views.MemeDetailsView
import kotlinx.android.synthetic.main.fragment_meme_details.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class MemeDetailsFragment : BaseFragment(), MemeDetailsView {

    @Inject
    lateinit var presenterProvider: Provider<MemeDetailsPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var styleManager: StyleManager

    @Inject
    lateinit var navBack: NavigationBackPressed

    @Inject
    lateinit var bottomBarVisible: BottomBarVisible

    private val args: MemeDetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentContentComponentOrCreateIfNull().inject(this)
        styleManager.setColorStatusBar(R.color.colorPrimaryContent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meme_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        val meme = args.meme
        presenter.meme = meme

        presenter.bindMeme()
        presenter.bindUserInfoToolbar()
    }

    private fun initToolbar() {
        meme_details_toolbar.navigationIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_close) }
        (activity as AppCompatActivity).setSupportActionBar(meme_details_toolbar)
        getActionBar()?.title = null
        meme_details_toolbar.setNavigationOnClickListener { navBack.back() }
    }


    override fun onStart() {
        super.onStart()
        bottomBarVisible.hideBottomNavigationBar()

    }

    override fun onStop() {
        super.onStop()
        bottomBarVisible.showBottomNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_details_meme, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            presenter.shareMeme()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showErrorStateUserInfoToolbar() {
        nickname_mini_tv.text = getString(R.string.memeDetails_errorToolbarUser_message)
    }

    override fun showMeme(data: Meme) {
        title_meme_tv.text = data.title
        Glide.with(this).load(data.photoUrl).into(img_meme_iv)
        created_date_tv.text = getPostCreateDate(data.createdDate)
        if (data.isFavorite) {
            favorite_details_chb.isChecked = true
        }
        text_meme_tv.text = data.description
    }

    override fun getActionBar() = (activity as AppCompatActivity).supportActionBar

    override fun shareMeme(meme: Meme) {
        val shareMeme = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, meme.title)
            putExtra(Intent.EXTRA_STREAM, meme.photoUrl)
            type = "image/*"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }, null)
        startActivity(shareMeme)
    }

    override fun showUserInfoToolbar(user: User) {
        Glide.with(this)
            .load("https://img.pngio.com/avatar-1-length-of-human-face-hd-png-download-6648260-free-human-face-png-840_640.png")
            .optionalCircleCrop()
            .into(avatars_mini_iv)
        nickname_mini_tv.text = user.firstName
    }
}