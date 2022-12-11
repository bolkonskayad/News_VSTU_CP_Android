package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.common.exceptions.EmptyNewsDatabaseException
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.domain.repository.NewsRepository
import com.chibisova.vstu.common.exceptions.NETWORK_EXCEPTIONS
import com.chibisova.vstu.views.NewsFeedView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class NewsFeedPresenter @Inject constructor(
    private val newsRepository: NewsRepository
) : BasePresenter<NewsFeedView>() {

    init {
        loadNews()
    }

    private var newsList: List<News>? = null
    private var newsSearchList: List<News>? = null

    private var searchText: String = ""
    private var isSearchMode: Boolean = false

    private fun loadNews() {
        newsRepository.getNews()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoadState() }
            .doFinally { viewState.hideLoadState() }
            .subscribe({
                newsList = it
                showNews(it)
            }, {
                errorProcessing(it)
            })
    }

    fun updateNews() {
        newsRepository.getNews()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showRefresh() }
            .doFinally { viewState.hideRefresh() }
            .subscribe({
                newsList = it
                if (isSearchMode) {
                    filterNews()
                }else {
                    showNews(it)
                }
            }, {
                errorProcessing(it)
            })

    }


    private fun showNews(newsList: List<News>) {
        if (newsList.isNotEmpty()) {
            viewState.showNews(newsList)
        } else {
            errorProcessing(EmptyNewsDatabaseException())
        }
    }

    fun openDetails(News: News) {
        viewState.openNewDetails(News)
    }

    private fun errorProcessing(throwable: Throwable) {
        newsList = null
        if (NETWORK_EXCEPTIONS.contains(throwable.javaClass)) {
            viewState.showErrorSnackbar("Отсутствует подключение к интернету \nПодключитесь к сети и попробуйте снова")
        }
        viewState.showErrorState()
    }

    fun startFilter() {
        isSearchMode = true
        viewState.enableSearchView()
        filterNews()
    }

    fun updateSearchText(searchText: String) {
        this.searchText = searchText
        filterNews()
    }

    //Завершаем фильтрацию и показываем начальный список новости
    fun stopFilter() {
        isSearchMode = false
        searchText = ""
        viewState.disableSearchView()
        newsList?.let { viewState.showNews(it) }
        newsSearchList = null
    }

    private fun filterNews() {
        newsList?.let { NewList ->
            newsSearchList = NewList.filter { New ->
                New.title.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))
            }
            if (newsSearchList!!.isNotEmpty()) {
                showNews(newsSearchList!!)
            } else {
                viewState.showEmptyFilterErrorState()
            }
        }
    }

    fun shareNewInSocialNetworks(News: News) {
        viewState.shareNew(News)
    }

}