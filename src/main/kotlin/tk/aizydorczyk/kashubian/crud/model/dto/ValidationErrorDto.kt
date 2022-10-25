package tk.aizydorczyk.kashubian.crud.model.dto

data class ValidationErrorDto(
    val objectErrors: Set<Map<String, String>> = emptySet(),
    val fieldErrors: Set<Map<String, String>> = emptySet(),
    val paramErrors: Set<Map<String, String>> = emptySet()
)