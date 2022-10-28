package tk.aizydorczyk.kashubian.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Clock
import java.time.ZoneId
import java.util.function.Supplier

@Configuration
class TimeConfig {
    @Bean
    @Primary
    fun clockProvider(): Supplier<Clock> = Supplier<Clock> {
        Clock.system(ZoneId.of("Europe/Warsaw"))
    }
}