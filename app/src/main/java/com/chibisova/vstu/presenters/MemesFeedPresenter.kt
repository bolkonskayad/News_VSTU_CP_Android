package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.common.exceptions.EmptyMemesDatabaseException
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.domain.repository.MemeRepository
import com.chibisova.vstu.common.exceptions.NETWORK_EXCEPTIONS
import com.chibisova.vstu.views.MemeFeedView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class MemesFeedPresenter @Inject constructor(
    private val memeRepository: MemeRepository
) : BasePresenter<MemeFeedView>() {

    init {
        loadMemes()
    }

    private var memeList: List<Meme>? = null
    private var memeSearchList: List<Meme>? = null

    private var searchText: String = ""
    private var isSearchMode: Boolean = false

    private fun loadMemes() {
        memeRepository.getMemes()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoadState() }
            .doFinally { viewState.hideLoadState() }
            .subscribe({
                memeList = it
                showMemes(it)
            }, {
                errorProcessing(it)
            })
    }

    fun updateMemes() {
        memeRepository.getMemes()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showRefresh() }
            .doFinally { viewState.hideRefresh() }
            .subscribe({
                memeList = it
                if (isSearchMode) {
                    filterMemes()
                }else {
                    showMemes(it)
                }
            }, {
                errorProcessing(it)
            })

    }


    private fun showMemes(memeList: List<Meme>) {
        if (memeList.isNotEmpty()) {
            viewState.showMemes(memeList)
        } else {
            errorProcessing(EmptyMemesDatabaseException())
        }
    }

    fun openDetails(meme: Meme) {
        viewState.openMemeDetails(meme)
    }

    private fun errorProcessing(throwable: Throwable) {
        memeList = null
        if (NETWORK_EXCEPTIONS.contains(throwable.javaClass)) {
            viewState.showErrorSnackbar("Отсутствует подключение к интернету \nПодключитесь к сети и попробуйте снова")
        }
        viewState.showErrorState()
    }

    fun startFilter() {
        isSearchMode = true
        viewState.enableSearchView()
        filterMemes()
    }

    fun updateSearchText(searchText: String) {
        this.searchText = searchText
        filterMemes()
    }

    //Завершаем фильтрацию и показываем начальный список мемов
    fun stopFilter() {
        isSearchMode = false
        searchText = ""
        viewState.disableSearchView()
        memeList?.let { viewState.showMemes(it) }
        memeSearchList = null
    }

    private fun filterMemes() {
        memeList?.let { memeList ->
            memeSearchList = memeList.filter { meme ->
                meme.title.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))
            }
            if (memeSearchList!!.isNotEmpty()) {
                showMemes(memeSearchList!!)
            } else {
                viewState.showEmptyFilterErrorState()
            }
        }
    }

    fun shareMemeInSocialNetworks(meme: Meme) {
        viewState.shareMeme(meme)
    }

}