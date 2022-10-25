package tk.aizydorczyk.kashubian.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Clock
import java.time.ZoneId

@Configuration
class TimeConfig {
    @Bean
    @Primary
    fun clock(): Clock = Clock.system(ZoneId.of("Europe/Warsaw"))
}