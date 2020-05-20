package businessday

import java.time.LocalDate

fun main() {
    val newDate = dateAfterBusinessDaysFrom(LocalDate.of(2019, 11, 8))
    println("New Date: $newDate")

    println(numberBusinessDaysUntilCurrentDate(referenceDate = LocalDate.now().minusDays(10)))
}