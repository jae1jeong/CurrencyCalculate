package com.jae1jeong.currencycalculate

import com.jae1jeong.currencycalculate.utils.Validator
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MainViewModelTest {
    @Test
    fun testOnlyNumberExceptDot() {
        val input = "1,234.56"
        val expected = 1234.56
        val result = Validator.onlyNumberExceptDot(input)
        assertEquals(expected, result, 0.0)
    }

    @Test
    fun testCheckValidInRange() {
        val validInput = 5000.0
        val invalidInput = 15000.0
        val invalidInput2 = -1.0
        val resultValid = Validator.checkValidInRange(validInput)
        val resultInvalid = Validator.checkValidInRange(invalidInput)
        val resultInvalid2 = Validator.checkValidInRange(invalidInput2)
        assertEquals(true, resultValid)
        assertEquals(false, resultInvalid)
        assertEquals(false, resultInvalid2)
    }

}