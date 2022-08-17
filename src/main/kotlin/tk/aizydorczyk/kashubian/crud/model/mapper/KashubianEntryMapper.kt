package tk.aizydorczyk.kashubian.crud.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy
import tk.aizydorczyk.kashubian.crud.model.dto.*
import tk.aizydorczyk.kashubian.crud.model.entity.*

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