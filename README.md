# Mobile application "New Factory" for android

## Описание проекта
Приложение, для просмотров постов с мемами, которые приходят с мок сервера и отображаются в виде списка для пользователя.
Имеется возможность добавление мемов пользователем. 
Они будут сохранятся локально в базе данных

Пользователь может осуществлять поиск мемов в ленте

Постом из списка можно поделиться в социальных сетях

Для просмотра мемов, пользователю необходимо авторизироваться 

## Используемые паттерны, архитектурные решения и парадигмы

* __MVP__
* __Single Activity__
* __Repository__
* __Dependency injection__
* __OOP__
* __Reactive programming__
* __Clean architecture__ (Проект следует лишь некоторым принципам)

## Используемые технологии

* __Retrofit__
* [Dagger2](https://github.com/google/dagger)
* [RxJava](https://github.com/ReactiveX/RxJava)
* __Room__
* __Navigation architecture component__
* [Moxy](https://github.com/moxy-community/Moxy)
* [TextFieldBoxes](https://github.com/HITGIF/TextFieldBoxes)
* [Glide](https://github.com/bumptech/glide)
* [Easy Adapter](https://github.com/surfstudio/EasyAdapter)
* __kotlin android extensions__


## Обзор работы приложения и его пакетов

### Список пакетов

* [domain](#domain)
* [data](#data)
* [di](#di)
* [app](#app)
* [presenters](#presenters)
* [ui](#ui)
* [views](#views)
* [navigation](#navigation)
* [common](#common)
* [utils](#utils)

### domain

Данный пакет содержит контракты для слоя [data](#data) и доменую модель объектов для слоя презентации
Таким образом, [domain](#domain) содержит интерфейсы, которые [data](#data) обязан имплементировать.

P.s в данном слое нету итеракторов

### data

  * Пакет содержит сервисы для передачи данных по сети и работы с бд. 

  * Содержит dto для базы данных и для данных, которые приходят с mock сервера и мапперы для них

  * Для работы с бд реализован класс storage, который инкапуслирует логику работы с dao

  * Имлементирует репозитории, которые передают данные слою представления

  * Данные, пришедшие с сети добавляется в базу данных и репозиторий уже предоставляет поток с самой БД

  * По умолчанию, все операции репозитория и storage происходят в потоке io 

### di

  * В данном пакете содержится компоненты для даггера, модули и кастомные скоупы
     Для всего уровня приложения используются AppComponent, который действуте, пока приложение живое 
     Acitivty компонент, который уничтожается и обновляется в зависиости от состояние приложение
     Так, если мы перевернем экран, мы обновим ссылку на активность и, следовательно, на все интерфейсы, которая она реализует
     
     FragemntAuthComponent провайдит зависимости для сплэш экрана и фрагмента авторизации. В случае сплэш экрана, если пользователь авторизован, то он
     уничтожает компонент. Если нет, то мы переходим на экран авторизации, который и выполнит уничтожение компонента, при переходе на tabFragment 
     
     FragmentContentComponent провайдит завимости для наших основных экранов. TabFragment управляет скоупом и пересоздает его при перевороте экрана

  * В модулях содержится все объекты, помимо view, для внедрения, которые нужны приложению

  * Само древо зависимостей имеет 4 компонента, которые образуют следующую иерархию 

                         AppComponent
                          (Singleton)
                              |
                              |
                              |
                       ActivityComponent
                        (AcitivytScope)
                       /                \
                      /                  \
                     /                    \
                    /                      \
                   /                        \
                  /                          \
          FragmentAuthComponent    FragmentContentComponent
          (FragmentAuthScope)       (FragmentContentScope)



### App

  * Класс App служит для иннициализации и управлении жизненном циклом даггера

  * AppActivity иннициализирует основные компоненты и имплементирует интерфейсы навигации, и различных менеджеров из пакета [common](#common)

### ui

* Содержит MVP фрагменты, которые содержат реализацию опредленного интерфейса из пакета view. Такие как
    * __AddNewFragment__
        Фрагмент для добавления мемов в локальное хранилище
    * __AuthFragment__
        Фрагмент авторизации пользователя
        В случае успешной авторизации ветка FragmentAuthComponent уничтожается
    * __NewDetailsFragment__
        Экран детального просмотра мема
    * __NewsFeedFragment__
        Экран ленты мемов и поиска этих самых мемов
        Показывает ленту мемов при удачном запросе, а при ошибке выводит сообщение о неудачном запросе
    * __ProfileFragment__
        Содержится информацию о пользователя и ленту мемов пользователя, которые он создал локально
    * __SplashFragment__
      Сплэш экран с анимированной иконкой
      В случае, если пользователь авторизован, ветка FragmentAuthComponent уничтожается

* Содержит TabFragment, который является хостовым для экранов профиля, ленты мемов, добавления мема и детального просмотра. Контроллирует жизненый цикл FragmentContentComponent

* В пакете custom_views содержится ToolbarSearchView, который служит для поисковых запросов

* В пакете dialogs содержится класс кастомного диалог

* Пакет controllers содержит контроллеры для Easy Adapter

### presenters

+ __AddNewPresenter__
  
  * Презентер реагирует на изменение полей/добавление картинок. Обновляет внутренние поля и вызывает метод checkFieldsAndImg(.)
  Который активирует кнопку при валидном заполеннии всех полей
  
  * При добавлении мема, создается объект и добавляется в базу данных. В случае успешного добавления данные выполняется очистка полей в презнтере и данных в самом фрагменте

+ __NewFeedPresenter__
  * При иннициализации экрана вызывается метод loadNews, который имеет идентичный функционал с updateNews, кроме того факта, что второй вызывается при взаимодействии со swipeRefreshLayout

  * В случае __успешной__ загрузки мемов, они показываются пользователю. Если пользователь находится в режиме searchMode(Пользователю включил режим поиска мемов в тулбаре), то мемы фильтруются по заголовку. который ввел пользователь

  * В случае __неудачной__ загрузки, пользователю показывается заглушка и вызывается snackBar

  * Функции __startFilter__ и __stopFilter__ уведомляет презентер, что пользователь открыл/закрыл ToolbarSearchView


+ __AuthPresenter__

    * View уведомляет презентер об изменениях в веденном тексте и наличии/отсутствии фокуса на поле с паролем
    
    * Включает кнопку, которая управляет видимостью пароля, если поле ввода пароля не пустое или пользователь на нем сфокусирован

    * Принимает введенный текст, обновляет внутренние поля и проверяет валидность поля, обновляет состояние view
  
+ __NewDetailsPresenter__
  Показывает дополнительную информацию о меме в случае, если получены корректные данные
  
+ __ProfilePresenter__
  Выполняет запрос к UserRepository, беря информацию о пользователе, и отправляет ее для заполения view
  Выполняет запрос с NewRepository и показывает ленту мемов, созданных пользователем
  
+ __SplashPresenter__
  Запускает анимацию. Проверяет, есть ли авторизованный пользователь и после небольшой задержки запускает приложение

### views
Содержит различные команды для View, которые фрагменты обязаны имплеменитровать для корректной поддержки переворота экрана или навигации по бэкстэку

### navigation
Содержит интерфейсы для навигации, которые имплементируют фрагменты/активность, содержащие navHostContainer

### common
Содержит вспомогательные классы и менеджеры, которые явно нельзя отнести к отдельной категории

В папке base_view содержится абстрактные классы, которые содержат общую логику для фрагментов/презентеров и [views](#views)

### utils

Содержит утилиты для преобразования даты и работе с клавиатурой
