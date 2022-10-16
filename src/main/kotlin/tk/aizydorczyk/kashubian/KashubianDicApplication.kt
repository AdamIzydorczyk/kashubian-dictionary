package tk.aizydorczyk.kashubian

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan("tk.aizydorczyk.kashubian")
@SpringBootApplication
@EnableAdminServer
class KashubianDicApplication

fun main(args: Array<String>) {
    runApplication<KashubianDicApplication>(*args)
}
