package com.springboot.postgres.docker.kotlin.prometheus.grafana.app.util

import kotlin.reflect.KClass

class LogServiceWrapper(private val caller: KClass<out Any>) : LogService() {

    fun error(message: String) =
        super.error(caller, message)

    fun error(message: String, cause: Throwable?) =
        super.error(caller, message, cause)

    fun error(message: String, parameters: Map<Any, Any?>) =
        super.error(caller, message, parameters)

    fun error(message: String, parameters: Map<Any, Any?>, cause: Throwable?) =
        super.error(caller, message, parameters, cause)

    fun warn(message: String, cause: Throwable?) =
        super.warn(caller, message, cause)

    fun warn(message: String) =
        super.warn(caller, message)

    fun warn(message: String, parameters: Map<Any, Any?>) =
        super.warn(caller, message, parameters)

    fun warn(message: String, parameters: Map<Any, Any?>, cause: Throwable?) =
        super.warn(caller, message, parameters, cause)

    fun info(message: String) =
        super.info(caller, message)

    fun info(message: String, cause: Throwable?) =
        super.info(caller, message, cause)

    fun info(message: String, parameters: Map<Any, Any?>) =
        super.info(caller, message, parameters)

    fun debug(message: String) =
        super.debug(caller, message)

    fun debug(message: String, cause: Throwable?) =
        super.debug(caller, message, cause)

    fun debug(message: String, parameters: Map<Any, Any?>) =
        super.debug(caller, message, parameters)

    fun debug(message: String, parameters: Map<Any, Any?>, cause: Throwable?) =
        super.debug(caller, message, parameters, cause)

    fun trace(message: String, cause: Throwable?) =
        super.trace(caller, message, cause)

    fun trace(message: String, parameters: Map<Any, Any?>) =
        super.trace(caller, message, parameters)
}
