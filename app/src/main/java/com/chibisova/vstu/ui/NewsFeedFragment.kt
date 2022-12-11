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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.common.RefresherOwner
import com.chibisova.vstu.common.base_view.BaseFragment
import com.chibisova.vstu.common.managers.InputModeManager
import com.chibisova.vstu.common.managers.SnackBarManager
import com.chibisova.vstu.ui.controllers.NewsController
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.navigation.NavigationNewDetails
import com.chibisova.vstu.presenters.NewsFeedPresenter
import com.chibisova.vstu.ui.custom_view.ToolbarSearchView
import com.chibisova.vstu.views.NewsFeedView
import kotlinx.android.synthetic.main.fragment_news_feed.*
import moxy.ktx.moxyPresenter
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import javax.inject.Inject
import javax.inject.Provider


class NewsFeedFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, NewsFeedView,
    RefresherOwner {

    @Inject
    lateinit var presenterProvider: Provider<NewsFeedPresenter>
    private val presenter by moxyPresenter {
        presenterProvider.get()
    }


    @Inject
    lateinit var inputModeManager: InputModeManager

    @Inject
    lateinit var snackBarManager: SnackBarManager

    @Inject
    lateinit var navNewDetailsFragment: NavigationNewDetails

    private val easyAdapter = EasyAdapter()

    private val newsController = NewsController()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentContentComponentOrCreateIfNull().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
        initRecyclerView()
    }

    private fun initToolbar() {
        New_feed_Stoolbar.onChangeSearchMode = object : ToolbarSearchView.OnChangeSearchModeListener {
            override fun onStartSearch() {
                presenter.startFilter()
            }

            override fun onStopSearch() {
                presenter.stopFilter()
                New_feed_Stoolbar.clearSearchText()
            }
        }
        New_feed_Stoolbar.onChangeSearchText = {
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
        with(news_list_rv) {
            adapter = easyAdapter
            layoutManager = LinearLayoutManager(context)
        }
        newsController.newsDetailsClickListener = { presenter.openDetails(it) }

        newsController.shareClickListener = {
            presenter.shareNewInSocialNetworks(it)
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

    override fun showNews(newsList: List<News>) {
        val itemList = ItemList.create().apply {
            addAll(newsList, newsController)
        }
        easyAdapter.setItems(itemList)
        news_list_rv.visibility = View.VISIBLE
        state_error_tv.visibility = View.GONE
    }

    override fun showErrorState() {
        news_list_rv.visibility = View.GONE
        state_error_tv.text = getString(R.string.errorDownloadNewState_message)
        state_error_tv.visibility = View.VISIBLE
    }

    override fun showEmptyFilterErrorState() {
        news_list_rv.visibility = View.GONE
        state_error_tv.visibility = View.VISIBLE
        state_error_tv.text = getString(R.string.New_feed_empty_filter_message)
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
        New_feed_Stoolbar.enableSearchMode()
    }

    override fun disableSearchView() {
        New_feed_Stoolbar.disableSearchMode()
    }

    override fun showErrorSnackbar(message: String) {
        snackBarManager.showErrorMessage(message)
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

    override fun onRefresh() {
        presenter.updateNews()
    }

    override fun setRefresherState(refresherState: Boolean) {
        refresh_container.post { refresh_container.isRefreshing = refresherState }
    }
    override fun getActionBar() = (activity as AppCompatActivity).supportActionBar


}