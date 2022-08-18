package tk.aizydorczyk.kashubian

import com.introproventures.graphql.jpa.query.web.autoconfigure.GraphQLControllerAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan("tk.aizydorczyk.kashubian")
@SpringBootApplication
@EnableAutoConfiguration(exclude = [GraphQLControllerAutoConfiguration::class])
class KashubianDicApplication

fun main(args: Array<String>) {
    runApplication<KashubianDicApplication>(*args)
}
