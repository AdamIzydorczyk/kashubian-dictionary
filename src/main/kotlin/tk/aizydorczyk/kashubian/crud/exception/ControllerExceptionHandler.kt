package tk.aizydorczyk.kashubian.crud.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException
import tk.aizydorczyk.kashubian.crud.model.dto.ValidationErrorDto
import javax.naming.SizeLimitExceededException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ControllerExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException): ValidationErrorDto {
        val groupedErrors = ex.bindingResult.allErrors
            .groupBy { it.javaClass.simpleName }
        val objectErrors = groupedErrors["ViolationObjectError"].orEmpty()
            .map { it as ObjectError }
            .map { mapOf("message" to it.defaultMessage.toString(), "name" to it.objectName) }
        val fieldErrors = groupedErrors["ViolationFieldError"].orEmpty()
            .map { it as FieldError }
            .map { mapOf("message" to it.defaultMessage.toString(), "name" to it.field) }
        val errorDto = ValidationErrorDto(
                objectErrors = objectErrors.toSortedSet(compareBy { it.hashCode() }),
                fieldErrors = fieldErrors.toSortedSet(compareBy { it.hashCode() })
        )
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException): ValidationErrorDto {
        val paramErrors = ex.constraintViolations.map {
            mapOf("message" to it.message.toString(),
                    "name" to it.propertyPath.toString().split(".", limit = 2).last())
        }
        val errorDto = ValidationErrorDto(
                paramErrors = paramErrors.toSortedSet(compareBy { it.hashCode() }))
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SizeLimitExceededException::class)
    fun handleSizeLimitExceededException(
        ex: SizeLimitExceededException): ValidationErrorDto {
        val errorDto = ValidationErrorDto(
                paramErrors = setOf(mapOf(
                        "message" to "512_KB_LIMIT_EXCEEDED",
                        "name" to "soundFile")))
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException(
        ex: MaxUploadSizeExceededException): ValidationErrorDto {
        val errorDto = ValidationErrorDto(
                paramErrors = setOf(mapOf("message" to "512_KB_LIMIT_EXCEEDED",
                        "name" to "soundFile")))
        logger.info("Validation failed with errors: $errorDto")
        return errorDto
    }
}
