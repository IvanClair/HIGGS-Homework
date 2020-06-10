package personal.ivan.higgshomework.util

import java.util.*

// Roman Map
private val romanMap = mutableMapOf(
    "I" to 1,
    "IV" to 4,
    "V" to 5,
    "IX" to 9,
    "X" to 10,
    "XL" to 40,
    "L" to 50,
    "XC" to 90,
    "C" to 100,
    "CD" to 400,
    "D" to 500,
    "CM" to 900,
    "M" to 1000
)

// Chinese Map
private val chineseMap = mutableMapOf(
    1 to "一",
    2 to "二",
    3 to "三",
    4 to "四",
    5 to "五",
    6 to "六",
    7 to "七",
    8 to "八",
    9 to "九",
    0 to "零"
)

/**
 * Convert roman number string to chinese number string
 *
 * @return empty string if exception happened
 */
fun String.convertRomanToChinese(): String {

    val handledString = toUpperCase(Locale.ENGLISH)

    // convert to integer
    var intResult = 0
    val length = handledString.length
    var i = 0
    while (i < length) {

        // index out of bound, means the roman number is out of definition
        if (i + 1 > length || i + 2 > length) {
            return ""
        }

        val temp1 = romanMap[handledString.substring(i, i + 2)]
        val temp2 = romanMap[handledString.substring(i, i + 1)]
        when {
            // encounter sequential wording
            i + 1 < length && temp1 != null -> {
                intResult += temp1
                i += 2
            }

            // encounter single wording
            temp2 != null -> {
                intResult += temp2
                i += 1
            }

            // invalid roman string
            else -> return ""
        }
    }

    if (intResult > 3000) {
        return ""
    }

    // convert to chinese
    val chineseStringBuilder = StringBuilder()
    var count = 0
    while (intResult > 0) {
        val temp = intResult % 10
        var wording = chineseMap[temp] ?: ""

        fun addToBuilder(digitString: String) {
            if (temp != 0) {
                wording += digitString
            }

            // avoid zero at the end
            if (temp != 0 || chineseStringBuilder.isNotEmpty()) {
                chineseStringBuilder.insert(0, wording)
            }
        }

        addToBuilder(
            digitString = when (count) {
                1 -> "十"
                2 -> "百"
                3 -> "千"
                else -> ""
            }
        )

        count++
        intResult /= 10
    }

    return chineseStringBuilder.toString()
}