package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.value.PartOfSpeechSubType

@Component
class VariationBySubtypeFinder(val exampleVariationsGenerator: ExampleVariationsGenerator) {
    fun find(subType: PartOfSpeechSubType): ObjectNode? {
        val exampleBySubtype: Map<String, ObjectNode?> = exampleVariationsGenerator.exampleBySubtype()
        return exampleBySubtype[subType.name]
    }
}

