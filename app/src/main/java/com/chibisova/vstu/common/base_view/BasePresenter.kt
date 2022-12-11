package com.chibisova.vstu.common.base_view

import io.reactivex.rxjava3.disposables.CompositeDisposable

import moxy.MvpPresenter
import moxy.MvpView

//Если ты, дорогой путник сюда добрался, то знай. этот фрагмент я создал,
// чтобы очищать потоки и выделять основные операции
//Но при добавлении CompositeDisposable и повторном его вызове ничего не получилось и операции вроде
//update не выполнялись
//Если ты знаешь в чем проблема, напиши мне
//Я презираю многострочные комменатрии
abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    protected val compositeDisposable = CompositeDisposable()

}