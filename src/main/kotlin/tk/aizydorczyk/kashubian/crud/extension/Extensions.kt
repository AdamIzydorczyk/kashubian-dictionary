package tk.aizydorczyk.kashubian.crud.extension

import org.apache.commons.lang3.StringUtils
import tk.aizydorczyk.kashubian.crud.model.entity.ChildEntity
import javax.persistence.EntityManager

fun String.normalize(): String = this.let(StringUtils::stripAccents)
    .replace("\\s".toRegex(), "")
    .lowercase()

fun List<ChildEntity>.assignParentToAllAndPersist(parentId: Long, entityManager: EntityManager) {
    this.onEach { it.setParentId(parentId) }.forEach(entityManager::persist)
}
