package tk.aizydorczyk.kashubian.crud.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy
import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.VariationDto
import tk.aizydorczyk.kashubian.crud.model.entity.Antonym
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Other
import tk.aizydorczyk.kashubian.crud.model.entity.Proverb
import tk.aizydorczyk.kashubian.crud.model.entity.Synonym
import tk.aizydorczyk.kashubian.crud.model.entity.Variation

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
abstract class KashubianEntryMapper {

    abstract fun toEntity(dto: KashubianEntryDto): KashubianEntry
    abstract fun toEntity(dto: ProverbDto): Proverb

    fun variationDtoToVariation(variationDto: VariationDto?): Variation? =
        variationDto?.let { Variation(0L, variationDto.variation!!, 0L) }

    fun otherDtoToOther(otherDto: OtherDto?): Other? =
        otherDto?.let {
            Other(
                    0L,
                    it.note,
                    it.entryId
            )
        }

    fun synonymDtoToSynonym(synonymDto: SynonymDto?): Synonym? =
        synonymDto?.let {
            Synonym(
                    0L,
                    it.note,
                    it.meaningId
            )
        }

    fun antonymDtoToAntonym(antonymDto: AntonymDto?): Antonym? =
        antonymDto?.let {
            Antonym(
                    0L,
                    it.note,
                    it.meaningId
            )
        }
}