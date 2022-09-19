package tk.aizydorczyk.kashubian.crud.model.dto

import com.fasterxml.jackson.databind.node.ObjectNode
import io.swagger.annotations.ApiModelProperty
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechType
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.IS_BLANK
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.IS_NULL
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.LENGTH_100_EXCEED
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.NOT_CONTAINS_AT_LEAST_ONE_MEANING
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.NOT_CONTAINS_ONLY_LETTERS_AND_SPACES
import tk.aizydorczyk.kashubian.crud.validator.CorrectVariationJsonFormatByPartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.validator.EntryExists
import tk.aizydorczyk.kashubian.crud.validator.HyperonimIdNotInUpdatedEntryMeaningsHyperonims
import tk.aizydorczyk.kashubian.crud.validator.HyperonimIdsCannotRepeatedInMeanings
import tk.aizydorczyk.kashubian.crud.validator.NotInUpdatedEntryBases
import tk.aizydorczyk.kashubian.crud.validator.NotInUpdatedEntryDerivatives
import tk.aizydorczyk.kashubian.crud.validator.NotUpdatedEntry
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import tk.aizydorczyk.kashubian.crud.validator.PartOfSpeechAndSubTypeConsistent
import tk.aizydorczyk.kashubian.crud.validator.UnchangedNormalizedWordToNonUnique
import tk.aizydorczyk.kashubian.crud.validator.UniqueNormalizedWord
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@CorrectVariationJsonFormatByPartOfSpeechSubType(groups = [OnCreate::class, OnUpdate::class])
@PartOfSpeechAndSubTypeConsistent(groups = [OnCreate::class, OnUpdate::class])
data class KashubianEntryDto(
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    @UniqueNormalizedWord(groups = [OnCreate::class])
    @UnchangedNormalizedWordToNonUnique(groups = [OnUpdate::class])
    @field:NotBlank(message = IS_BLANK, groups = [OnCreate::class, OnUpdate::class])
    @field:Size(max = 100, message = LENGTH_100_EXCEED, groups = [OnCreate::class, OnUpdate::class])
    @field:Pattern(regexp = "[\\p{L} ]+",
            message = NOT_CONTAINS_ONLY_LETTERS_AND_SPACES,
            groups = [OnCreate::class, OnUpdate::class])
    val word: String?,
    val note: String?,
    val priority: Boolean,
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    @ApiModelProperty(example = "NOUN")
    val partOfSpeech: PartOfSpeechType?,
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    @ApiModelProperty(example = "MASCULINE")
    val partOfSpeechSubType: PartOfSpeechSubType?,
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    @ApiModelProperty(dataType = "tk.aizydorczyk.kashubian.crud.model.json.NounVariation")
    val variation: ObjectNode?,
    @EntryExists(groups = [OnCreate::class, OnUpdate::class])
    @NotUpdatedEntry(groups = [OnUpdate::class])
    @NotInUpdatedEntryBases(groups = [OnUpdate::class])
    @NotInUpdatedEntryDerivatives(groups = [OnUpdate::class])
    var base: Long?,
    @field:Valid
    @field:NotEmpty(message = NOT_CONTAINS_AT_LEAST_ONE_MEANING, groups = [OnCreate::class, OnUpdate::class])
    @HyperonimIdNotInUpdatedEntryMeaningsHyperonims(groups = [OnUpdate::class])
    @HyperonimIdsCannotRepeatedInMeanings(groups = [OnUpdate::class])
    val meanings: List<MeaningDto> = emptyList(),
    @field:Valid
    val others: List<OtherDto> = emptyList()
)