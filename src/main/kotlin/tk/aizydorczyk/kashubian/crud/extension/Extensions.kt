package tk.aizydorczyk.kashubian.crud.extension

import org.apache.commons.lang3.StringUtils

fun String.stripAccents(): String = this.let(StringUtils::stripAccents)