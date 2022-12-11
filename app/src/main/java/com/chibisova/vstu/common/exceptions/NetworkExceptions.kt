package com.chibisova.vstu.common.exceptions

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


val NETWORK_EXCEPTIONS: List<Class<*>> = listOf(
    UnknownHostException::class.java,
    SocketTimeoutException::class.java,
    ConnectException::class.java
)
