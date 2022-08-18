package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ProverbDto(
    @field:NotNull(message = "IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    @field:Size(max = 150, message = "LENGTH_150_EXCEED", groups = [OnCreate::class, OnUpdate::class])
    val proverb: String,
    val note: String)
