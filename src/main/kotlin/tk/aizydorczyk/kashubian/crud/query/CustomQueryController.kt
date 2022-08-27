package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.node.ObjectNode
import io.swagger.annotations.Api
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.ENTRY_ID
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.validator.EntryExists
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists

@RestController
@RequestMapping("custom-query")
@Validated
@Api("Custom Query", tags = ["CustomQuery"])
class CustomQueryController(val variationBySubtypeFinder: VariationBySubtypeFinder,
    val hyperonymsAndHyponymsFinder: HyperonymsAndHyponymsFinder,
    val basesAndDerivativesFinder: BasesAndDerivativesFinder,
    val wordOfTheDayFinder: WordOfTheDayFinder) {

    @GetMapping("variation-example/{partOfSpeechSubType}")
    fun getVariationExampleBySubType(@PathVariable partOfSpeechSubType: PartOfSpeechSubType): ObjectNode? =
        variationBySubtypeFinder.find(partOfSpeechSubType)

    @GetMapping("hyperonyms-and-hyponyms/{$MEANING_ID}")
    fun findHyperonymsAndHyponyms(@MeaningExists @PathVariable meaningId: Long) =
        hyperonymsAndHyponymsFinder.find(meaningId)

    @GetMapping("bases-and-derivatives/{$ENTRY_ID}")
    fun findBasesAndDerivatives(@EntryExists @PathVariable entryId: Long) = basesAndDerivativesFinder.find(entryId)

    @GetMapping("word-of-the-day")
    fun findWordOfTheDay() = wordOfTheDayFinder.find()
}