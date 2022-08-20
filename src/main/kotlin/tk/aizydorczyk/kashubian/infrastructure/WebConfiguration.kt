package tk.aizydorczyk.kashubian.infrastructure

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebConfiguration : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(handlerInterceptor())
        super.addInterceptors(registry)
    }

    fun handlerInterceptor(): HandlerInterceptor = object : HandlerInterceptor {
        val logger: Logger = LoggerFactory.getLogger(javaClass)

        override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
            val currentUser = SecurityContextHolder.getContext().authentication.principal

            logger.info("${request.method} ${request.requestURL} called by $currentUser")

            val startTime = System.currentTimeMillis()
            request.setAttribute("START_TIME", startTime)

            return super.preHandle(request, response, handler)
        }

        override fun afterCompletion(request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any,
            ex: Exception?) {
            val startTime = request.getAttribute("START_TIME") as Long
            val executionTime = System.currentTimeMillis() - startTime

            val currentUser = SecurityContextHolder.getContext().authentication.principal

            logger.info("${request.method} ${request.requestURL} executed in $executionTime ms by $currentUser")

            super.afterCompletion(request, response, handler, ex)
        }
    }
}