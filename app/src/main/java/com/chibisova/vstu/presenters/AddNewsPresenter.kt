package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.domain.repository.NewsRepository
import com.chibisova.vstu.views.AddNewView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.security.SecureRandom
import java.util.*
import javax.inject.Inject

class AddNewsPresenter @Inject constructor(
    private val newsRepository: NewsRepository
) : BasePresenter<AddNewView>() {

    private var titleNew: String? = null
    private var descriptionNew: String? = null
    private var photoNewUrl: String? = null

    init {
        //По умолчанию делаем кнопку неактивной
        viewState.disableCreateNewBtn()
    }

    fun updateTitle(title: String) {
        titleNew = title
        checkFieldsAndImg()
    }

    fun updateDescription(description: String) {
        descriptionNew = description
        checkFieldsAndImg()
    }

    fun updateImg(url: String) {
        photoNewUrl = url
        viewState.showImg(url)
        checkFieldsAndImg()
    }

    //Проверяем поля на валидность
    private fun checkFieldsAndImg() {
        if (photoNewUrl != null &&
            checkValidTitleInput(titleNew) &&
            checkValidDescriptionInput(descriptionNew)
        ) {
            viewState.enableCreateNewBtn()
        } else {
            viewState.disableCreateNewBtn()
        }
    }


    //Создание новости из данных и обновления бд
    fun createNew() {
        val News = News(
            SecureRandom().nextInt(),
            titleNew!!,
            getCreatedData(),
            descriptionNew!!,
            true,
            photoNewUrl!!
        )
        //Добавляем новость в базу данных и очищаем поля в случае успешного добавления
        newsRepository.addNews(News)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { viewState.disableCreateNewBtn() }
            .subscribe({
                clearData()
            }, {
                viewState.showErrorSnackBar("Ошибка при добавлении. Попробуйте снова")
            })
    }

    fun deleteImg() {
        photoNewUrl = null
        viewState.hideImg()
        checkFieldsAndImg()
    }

    private fun clearData() {
        titleNew = null
        descriptionNew = null
        photoNewUrl = null
        viewState.hideImg()
        viewState.clearFieldsAndImg()
    }

    //Получаем рандомную дату
    private fun getCreatedData(): Int {
        return Random().nextInt(10000)
    }

    private fun checkValidTitleInput(title: String?): Boolean =
        if (title != null) (title.length in 5..100)
        else false

    private fun checkValidDescriptionInput(description: String?): Boolean =
        if (description != null) (description.length in 10..999)
        else false


}