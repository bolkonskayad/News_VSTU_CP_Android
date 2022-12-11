package com.chibisova.vstu.ui.controllers

import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chibisova.vstu.R
import com.chibisova.vstu.domain.model.Meme
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class MemeController: BindableItemController<Meme, MemeController.Holder>() {

    var memeDetailsClickListener: ((Meme) -> Unit) = {}
    var shareClickListener: ((Meme) -> Unit) = {}

    override fun createViewHolder(parent: ViewGroup?) = Holder(parent)

    override fun getItemId(data: Meme) = data.id.hashCode().toString()

    inner class Holder(parent: ViewGroup?): BindableViewHolder<Meme>(
        parent,
        R.layout.list_item_mem
    ) {
        private val photoMeme: ImageView = itemView.findViewById(R.id.photo_meme_iv)
        private val nameMeme: TextView = itemView.findViewById(R.id.meme_name_tv)
        private val favoriteBtn: CheckBox = itemView.findViewById(R.id.favorite_chb)
        private val shareBtn: Button = itemView.findViewById(R.id.share_btn)

        override fun bind(data: Meme) {
            Glide.with(itemView).load(data.photoUrl).into(photoMeme)
            nameMeme.text = data.title
            if (data.isFavorite){
                favoriteBtn.isChecked = true
            }
            itemView.setOnClickListener { memeDetailsClickListener(data) }
            shareBtn.setOnClickListener { shareClickListener(data) }
        }
    }
}

/*
    Те, кто читает мой код явно найдут время для хорошего анекдота

    Идет мужик и видит пастуха, пасущего овец.
    Мужик:
    - Уважаемый, добрый день. А сколько ваши овцы дают шерсти за год?
    - Какие: черные или белые?
    - Черные.
    - 2 килограмма.
    - А белые?
    - Тоже 2.
    Удивился мужик, думает: дай, еще чего спрошу...
    - Уважаемый, а сколько ваши овцы съедают корма в день?
    - Какие: черные или белые?
    - Белые.
    - Килограмм.
    - А черные?
    - Тоже килограмм.
    Мужик растерялся:
    - Уважаемый, а почему вы все время спрашиваете, какие овцы, хотя результаты одинаковые?
    - Дык, черные овцы-то мои!
    - А белые?
    - Белые? Тоже мои.
 */