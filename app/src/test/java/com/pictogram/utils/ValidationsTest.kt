package com.pictogram.utils

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ValidationsTest {

    @Mock
    private lateinit var validations: Validations

    @Test
    fun `test valid email addresses`() {
        val email = "user123@mail.co"
        `when`(validations.isEmailValidCheck(email)).thenReturn(true)

        val result = validations.isEmailValidCheck(email)
        assertTrue("Email $email should be valid", result)
    }

    @Test
    fun `test invalid email addresses`() {
        val email = "invalid.email"
        `when`(validations.isEmailValidCheck(email)).thenReturn(false)

        val result = validations.isEmailValidCheck(email)
        assertFalse("Email $email should be invalid", result)
    }

    @Test
    fun `test valid password`() {
        val password = "Pass123"
        `when`(validations.isPasswordValidCheck(password)).thenReturn(true)

        val result = validations.isPasswordValidCheck(password)
        assertTrue("Password $password should be valid", result)
    }

    @Test
    fun `test invalid password`() {
        val password = "12345"
        `when`(validations.isPasswordValidCheck(password)).thenReturn(false)

        val result = validations.isPasswordValidCheck(password)
        assertFalse("Password $password should be invalid", result)
    }


    @Test
    fun `test valid age`() {
        val age = "25"
        `when`(validations.isAgeValidCheck(age)).thenReturn(true)

        val result = validations.isAgeValidCheck(age)
        assertTrue("Age $age should be valid", result)
    }

    @Test
    fun `test invalid age`() {
        val age = "4"
        `when`(validations.isAgeValidCheck(age)).thenReturn(false)

        val result = validations.isAgeValidCheck(age)
        assertFalse("Age $age should be invalid", result)
    }



}