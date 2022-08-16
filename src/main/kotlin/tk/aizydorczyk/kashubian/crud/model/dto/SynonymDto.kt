package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import javax.validation.constraints.NotNull

data class SynonymDto(
    @MeaningExists(groups = [OnCreate::class, OnUpdate::class])
    @field:NotNull(message = "MEANING_ID_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    val meaningId: Long,
    val note: String?
)