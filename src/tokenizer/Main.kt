package tokenizer
import java.util.StringTokenizer
import kotlin.math.roundToInt

const val PAGE = """
Lorem Ipsum is simply dummy text of the printing and typesetting industry. 
Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of 
type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into 
electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset 
sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker 
including versions of Lorem Ipsum.
"""

fun main (){
    println(randomContentPage(PAGE, 269))
}

fun randomContentPage(fullContent: String, limitPageBytes: Int):String {
    val ranges = getRanges(fullContent.length, limitPageBytes)
    val computedRange = ranges.random()
    val startsWithZero = 0 in computedRange
    val contentToTokenizer = fullContent.slice(computedRange)

    val words = linkedSetOf<String>()
    val tokens = StringTokenizer(contentToTokenizer)
    var iterator = 0
    while (tokens.hasMoreTokens()) {
        val currentToken = tokens.nextToken()
        val isInLimit = words.sumBy { it.length } < limitPageBytes
        if ( isInLimit ){
            when {
                startsWithZero -> words.add(currentToken)
                !startsWithZero && iterator > 0 -> words.add(currentToken)
            }
            iterator++
        }
        else
            break
    }
    return words.joinToString(" ")
}

fun getRanges(maxLenth: Int, limitPageBytes: Int): List<IntRange> {
    val limitRange = (1.5 * limitPageBytes).roundToInt()
    if (limitPageBytes > maxLenth || limitRange >= maxLenth)
        return listOf(0 until maxLenth)

    val numberRangeElements = maxLenth / limitRange
    return (1..numberRangeElements).map {
        when (it) {
            1 -> 0 until limitRange
            else -> (it * limitRange - limitRange) until it * limitRange
        }
    }
}
