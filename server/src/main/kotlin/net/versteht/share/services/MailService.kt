package net.versteht.share.services

import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import kotlinx.coroutines.coroutineScope
import java.util.*
import java.io.StringWriter
import java.io.Writer

// https://mailtrap.io/blog/android-javamail-api/#Step-1-Configure-Mailtrap-SMTP
class MailService(private val host: String,private val port: String,private  val user: String, private val password: String) {
    private val props = Properties();

    init {
        props["mail.smtp.host"] = host
        props["mail.smtp.port"] = port
        props["mail.smtp.socketFactory.port"] = port
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory";
    }

    fun send(to: String, subject: String, body: String){
        val session = Session.getInstance(props,AuthSession(user, password))

        // Create a default MimeMessage object.
        val message = MimeMessage(session)

        // Set From: header field of the header.
        message.setFrom(InternetAddress(user))
        message.addRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(to)
        )
        val bodyPart: BodyPart = MimeBodyPart()
        bodyPart.setContent(body, "text/html")
        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(bodyPart)
        message.setContent(multipart, "text/html; charset=ISO-8859-1")

        // Set Subject: header field
        message.subject = subject

        // Send message
        Transport.send(message)
    }

   suspend fun sendAsync(to: String, subject: String, body: String)  = coroutineScope{
        send(to,subject, body)
   }

}

class AuthSession(private val user: String, private val password: String) : Authenticator() {

    override fun getPasswordAuthentication(): PasswordAuthentication? {
        return PasswordAuthentication(user,password)
    }
}
