package net.versteht.share.services

import kotlin.test.*

import org.junit.jupiter.api.Assertions.*

// TODO("Write some internal test")
class MailServiceTest {

    @Test
    fun send() {
        val dut = MailService("localhost", "2525", "you@me.de", "secret")
        dut.send("me@you.de", "test mail", "This is a test mail")
        assert(true)
    }

    @Test
    fun sendAsync() {
    }
}