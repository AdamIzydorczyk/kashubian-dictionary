package tk.aizydorczyk.kashubian.crud.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class MeaningConfiguration {

    @Bean
    fun meaningCreator(entityManager: EntityManager): MeaningCreator =
        MeaningCreator(entityManager)

    @Bean
    fun meaningUpdater(entityManager: EntityManager): MeaningUpdater =
        MeaningUpdater(entityManager)

    @Bean
    fun meaningRemover(repository: MeaningRepository): MeaningRemover =
        MeaningRemover(repository::deleteMeaningById)

    @Bean
    fun antonymCreator(entityManager: EntityManager): AntonymCreator =
        AntonymCreator(entityManager)

    @Bean
    fun antonymUpdater(entityManager: EntityManager): AntonymUpdater =
        AntonymUpdater(entityManager)

    @Bean
    fun antonymRemover(repository: MeaningRepository): AntonymRemover =
        AntonymRemover(repository::deleteAntonymById)

    @Bean
    fun synonymCreator(entityManager: EntityManager): SynonymCreator =
        SynonymCreator(entityManager)

    @Bean
    fun synonymUpdater(entityManager: EntityManager): SynonymUpdater =
        SynonymUpdater(entityManager)

    @Bean
    fun synonymRemover(repository: MeaningRepository): SynonymRemover =
        SynonymRemover(repository::deleteSynonymById)

    @Bean
    fun exampleCreator(entityManager: EntityManager): ExampleCreator =
        ExampleCreator(entityManager)

    @Bean
    fun exampleUpdater(entityManager: EntityManager): ExampleUpdater =
        ExampleUpdater(entityManager)

    @Bean
    fun exampleRemover(repository: MeaningRepository): ExampleRemover =
        ExampleRemover(repository::deleteExampleById)

    @Bean
    fun idiomCreator(entityManager: EntityManager): IdiomCreator =
        IdiomCreator(entityManager)

    @Bean
    fun idiomUpdater(entityManager: EntityManager): IdiomUpdater =
        IdiomUpdater(entityManager)

    @Bean
    fun idiomRemover(repository: MeaningRepository): IdiomRemover =
        IdiomRemover(repository::deleteIdiomById)

    @Bean
    fun proverbCreator(entityManager: EntityManager): ProverbCreator =
        ProverbCreator(entityManager)

    @Bean
    fun proverbUpdater(entityManager: EntityManager): ProverbUpdater =
        ProverbUpdater(entityManager)

    @Bean
    fun proverbRemover(repository: MeaningRepository): ProverbRemover =
        ProverbRemover(repository::deleteProverbById)

    @Bean
    fun quoteCreator(entityManager: EntityManager): QuoteCreator =
        QuoteCreator(entityManager)

    @Bean
    fun quoteUpdater(entityManager: EntityManager): QuoteUpdater =
        QuoteUpdater(entityManager)

    @Bean
    fun quoteRemover(repository: MeaningRepository): QuoteRemover =
        QuoteRemover(repository::deleteQuoteById)

    @Bean
    fun translationCreator(entityManager: EntityManager): TranslationCreator =
        TranslationCreator(entityManager)

    @Bean
    fun translationUpdater(entityManager: EntityManager): TranslationUpdater =
        TranslationUpdater(entityManager)

    @Bean
    fun translationRemover(repository: MeaningRepository): TranslationRemover =
        TranslationRemover(repository::deleteTranslationByMeaningId)
}
