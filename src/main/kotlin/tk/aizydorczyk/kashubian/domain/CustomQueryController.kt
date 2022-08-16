package tk.aizydorczyk.kashubian.domain

import com.fasterxml.jackson.databind.node.ObjectNode
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.domain.model.value.PartOfSpeechSubType

@RestController
@RequestMapping("custom-query")
@Api("Custom Query", tags = ["CustomQuery"])
class CustomQueryController(val variationBySubtypeFinder: VariationBySubtypeFinder,
    val hierarchyFinder: MeaningHierarchyFinder) {

    @GetMapping("variation-example/{partOfSpeechSubType}")
    fun getVariationExampleBySubType(@PathVariable partOfSpeechSubType: PartOfSpeechSubType): ObjectNode? =
        variationBySubtypeFinder.find(partOfSpeechSubType)

    @GetMapping("meaning-hierarchy/{meaningId}")
    fun findMeaningHierarchy(@PathVariable meaningId: Long) = hierarchyFinder.find(meaningId)
}