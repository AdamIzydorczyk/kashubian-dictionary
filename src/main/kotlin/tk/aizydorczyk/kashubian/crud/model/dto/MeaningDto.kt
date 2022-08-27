package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.IS_NULL
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists
import tk.aizydorczyk.kashubian.crud.validator.NotMeaningOfUpdatedEntry
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class MeaningDto(
    @field:Valid
    val translation: TranslationDto?,
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    val definition: String?,
    val origin: String?,
    @MeaningExists(groups = [OnCreate::class, OnUpdate::class])
    @NotMeaningOfUpdatedEntry(groups = [OnUpdate::class])
    var base: Long?,
    @MeaningExists(groups = [OnCreate::class, OnUpdate::class])
    @NotMeaningOfUpdatedEntry(groups = [OnUpdate::class])
    var hyperonym: Long?,
    @field:Valid
    val proverbs: List<ProverbDto> = emptyList(),
    @field:Valid
    val phrasalVerbs: List<PhrasalVerbDto> = emptyList(),
    @field:Valid
    val quotes: List<QuoteDto> = emptyList(),
    @field:Valid
    val examples: List<ExampleDto> = emptyList(),
    @field:Valid
    val synonyms: List<SynonymDto> = emptyList(),
    @field:Valid
    val antonyms: List<AntonymDto> = emptyList()
)
