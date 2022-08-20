package tk.aizydorczyk.kashubian.crud.model.entity

interface ChildEntity : BaseEntity {
    fun setParentId(parentId: Long)
}