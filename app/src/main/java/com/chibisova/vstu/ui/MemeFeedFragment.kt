package com.chibisova.vstu.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.RefresherOwner
import com.chibisova.vstu.common.base_view.BaseFragment
import com.chibisova.vstu.common.managers.InputModeManager
import com.chibisova.vstu.common.managers.SnackBarManager
import com.chibisova.vstu.common.managers.StyleManager
import com.chibisova.vstu.ui.controllers.MemeController
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.navigation.NavigationMemeDetails
import com.chibisova.vstu.presenters.MemesFeedPresenter
import com.chibisova.vstu.ui.custom_view.ToolbarSearchView
import com.chibisova.vstu.views.MemeFeedView
import kotlinx.android.synthetic.main.fragment_meme_feed.*
import moxy.ktx.moxyPresenter
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import javax.inject.Inject
import javax.inject.Provider


class MemeFeedFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, MemeFeedView,
    RefresherOwner {

    @Inject
    lateinit var presenterProvider: Provider<MemesFeedPresenter>
    private val presenter by moxyPresenter {
        presenterProvider.get()
    }


    @Inject
    lateinit var styleManager: StyleManager

    @Inject
    lateinit var inputModeManager: InputModeManager

    @Inject
    lateinit var snackBarManager: SnackBarManager

    @Inject
    lateinit var navMemeDetailsFragment: NavigationMemeDetails

    @Inject
    lateinit var easyAdapter: EasyAdapter

    @Inject
    lateinit var memeController: MemeController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentContentComponentOrCreateIfNull().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        styleManager.setColorStatusBar(R.color.colorPrimaryContent)
        return inflater.inflate(R.layout.fragment_meme_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
        initRecyclerView()
    }

    private fun initToolbar() {
        meme_feed_Stoolbar.onChangeSearchMode = object : ToolbarSearchView.OnChangeSearchModeListener {
            override fun onStartSearch() {
                presenter.startFilter()
            }

            override fun onStopSearch() {
                presenter.stopFilter()
                meme_feed_Stoolbar.clearSearchText()
            }
        }
        meme_feed_Stoolbar.onChangeSearchText = {
            presenter.updateSearchText(it)
        }

    }

    private fun initView() {
        with(refresh_container) {
            setColorSchemeColors(Color.BLACK)
            setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorAccent))
        }
        refresh_container.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {
        with(meme_list_rv) {
            adapter = easyAdapter
            layoutManager = LinearLayoutManager(context)
        }
        memeController.memeDetailsClickListener = { presenter.openDetails(it) }

        memeController.shareClickListener = {
            presenter.shareMemeInSocialNetworks(it)
        }
    }

    override fun onStart() {
        super.onStart()
        inputModeManager.setAdjustPan()
    }

    override fun onStop() {
        super.onStop()
        inputModeManager.setAdjustResize()
    }

    override fun showMemes(memeList: List<Meme>) {
        val itemList = ItemList.create().apply {
            addAll(memeList, memeController)
        }
        easyAdapter.setItems(itemList)
        meme_list_rv.visibility = View.VISIBLE
        state_error_tv.visibility = View.GONE
    }

    override fun showErrorState() {
        meme_list_rv.visibility = View.GONE
        state_error_tv.text = getString(R.string.errorDownloadMemeState_message)
        state_error_tv.visibility = View.VISIBLE
    }

    override fun showEmptyFilterErrorState() {
        meme_list_rv.visibility = View.GONE
        state_error_tv.visibility = View.VISIBLE
        state_error_tv.text = getString(R.string.meme_feed_empty_filter_message)
    }

    override fun showLoadState() {
        state_progress_container.visibility = View.VISIBLE
    }

    override fun hideLoadState() {
        state_progress_container.visibility = View.GONE
    }

    override fun showRefresh() {
        setRefresherState(true)
    }

    override fun hideRefresh() {
        setRefresherState(false)
    }

    override fun enableSearchView() {
        meme_feed_Stoolbar.enableSearchMode()
    }

    override fun disableSearchView() {
        meme_feed_Stoolbar.disableSearchMode()
    }

    override fun showErrorSnackbar(message: String) {
        snackBarManager.showErrorMessage(message)
    }

    override fun openMemeDetails(data: Meme) {
        navMemeDetailsFragment.startMemeDetailsScreen(data)
    }

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

    override fun onRefresh() {
        presenter.updateMemes()
    }

    override fun setRefresherState(refresherState: Boolean) {
        refresh_container.post { refresh_container.isRefreshing = refresherState }
    }
    override fun getActionBar() = (activity as AppCompatActivity).supportActionBar


}