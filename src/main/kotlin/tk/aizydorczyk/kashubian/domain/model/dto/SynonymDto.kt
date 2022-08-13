package tk.aizydorczyk.kashubian.domain.model.dto

import tk.aizydorczyk.kashubian.domain.validator.MeaningExists
import tk.aizydorczyk.kashubian.domain.validator.OnCreate
import tk.aizydorczyk.kashubian.domain.validator.OnUpdate
import javax.validation.constraints.NotNull

data class SynonymDto(
    @MeaningExists(groups = [OnCreate::class, OnUpdate::class])
    @field:NotNull(message = "MEANING_ID_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    val meaningId: Long,
    val note: String?
)