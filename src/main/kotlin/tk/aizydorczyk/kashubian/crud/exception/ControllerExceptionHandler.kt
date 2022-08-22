package tk.aizydorczyk.kashubian.crud.exception

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import tk.aizydorczyk.kashubian.crud.model.dto.ValidationErrorDto
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ControllerExceptionHandler {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException): ValidationErrorDto {
        val groupedErrors = ex.bindingResult.allErrors
            .groupBy { it.javaClass.simpleName }
        val objectErrors = groupedErrors["ViolationObjectError"].orEmpty()
            .map { it as ObjectError }.map { it.defaultMessage.toString() }
        val fieldErrors = groupedErrors["ViolationFieldError"].orEmpty()
            .map { it as FieldError }.map { mapOf("message" to it.defaultMessage.toString(), "fieldName" to it.field) }
        val errorDto = ValidationErrorDto(objectErrors = objectErrors, fieldErrors = fieldErrors)
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException): ValidationErrorDto {
        val paramErrors = ex.constraintViolations.map {
            mapOf("message" to it.message.toString(),
                    "paramName" to it.propertyPath.toString().split(".", limit = 2).last())
        }
        val errorDto = ValidationErrorDto(paramErrors = paramErrors)
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SizeLimitExceededException::class)
    fun handleSizeLimitExceededException(
        ex: SizeLimitExceededException): ValidationErrorDto {
        val errorDto = ValidationErrorDto(paramErrors = listOf(mapOf("message" to "512_KB_LIMIT_EXCEEDED",
                "paramName" to "soundFile")))
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }

}

