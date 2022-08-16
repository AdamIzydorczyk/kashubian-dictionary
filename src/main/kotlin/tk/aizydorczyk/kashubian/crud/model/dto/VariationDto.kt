package tk.aizydorczyk.kashubian.crud.model.dto

import com.fasterxml.jackson.databind.node.ObjectNode
import javax.validation.constraints.NotNull

data class VariationDto(
    @field:NotNull
    val variation: ObjectNode?
)
