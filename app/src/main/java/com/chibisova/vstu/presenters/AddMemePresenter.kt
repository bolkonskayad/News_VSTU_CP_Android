package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.domain.repository.MemeRepository
import com.chibisova.vstu.views.AddMemeView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.security.SecureRandom
import java.util.*
import javax.inject.Inject

class AddMemePresenter @Inject constructor(
    private val memeRepository: MemeRepository
) : BasePresenter<AddMemeView>() {

    private var titleMeme: String? = null
    private var descriptionMeme: String? = null
    private var photoMemeUrl: String? = null

    init {
        //По умолчанию делаем кнопку неактивной
        viewState.disableCreateMemeBtn()
    }

    fun updateTitle(title: String) {
        titleMeme = title
        checkFieldsAndImg()
    }

    fun updateDescription(description: String) {
        descriptionMeme = description
        checkFieldsAndImg()
    }

    fun updateImg(url: String) {
        photoMemeUrl = url
        viewState.showImg(url)
        checkFieldsAndImg()
    }

    //Проверяем поля на валидность
    private fun checkFieldsAndImg() {
        if (photoMemeUrl != null &&
            checkValidTitleInput(titleMeme) &&
            checkValidDescriptionInput(descriptionMeme)
        ) {
            viewState.enableCreateMemeBtn()
        } else {
            viewState.disableCreateMemeBtn()
        }
    }


    //Создание мема из данных и обновления бд
    fun createMeme() {
        val meme = Meme(
            SecureRandom().nextInt(),
            titleMeme!!,
            getCreatedData(),
            descriptionMeme!!,
            true,
            photoMemeUrl!!
        )
        //Добавляем мем в базу данных и очищаем поля в случае успешного добавления
        memeRepository.addMeme(meme)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { viewState.disableCreateMemeBtn() }
            .subscribe({
                clearData()
            }, {
                viewState.showErrorSnackBar("Ошибка при добавлении. Попробуйте снова")
            })
    }

    fun deleteImg() {
        photoMemeUrl = null
        viewState.hideImg()
        checkFieldsAndImg()
    }

    private fun clearData() {
        titleMeme = null
        descriptionMeme = null
        photoMemeUrl = null
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