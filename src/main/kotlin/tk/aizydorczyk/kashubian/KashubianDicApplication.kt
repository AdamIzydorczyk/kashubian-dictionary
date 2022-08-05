package tk.aizydorczyk.kashubian

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan("tk.aizydorczyk.kashubian")
@SpringBootApplication
class KashubianDicApplication

fun main(args: Array<String>) {
    runApplication<KashubianDicApplication>(*args)
}
