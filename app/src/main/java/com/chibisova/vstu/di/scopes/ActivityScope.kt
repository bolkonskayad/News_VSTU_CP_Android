package com.chibisova.vstu.di.scopes

import javax.inject.Scope

/**
 * Скоуп это жизненный цикл объектов, внедренных с помощью DI(dagger2 внедрение зависимостей)
 * Данный скоуп озночает, что объекты помеченные этим скоупом, будут видны пока живет активность
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope