package tk.aizydorczyk.kashubian.crud.validator

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.multipart.MultipartFile
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [AudioTypeValidator::class])
@Target(allowedTargets = [AnnotationTarget.VALUE_PARAMETER])
@Retention(AnnotationRetention.RUNTIME)
annotation class AudioType(
    val message: String = "NOT_AUDIO_EXTENSION_OR_MIME_TYPE",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [])

@Component
@RequestScope
class AudioTypeValidator : ConstraintValidator<AudioType, MultipartFile> {
    private val extensionList = listOf("m4a", "flac", "mp3", "wav", "wma", "aac")

    override fun isValid(soundFile: MultipartFile, context: ConstraintValidatorContext?): Boolean =
        isAudioFileExtension(soundFile) && isAudioContentType(soundFile)

    private fun isAudioContentType(soundFile: MultipartFile) =
        soundFile.contentType?.startsWith("audio/") == true

    private fun isAudioFileExtension(soundFile: MultipartFile): Boolean =
        soundFile.originalFilename?.split(".", limit = 2)?.last()?.lowercase().let { it in extensionList }
}
