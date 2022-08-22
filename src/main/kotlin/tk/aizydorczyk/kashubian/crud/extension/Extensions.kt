package tk.aizydorczyk.kashubian.crud.extension

import org.apache.commons.lang3.StringUtils

fun String.normalize(): String = this.let(StringUtils::stripAccents)
    .replace("\\s".toRegex(), "")
    .lowercase()