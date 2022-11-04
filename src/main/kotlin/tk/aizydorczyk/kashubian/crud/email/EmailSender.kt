package tk.aizydorczyk.kashubian.crud.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import tk.aizydorczyk.kashubian.crud.model.dto.EmailDto

@Component
class EmailSender(private val javaMailSender: JavaMailSender) {

    @Value("\${spring.mail.username}")
    private lateinit var sourceMail: String

    fun send(emailDto: EmailDto) {
        with(SimpleMailMessage()) {
            setFrom(sourceMail)
            setTo(sourceMail)
            setReplyTo(emailDto.contactEmail.toString())
            setSubject(emailDto.subject.toString())
            setText(emailDto.content.toString())
            javaMailSender.send(this)
        }
    }
}