package tk.aizydorczyk.kashubian.crud.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT
import org.mapstruct.ReportingPolicy.IGNORE
import tk.aizydorczyk.kashubian.crud.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.crud.model.dto.OtherDto
import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.crud.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import tk.aizydorczyk.kashubian.crud.model.entity.Other
import tk.aizydorczyk.kashubian.crud.model.entity.Proverb

@Mapper(componentModel = "spring", nullValueMappingStrategy = RETURN_DEFAULT, unmappedTargetPolicy = IGNORE)
abstract class KashubianEntryMapper {
    abstract fun toEntity(dto: KashubianEntryDto): KashubianEntry
    abstract fun toEntity(dto: ProverbDto): Proverb

    @Mapping(target = "id", expression = "java(dto.getId() != null ? dto.getId() : 0L)")
    @Mapping(target = "kashubianEntries", ignore = true)
    abstract fun meaningDtoToMeaning(dto: MeaningDto): Meaning

    fun otherDtoToOther(otherDto: OtherDto): Other =
        Other(
                0L,
                otherDto.note,
                otherDto.entryId,
                0L
        )
}
