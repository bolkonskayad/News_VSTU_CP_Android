package com.chibisova.vstu.di.scopes

import javax.inject.Scope


/**
 * Скоуп это жизненный цикл объектов, внедренных с помощью DI(dagger2 внедрение зависимостей)
 * Данный скоуп озночает, что объекты помеченные этим скоупом, будут жить пока пользователь не авторизован
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentAuthScope