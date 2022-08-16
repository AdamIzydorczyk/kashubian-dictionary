package tk.aizydorczyk.kashubian.crud.model.dto

import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import tk.aizydorczyk.kashubian.crud.validator.CorrectVariationJsonFormatByPartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.validator.EntryExists
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import tk.aizydorczyk.kashubian.crud.validator.PartOfSpeechAndSubTypeConsistent
import tk.aizydorczyk.kashubian.crud.validator.UnchangedToNonUnique
import tk.aizydorczyk.kashubian.crud.validator.UniqueWord
import javax.validation.Valid
import javax.validation.constraints.NotNull

@EntryExists(groups = [OnUpdate::class])
@CorrectVariationJsonFormatByPartOfSpeechSubType(groups = [OnCreate::class, OnUpdate::class])
@PartOfSpeechAndSubTypeConsistent(groups = [OnCreate::class, OnUpdate::class])
data class KashubianEntryDto(
    @field:NotNull(message = "WORD_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    @UniqueWord(groups = [OnCreate::class])
    @UnchangedToNonUnique(groups = [OnUpdate::class])
    val word: String?,
    val note: String?,
    @field:NotNull(message = "PART_OF_SPEECH_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    val partOfSpeech: PartOfSpeechType?,
    @field:NotNull(message = "PART_OF_SPEECH_SUBTYPE_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @field:Valid
    val variation: VariationDto?,
    @field:Valid
    val meanings: List<MeaningDto> = emptyList(),
    @field:Valid
    val others: List<OtherDto> = emptyList()
)