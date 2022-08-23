package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.node.ObjectNode
import io.swagger.annotations.Api
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.MEANING_ID
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType
import tk.aizydorczyk.kashubian.crud.validator.MeaningExists

@RestController
@RequestMapping("custom-query")
@Validated
@Api("Custom Query", tags = ["CustomQuery"])
class CustomQueryController(val variationBySubtypeFinder: VariationBySubtypeFinder,
    val hierarchyFinder: MeaningHierarchyFinder) {

    @GetMapping("variation-example/{partOfSpeechSubType}")
    fun getVariationExampleBySubType(@PathVariable partOfSpeechSubType: PartOfSpeechSubType): ObjectNode? =
        variationBySubtypeFinder.find(partOfSpeechSubType)

    @GetMapping("meaning-hierarchy/{$MEANING_ID}")
    fun findMeaningHierarchy(@MeaningExists @PathVariable meaningId: Long) = hierarchyFinder.find(meaningId)
}