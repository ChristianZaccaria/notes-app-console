package controllers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Utilities.isValidText
import utils.Utilities.validRange


class UtilitiesTest {

    @Nested
    inner class ValidRange {
        @Test
        fun validRangeWorksWithPositiveTestData() {
            Assertions.assertTrue(validRange(1, 1, 1))
            Assertions.assertTrue(validRange(1, 1, 2))
            Assertions.assertTrue(validRange(1, 0, 1))
            Assertions.assertTrue(validRange(1, 0, 2))
            Assertions.assertTrue(validRange(-1, -2, -1))
        }

        @Test
        fun validRangeWorksWithNegativeTestData() {
            Assertions.assertFalse(validRange(1, 0, 0))
            Assertions.assertFalse(validRange(1, 1, 0))
            Assertions.assertFalse(validRange(1, 2, 1))
            Assertions.assertFalse(validRange(-1, -1, -2))
        }
    }

    @Nested
    inner class ValidText {
        @Test
        fun `text is accepted as long as it is not empty or null`() {
            Assertions.assertTrue(isValidText("s"))
            Assertions.assertTrue(isValidText("A"))
            Assertions.assertTrue(isValidText("ASDDS"))
            Assertions.assertTrue(isValidText("sdassadsaas"))
            Assertions.assertTrue(isValidText("0342"))
            Assertions.assertTrue(isValidText("$^%£*"))
            Assertions.assertFalse(isValidText(""))
    }
}



}