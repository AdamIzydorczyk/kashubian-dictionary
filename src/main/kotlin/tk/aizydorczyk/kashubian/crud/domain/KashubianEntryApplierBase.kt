package tk.aizydorczyk.kashubian.crud.domain

import tk.aizydorczyk.kashubian.crud.extension.normalize
import tk.aizydorczyk.kashubian.crud.model.entity.Translation

abstract class KashubianEntryApplierBase {
    protected fun prepareTranslation(meaningId: Long,
        it: Translation) = Translation(id = meaningId,
            polish = it.polish,
            normalizedPolish = it.polish?.normalize(),
            english = it.english,
            normalizedEnglish = it.english?.normalize(),
            german = it.german,
            normalizedGerman = it.german?.normalize(),
            ukrainian = it.ukrainian,
            normalizedUkrainian = it.ukrainian?.normalize(),
            meaning = meaningId)
}
