package tk.aizydorczyk.kashubian.crud.model.dto

import com.fasterxml.jackson.databind.node.ObjectNode
import io.swagger.annotations.ApiModelProperty
import tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.Companion.IS_NULL
import tk.aizydorczyk.kashubian.crud.validator.OnCreate
import tk.aizydorczyk.kashubian.crud.validator.OnUpdate
import javax.validation.constraints.NotNull

data class VariationDto(
    @field:NotNull(message = IS_NULL, groups = [OnCreate::class, OnUpdate::class])
    @ApiModelProperty(dataType = "tk.aizydorczyk.kashubian.crud.model.json.NounVariation")
    val variation: ObjectNode?
)
