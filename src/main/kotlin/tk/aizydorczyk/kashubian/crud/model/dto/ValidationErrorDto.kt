package tk.aizydorczyk.kashubian.crud.model.dto

data class ValidationErrorDto(
    val objectErrors: List<Map<String, String>> = emptyList(),
    val fieldErrors: List<Map<String, String>> = emptyList(),
    val paramErrors: List<Map<String, String>> = emptyList()
)