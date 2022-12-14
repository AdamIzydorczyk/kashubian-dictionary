package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.IS_NULL
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.NotInUpdatedEntryMeaningsHyponims
import tk.aizydorczyk.kashubian.crud.validator.NotMeaningOfUpdatedEntry
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class MeaningDto(
    @field:Valid
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    val translation: TranslationDto?,
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    val definition: String?,
    val origin: String?,
    @field:MeaningExists(groups = [OnCreate::class, OnUpdate::class])
    @field:NotMeaningOfUpdatedEntry(groups = [OnUpdate::class])
    @field:NotInUpdatedEntryMeaningsHyponims(groups = [OnUpdate::class])
    var hyperonym: Long?,
    @field:Valid
    val proverbs: List<ProverbDto> = emptyList(),
    @field:Valid
    val idioms: List<IdiomDto> = emptyList(),
    @field:Valid
    val quotes: List<QuoteDto> = emptyList(),
    @field:Valid
    val examples: List<ExampleDto> = emptyList(),
    @field:Valid
    val synonyms: List<SynonymDto> = emptyList(),
    @field:Valid
    val antonyms: List<AntonymDto> = emptyList()
)
