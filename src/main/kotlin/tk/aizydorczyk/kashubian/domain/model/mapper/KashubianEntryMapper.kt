package tk.aizydorczyk.kashubian.domain.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.NullValueMappingStrategy
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.domain.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.domain.model.entity.KashubianEntry
import tk.aizydorczyk.kashubian.domain.model.entity.Meaning
import tk.aizydorczyk.kashubian.domain.model.entity.Proverb

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
abstract class KashubianEntryMapper {

    abstract fun toEntity(dto: KashubianEntryDto): KashubianEntry
    abstract fun toEntity(dto: ProverbDto): Proverb

    fun longToMeaning(id: Long?): Meaning? = id?.let { Meaning(id = it) }
}