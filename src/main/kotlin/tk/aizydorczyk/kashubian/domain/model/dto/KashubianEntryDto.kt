package tk.aizydorczyk.kashubian.domain.model.dto

import org.springframework.lang.NonNull
import tk.aizydorczyk.kashubian.domain.validator.EntryExists
import tk.aizydorczyk.kashubian.domain.validator.OnCreate
import tk.aizydorczyk.kashubian.domain.validator.OnUpdate
import tk.aizydorczyk.kashubian.domain.validator.UnchangedToNonUnique
import tk.aizydorczyk.kashubian.domain.validator.UniqueWord

@EntryExists(groups = [OnUpdate::class])
data class KashubianEntryDto(
    @NonNull
    @UniqueWord(groups = [OnCreate::class])
    @UnchangedToNonUnique(groups = [OnUpdate::class])
    val word: String,
    val note: String?,
    val partOfSpeech: String?,
    val genderNounType: String?,
    val variation: VariationDto?,
    val meanings: List<MeaningDto> = emptyList()
)