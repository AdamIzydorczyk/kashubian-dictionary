package tk.aizydorczyk.kashubian.crud.model.dto

import io.swagger.annotations.ApiModelProperty
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
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@EntryExists(groups = [OnUpdate::class])
@CorrectVariationJsonFormatByPartOfSpeechSubType(groups = [OnCreate::class, OnUpdate::class])
@PartOfSpeechAndSubTypeConsistent(groups = [OnCreate::class, OnUpdate::class])
data class KashubianEntryDto(
    @field:NotNull(message = "IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    @UniqueWord(groups = [OnCreate::class])
    @UnchangedToNonUnique(groups = [OnUpdate::class])
    @field:NotBlank(message = "IS_BLANK", groups = [OnCreate::class, OnUpdate::class])
    @field:Size(max = 100, message = "LENGTH_100_EXCEED", groups = [OnCreate::class, OnUpdate::class])
    val word: String?,
    val note: String?,
    val priority: Boolean,
    @field:NotNull(message = "PART_OF_SPEECH_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    @ApiModelProperty(example = "NOUN")
    val partOfSpeech: PartOfSpeechType?,
    @field:NotNull(message = "PART_OF_SPEECH_SUBTYPE_IS_NULL", groups = [OnCreate::class, OnUpdate::class])
    @ApiModelProperty(example = "MASCULINE")
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @field:Valid
    val variation: VariationDto?,
    @field:Valid
    val meanings: List<MeaningDto> = emptyList(),
    @field:Valid
    val others: List<OtherDto> = emptyList()
)