package com.chibisova.vstu.ui.controllers

import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.chibisova.vstu.R
import com.chibisova.vstu.domain.model.News
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Контроллер библиотеки easyAdapter
 * Выполняет отрисовку элемента списка
 */
class NewsController: BindableItemController<News, NewsController.Holder>() {

    var newsDetailsClickListener: ((News) -> Unit) = {}
    var shareClickListener: ((News) -> Unit) = {}

    var deleteClickListener: ((News) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup?) = Holder(parent)

    override fun getItemId(data: News) = data.id.hashCode().toString()

    inner class Holder(parent: ViewGroup?): BindableViewHolder<News>(
        parent,
        R.layout.list_item_news
    ) {
        private val photoNew: ImageView = itemView.findViewById(R.id.photo_New_iv)
        private val naNewme: TextView = itemView.findViewById(R.id.New_name_tv)
        private val favoriteBtn: CheckBox = itemView.findViewById(R.id.favorite_chb)
        private val shareBtn: Button = itemView.findViewById(R.id.share_btn)
        private val deleteBtn: Button = itemView.findViewById(R.id.delete_news_btn)

        override fun bind(data: News) {
            Glide.with(itemView).load(data.photoUrl).into(photoNew)
            naNewme.text = data.title
            if (data.isFavorite){
                favoriteBtn.isChecked = true
            }
            deleteBtn.isVisible = deleteClickListener != null
            itemView.setOnClickListener { newsDetailsClickListener(data) }
            shareBtn.setOnClickListener { shareClickListener(data) }
            deleteBtn.setOnClickListener { deleteClickListener?.invoke(data) }
        }
    }
}
