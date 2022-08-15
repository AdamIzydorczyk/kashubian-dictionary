package tk.aizydorczyk.kashubian.domain.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy
import tk.aizydorczyk.kashubian.domain.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.domain.model.dto.OtherDto
import tk.aizydorczyk.kashubian.domain.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.domain.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.domain.model.dto.VariationDto
import tk.aizydorczyk.kashubian.domain.model.entity.Antonym
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Other
import tk.aizydorczyk.kashubian.domain.model.entity.Proverb
import tk.aizydorczyk.kashubian.domain.model.entity.Synonym
import tk.aizydorczyk.kashubian.domain.model.entity.Variation

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
abstract class KashubianEntryMapper {

    abstract fun toEntity(dto: KashubianEntryDto): KashubianEntry
    abstract fun toEntity(dto: ProverbDto): Proverb

    fun variationDtoToVariation(variationDto: VariationDto?): Variation? {
        if (variationDto == null) {
            return null
        }
        return Variation(0L, variationDto.variation!!, 0L)
    }

    fun otherDtoToOther(otherDto: OtherDto?): Other? =
        otherDto?.let {
            Other(0L,
                    it.note,
                    KashubianEntry(id = it.entryId,
                            word = null,
                            partOfSpeech = null,
                            partOfSpeechSubType = null,
                            soundFile = null,
                            variation = null))
        }

    fun synonymDtoToSynonym(synonymDto: SynonymDto?): Synonym? =
        synonymDto?.let {
            Synonym(0L,
                    it.note,
                    it.meaningId)
        }

    fun antonymDtoToAntonym(antonymDto: AntonymDto?): Antonym? =
        antonymDto?.let {
            Antonym(0L,
                    it.note,
                    it.meaningId)
        }
}