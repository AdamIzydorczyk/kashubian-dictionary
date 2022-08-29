package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.LENGTH_100_EXCEED
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class TranslationDto(
    @field:NotNull(message = ValidationMessages.IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    @field:Size(max = 100, message = LENGTH_100_EXCEED, groups = [OnCreate::class, OnUpdate::class])
    val polish: String?,
    @field:Size(max = 100, message = LENGTH_100_EXCEED, groups = [OnCreate::class, OnUpdate::class])
    val english: String?,
    @field:Size(max = 100, message = LENGTH_100_EXCEED, groups = [OnCreate::class, OnUpdate::class])
    val german: String?,
    @field:Size(max = 100, message = LENGTH_100_EXCEED, groups = [OnCreate::class, OnUpdate::class])
    val ukrainian: String?
)