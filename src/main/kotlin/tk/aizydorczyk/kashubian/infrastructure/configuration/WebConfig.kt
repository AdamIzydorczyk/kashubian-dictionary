package tk.aizydorczyk.kashubian.infrastructure.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.AsyncHandlerInterceptor
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import tk.aizydorczyk.kashubian.infrastructure.AuditingInformation
import java.lang.System.currentTimeMillis
import java.time.Clock
import java.time.LocalDateTime.now
import java.util.function.Supplier
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class WebConfig(val clockProvider: Supplier<Clock>) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(asyncHandlerInterceptor())
        super.addInterceptors(registry)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(AuditingInformationArgumentResolver(clockProvider))
        super.addArgumentResolvers(resolvers)
    }

    fun asyncHandlerInterceptor(): HandlerInterceptor = object : AsyncHandlerInterceptor {
        val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

        override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
            val currentUser = SecurityContextHolder.getContext().authentication.principal

            logger.debug("${request.method} ${request.requestURL} called by $currentUser")

            val startTime = currentTimeMillis()
            request.setAttribute(START_TIME, startTime)

            return super.preHandle(request, response, handler)
        }

        override fun afterCompletion(request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any,
            ex: Exception?) {
            val startTime = request.getAttribute(START_TIME) as Long
            val executionTime = currentTimeMillis() - startTime

            val currentUserName = SecurityContextHolder.getContext().authentication.name

            logger.info("${request.method} ${request.requestURL} executed in $executionTime ms by $currentUserName")

            super.afterCompletion(request, response, handler, ex)
        }

        override fun afterConcurrentHandlingStarted(request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any) {
            val startTime = request.getAttribute(START_TIME) as Long
            val executionTime = currentTimeMillis() - startTime

            val currentUserName = SecurityContextHolder.getContext().authentication.name

            logger.info("${request.method} ${request.requestURL} executed in $executionTime ms by $currentUserName")

            super.afterConcurrentHandlingStarted(request, response, handler)
        }
    }

    class AuditingInformationArgumentResolver(private val clockProvider: Supplier<Clock>) :
        HandlerMethodArgumentResolver {
        override fun supportsParameter(methodParameter: MethodParameter): Boolean {
            return methodParameter.parameterType == AuditingInformation::class.java
        }

        override fun resolveArgument(parameter: MethodParameter,
            mavContainer: ModelAndViewContainer?,
            webRequest: NativeWebRequest,
            binderFactory: WebDataBinderFactory?): Any {
            return AuditingInformation(webRequest.userPrincipal?.name ?: "anonymous", now(clockProvider.get()))
        }

    }

    companion object {
        const val START_TIME = "START_TIME"
    }
}