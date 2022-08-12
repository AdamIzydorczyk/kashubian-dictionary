package tk.aizydorczyk.kashubian.domain.model.dto

import tk.aizydorczyk.kashubian.domain.validator.EntryExists
import tk.aizydorczyk.kashubian.domain.validator.OnCreate
import tk.aizydorczyk.kashubian.domain.validator.OnUpdate
import tk.aizydorczyk.kashubian.domain.validator.UnchangedToNonUnique
import tk.aizydorczyk.kashubian.domain.validator.UniqueWord
import javax.validation.Valid
import javax.validation.constraints.NotNull

@EntryExists(groups = [OnUpdate::class])
data class KashubianEntryDto(
    @field:NotNull(message = "WORD_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    @UniqueWord(groups = [OnCreate::class])
    @UnchangedToNonUnique(groups = [OnUpdate::class])
    val word: String?,
    val note: String?,
    val partOfSpeech: String?,
    @field:Valid
    val variation: VariationDto?,
    @field:Valid
    val meanings: List<MeaningDto> = emptyList()
)