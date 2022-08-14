package tk.aizydorczyk.kashubian

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.domain.KashubianEntryController
import tk.aizydorczyk.kashubian.domain.model.dto.KashubianEntryDto
import tk.aizydorczyk.kashubian.domain.model.dto.MeaningDto


@Component
class TestDataInitializer(val kashubianEntryController: KashubianEntryController) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val parameters = EasyRandomParameters()
        parameters.stringLengthRange(64, 64)
        parameters.collectionSizeRange(3, 3)
        parameters.excludeField(FieldPredicates.named("variation")
            .and(FieldPredicates.inClass(KashubianEntryDto::class.java)))
        parameters.excludeField(FieldPredicates.named("others")
            .and(FieldPredicates.inClass(KashubianEntryDto::class.java)))
        parameters.excludeField(FieldPredicates.named("others")
            .and(FieldPredicates.inClass(KashubianEntryDto::class.java)))
        parameters.excludeField(FieldPredicates.named("synonyms").and(FieldPredicates.inClass(MeaningDto::class.java)))
        parameters.excludeField(FieldPredicates.named("antonyms").and(FieldPredicates.inClass(MeaningDto::class.java)))
        parameters.excludeField(FieldPredicates.named("base").and(FieldPredicates.inClass(MeaningDto::class.java)))
        parameters.excludeField(FieldPredicates.named("superordinate")
            .and(FieldPredicates.inClass(MeaningDto::class.java)))
        val generator = EasyRandom(parameters)
        generator.objects(KashubianEntryDto::class.java, 1000)
            .forEach(kashubianEntryController::create)
    }
}