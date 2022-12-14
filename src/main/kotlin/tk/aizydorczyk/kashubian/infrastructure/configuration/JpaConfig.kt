package tk.aizydorczyk.kashubian.infrastructure.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration


@Configuration
@EntityScan("tk.aizydorczyk.kashubian.crud.model.entity")
class JpaConfig

