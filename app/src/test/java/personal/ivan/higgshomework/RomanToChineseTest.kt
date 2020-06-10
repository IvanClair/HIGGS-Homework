package personal.ivan.higgshomework

import org.junit.Assert
import org.junit.Test
import personal.ivan.higgshomework.util.convertRomanToChinese

class RomanToChineseTest {

    // region Correct

    @Test
    fun convertRomanToChinese_shouldWorkAsExpected_whenRomanNumberIsValid() {
        // tens digit
        var expected = "九"
        var actual = "IX".convertRomanToChinese()
        Assert.assertEquals(expected, actual)

        // hundreds digit
        expected = "三百九十四"
        actual = "CCCXCIV".convertRomanToChinese()
        Assert.assertEquals(expected, actual)

        // thousands digit
        expected = "一千九百"
        actual = "MCM".convertRomanToChinese()
        Assert.assertEquals(expected, actual)
    }

    // endregion

    // region Incorrect

    @Test
    fun convertRomanToChinese_shouldReturnEmptyString_whenRomanNumberIsInValid() {
        val expected = ""
        val actual = "123".convertRomanToChinese()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun convertRomanToChinese_shouldReturnEmptyString_whenRomanNumberIsOutOfDefinition() {
        val expected = ""
        val actual = "MMMM".convertRomanToChinese()
        Assert.assertEquals(expected, actual)
    }

// endregion
}