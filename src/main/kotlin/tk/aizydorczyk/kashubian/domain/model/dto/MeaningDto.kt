package tk.aizydorczyk.kashubian.domain.model.dto

data class MeaningDto(
    val translation: TranslationDto?,
    val definition: String?,
    val origin: String?,
    var base: Long?,
    var superordinate: Long?,
    val proverbs: List<ProverbDto> = emptyList(),
    val phrasalVerbs: List<PhrasalVerbDto> = emptyList(),
    val quotes: List<QuoteDto> = emptyList(),
    val examples: List<ExampleDto> = emptyList(),
)
