package tk.aizydorczyk.kashubian.crud.email

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.aizydorczyk.kashubian.crud.model.dto.EmailDto
import javax.validation.Valid

@RestController
@RequestMapping("email")
@Validated
@Tag(name = "Email")
class EmailController(val emailSender: EmailSender) {

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    @PostMapping
    fun sendEmail(@Valid @RequestBody emailDto: EmailDto) {
        logger.info("Sending email with payload: $emailDto")
        emailSender.send(emailDto)
    }
}