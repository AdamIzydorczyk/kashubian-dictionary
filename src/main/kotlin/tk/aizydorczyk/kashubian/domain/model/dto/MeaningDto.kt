package tk.aizydorczyk.kashubian.domain.model.dto

import tk.aizydorczyk.kashubian.domain.validator.MeaningExists
import tk.aizydorczyk.kashubian.domain.validator.OnCreate
import tk.aizydorczyk.kashubian.domain.validator.OnUpdate
import javax.validation.Valid

data class MeaningDto(
    @field:Valid
    val translation: TranslationDto?,
    val definition: String?,
    val origin: String?,
    @MeaningExists(groups = [OnCreate::class, OnUpdate::class])
    var base: Long?,
    @MeaningExists(groups = [OnCreate::class, OnUpdate::class])
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
