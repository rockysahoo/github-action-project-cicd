package github.action.project.cicd.util

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class LogService {

    fun setup(traceId: String, externalTraceId: String?) {
        MDC.put(Constants.TRACE_ID, traceId)
        MDC.put(Constants.EXTERNAL_TRACE_ID, externalTraceId ?: Constants.VALUE_NOT_AVAILABLE)
    }

    fun isErrorEnabled(caller: KClass<out Any>): Boolean {
        return LoggerFactory.getLogger(caller.qualifiedName).isErrorEnabled
    }

    fun error(caller: KClass<out Any>, message: String) {
        LoggerFactory.getLogger(caller.qualifiedName).error(message)
    }

    fun error(caller: KClass<out Any>, message: String, cause: Throwable? = null) {
        LoggerFactory.getLogger(caller.qualifiedName).error(message, cause)
    }

    fun error(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap()) {
        if (isErrorEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).error(appendParameters(message, parameters))
        }
    }

    fun error(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap(), cause: Throwable? = null) {
        if (isErrorEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).error(appendParameters(message, parameters), cause)
        }
    }

    fun isWarnEnabled(caller: KClass<out Any>): Boolean {
        return LoggerFactory.getLogger(caller.qualifiedName).isWarnEnabled
    }

    fun warn(caller: KClass<out Any>, message: String, cause: Throwable? = null) {
        LoggerFactory.getLogger(caller.qualifiedName).warn(message, cause)
    }

    fun warn(caller: KClass<out Any>, message: String) {
        LoggerFactory.getLogger(caller.qualifiedName).warn(message)
    }

    fun warn(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap()) {
        if (isWarnEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).warn(appendParameters(message, parameters))
        }
    }

    fun warn(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap(), cause: Throwable? = null) {
        if (isWarnEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).warn(appendParameters(message, parameters), cause)
        }
    }

    fun isInfoEnabled(caller: KClass<out Any>): Boolean {
        return LoggerFactory.getLogger(caller.qualifiedName).isInfoEnabled
    }

    fun info(caller: KClass<out Any>, message: String) {
        LoggerFactory.getLogger(caller.qualifiedName).info(message)
    }

    fun info(caller: KClass<out Any>, message: String, cause: Throwable? = null) {
        LoggerFactory.getLogger(caller.qualifiedName).info(message, cause)
    }

    fun info(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap()) {
        if (isInfoEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).info(appendParameters(message, parameters))
        }
    }

    fun isDebugEnabled(caller: KClass<out Any>): Boolean {
        return LoggerFactory.getLogger(caller.qualifiedName).isDebugEnabled
    }

    fun debug(caller: KClass<out Any>, message: String) {
        LoggerFactory.getLogger(caller.qualifiedName).debug(message)
    }

    fun debug(caller: KClass<out Any>, message: String, cause: Throwable? = null) {
        LoggerFactory.getLogger(caller.qualifiedName).debug(message, cause)
    }

    fun debug(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap()) {
        if (isDebugEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).debug(appendParameters(message, parameters), message)
        }
    }

    fun debug(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap(), cause: Throwable? = null) {
        if (isDebugEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).debug(appendParameters(message, parameters), message, cause)
        }
    }

    fun isTraceEnabled(caller: KClass<out Any>): Boolean {
        return LoggerFactory.getLogger(caller.qualifiedName).isTraceEnabled
    }

    fun trace(caller: KClass<out Any>, message: String, cause: Throwable? = null) {
        LoggerFactory.getLogger(caller.qualifiedName).trace(message, cause)
    }

    fun trace(caller: KClass<out Any>, message: String, parameters: Map<Any, Any?> = emptyMap()) {
        if (isTraceEnabled(caller)) {
            LoggerFactory.getLogger(caller.qualifiedName).trace(appendParameters(message, parameters), message)
        }
    }

    fun teardown() {
        MDC.clear()
    }

    fun getTraceId(): String {
        return MDC.get(Constants.TRACE_ID)
    }

    fun getExternalTraceId(): String {
        return MDC.get(Constants.EXTERNAL_TRACE_ID)
    }

    fun getForwardTraceId(): String {
        val externalTraceId = MDC.get(Constants.EXTERNAL_TRACE_ID) ?: ""
        return if (externalTraceId == Constants.VALUE_NOT_AVAILABLE)
            MDC.get(Constants.TRACE_ID)
        else externalTraceId
    }

    private fun appendParameters(message: String, parameters: Map<Any, Any?>): String {
        if (parameters.isEmpty()) return message

        return message.plus(" ").plus(
            parameters.entries.joinToString(
                prefix = "[",
                separator = ", ", postfix = "]"
            )
        )
    }
}

object Constants {
    const val EXTERNAL_TRACE_ID = "X-External-Trace-Id"
    const val TRACE_ID = "X-Trace-Id"
    const val VALUE_NOT_AVAILABLE = "N/A"
}

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