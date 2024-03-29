package tk.aizydorczyk.kashubian.crud.query.rest

import com.fasterxml.jackson.databind.node.ObjectNode
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType

@RestController
@RequestMapping("custom-query")
@Validated
@Tag(name = "Custom Query")
class CustomQueryController(
        val variationBySubtypeFinder: VariationBySubtypeFinder,
        val wordOfTheDayFinder: WordOfTheDayFinder) {

    @GetMapping("variation-example/{partOfSpeechSubType}")
    fun getVariationExampleBySubType(@PathVariable partOfSpeechSubType: PartOfSpeechSubType): ObjectNode? =
            variationBySubtypeFinder.find(partOfSpeechSubType)

    @GetMapping("word-of-the-day")
    fun findWordOfTheDay() = wordOfTheDayFinder.find()
}