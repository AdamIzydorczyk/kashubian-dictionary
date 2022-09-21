package tk.aizydorczyk.kashubian.crud.model.graphql.criteria

import tk.aizydorczyk.kashubian.crud.query.graphql.base.CriteriaExpression

data class KashubianEntryCriteriaExpression(
    val id: NumericCriteria?,
    val note: StringCriteria?,
    val word: StringCriteria?,
    val variation: JsonCriteria?,
    val bases: JsonCriteria?,
    val derivatives: JsonCriteria?,
    val normalizedWord: NormalizedCriteria?,
    val priority: BooleanCriteria?,
    val soundFile: SoundFileCriteriaExpression?,
    val others: OthersCriteriaExpression?,
    val meanings: MeaningsCriteriaExpression?) : CriteriaExpression
