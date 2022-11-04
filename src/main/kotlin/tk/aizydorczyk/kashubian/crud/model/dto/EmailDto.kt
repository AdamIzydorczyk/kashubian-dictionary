package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class EmailDto(
    @field:NotNull(message = ValidationMessages.IS_NULL)
    @field:Email(message = ValidationMessages.NOT_EMAIL_FORMAT)
    val contactEmail: String?,
    @field:NotNull(message = ValidationMessages.IS_NULL)
    val subject: String?,
    @field:NotNull(message = ValidationMessages.IS_NULL)
    val content: String?)
