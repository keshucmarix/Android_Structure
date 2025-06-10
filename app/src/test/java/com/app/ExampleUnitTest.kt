package com.app

/*import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

*//**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *//*
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(6, 4 + 2)
    }
}

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoAt_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("invalidemail.com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("invalid@emailcom"))
    }

    @Test
    fun emailValidator_InvalidEmailNoAtNoDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("invalidemailcom"))
    }
}*/

object EmailValidator {
    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}