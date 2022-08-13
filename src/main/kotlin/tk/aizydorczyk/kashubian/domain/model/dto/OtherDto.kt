package tk.aizydorczyk.kashubian.domain.model.dto

import tk.aizydorczyk.kashubian.domain.validator.EntryExists
import tk.aizydorczyk.kashubian.domain.validator.OnCreate
import tk.aizydorczyk.kashubian.domain.validator.OnUpdate
import javax.validation.constraints.NotNull

data class OtherDto(
    @EntryExists(groups = [OnCreate::class, OnUpdate::class])
    @field:NotNull(message = "ENTRY_ID_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    val entryId: Long,
    val note: String?
)