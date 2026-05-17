package tk.aizydorczyk.kashubian.crud.model.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT
import org.mapstruct.ReportingPolicy.IGNORE
import tk.aizydorczyk.kashubian.crud.model.dto.AntonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.ExampleDto
import tk.aizydorczyk.kashubian.crud.model.dto.IdiomDto
import tk.aizydorczyk.kashubian.crud.model.dto.MeaningDto
import tk.aizydorczyk.kashubian.crud.model.dto.ProverbDto
import tk.aizydorczyk.kashubian.crud.model.dto.QuoteDto
import tk.aizydorczyk.kashubian.crud.model.dto.SynonymDto
import tk.aizydorczyk.kashubian.crud.model.dto.TranslationDto
import tk.aizydorczyk.kashubian.crud.model.entity.Antonym
import tk.aizydorczyk.kashubian.crud.model.entity.Example
import tk.aizydorczyk.kashubian.crud.model.entity.Idiom
import tk.aizydorczyk.kashubian.crud.model.entity.Meaning
import tk.aizydorczyk.kashubian.crud.model.entity.Proverb
import tk.aizydorczyk.kashubian.crud.model.entity.Quote
import tk.aizydorczyk.kashubian.crud.model.entity.Synonym
import tk.aizydorczyk.kashubian.crud.model.entity.Translation

@Mapper(componentModel = "spring", nullValueMappingStrategy = RETURN_DEFAULT, unmappedTargetPolicy = IGNORE)
abstract class MeaningMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "kashubianEntries", ignore = true)
    abstract fun toEntity(dto: MeaningDto): Meaning

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "meaning", constant = "0L")
    abstract fun toEntity(dto: ProverbDto): Proverb

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "meaning", constant = "0L")
    abstract fun toEntity(dto: ExampleDto): Example

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "meaning", constant = "0L")
    abstract fun toEntity(dto: IdiomDto): Idiom

    @Mapping(target = "id", constant = "0L")
    @Mapping(target = "meaning", constant = "0L")
    abstract fun toEntity(dto: QuoteDto): Quote

    fun toEntity(dto: TranslationDto): Translation =
        Translation(0L, dto.polish, null, dto.english, null, dto.german, null, dto.ukrainian, null, 0L)

    fun synonymDtoToSynonym(synonymDto: SynonymDto): Synonym =
        Synonym(0L, synonymDto.note, synonymDto.meaningId, 0L)

    fun antonymDtoToAntonym(antonymDto: AntonymDto): Antonym =
        Antonym(0L, antonymDto.note, antonymDto.meaningId, 0L)
}
