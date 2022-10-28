package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class KashubianEntryConfiguration {
    @Bean
    fun kashubianEntryCreator(entityManager: EntityManager): KashubianEntryCreator =
        KashubianEntryCreator(entityManager)

    @Bean
    fun kashubianEntryUpdater(entityManager: EntityManager): KashubianEntryUpdater =
        KashubianEntryUpdater(entityManager)

    @Bean
    fun kashubianEntryRemover(repository: KashubianEntryRepository): KashubianEntryRemover =
        KashubianEntryRemover(repository::deleteEntryById)

    @Bean
    fun kashubianEntrySoundFileDownloader(repository: KashubianEntryRepository): KashubianEntrySoundFileDownloader =
        KashubianEntrySoundFileDownloader(repository::findSoundFileByEntryId)

    @Bean
    fun kashubianEntrySoundFileRemover(repository: KashubianEntryRepository): KashubianEntrySoundFileRemover =
        KashubianEntrySoundFileRemover(repository::removeSoundFileByEntryId)

    @Bean
    fun kashubianEntrySoundFileUploader(entityManager: EntityManager): KashubianEntrySoundFileUploader =
        KashubianEntrySoundFileUploader(entityManager::merge)
}