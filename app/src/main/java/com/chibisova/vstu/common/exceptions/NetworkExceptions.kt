package com.chibisova.vstu.common.exceptions

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Список ошибок при работе с сетью
 */
val NETWORK_EXCEPTIONS: List<Class<*>> = listOf(
    UnknownHostException::class.java,
    SocketTimeoutException::class.java,
    ConnectException::class.java
)
