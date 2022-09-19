package tk.aizydorczyk.kashubian.crud.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT
import org.mapstruct.ReportingPolicy.IGNORE
import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.entity.Antonym
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Other
import tk.aizydorczyk.kashubian.crud.model.entity.Proverb
import tk.aizydorczyk.kashubian.crud.model.entity.Synonym

@Mapper(componentModel = "spring", nullValueMappingStrategy = RETURN_DEFAULT, unmappedTargetPolicy = IGNORE)
abstract class KashubianEntryMapper {
    abstract fun toEntity(dto: KashubianEntryDto): KashubianEntry
    abstract fun toEntity(dto: ProverbDto): Proverb

    fun otherDtoToOther(otherDto: OtherDto?): Other? =
        otherDto?.let {
            Other(
                    0L,
                    it.note,
                    it.entryId,
                    0L
            )
        }

    fun synonymDtoToSynonym(synonymDto: SynonymDto?): Synonym? =
        synonymDto?.let {
            Synonym(
                    0L,
                    it.note,
                    it.meaningId,
                    0L
            )
        }

    fun antonymDtoToAntonym(antonymDto: AntonymDto?): Antonym? =
        antonymDto?.let {
            Antonym(
                    0L,
                    it.note,
                    it.meaningId,
                    0L
            )
        }
}