import java.time.DayOfWeek
import java.time.LocalDate

const val TERM_IN_DAYS: Long = 5L

fun dateAfterBusinessDaysFrom(referenceDate: LocalDate): LocalDate {

    var realTerm = referenceDate.plusDays(TERM_IN_DAYS)
    var dateAfterCalc = LocalDate.of(referenceDate.year, referenceDate.month, referenceDate.dayOfMonth)
    var (numberDaysChecked, numberOfDaysWithoutOfficeHours) = listOf(0, 0)
    do {
        dateAfterCalc = dateAfterCalc.plusDays(1)

        if (isDayOffBy(dateAfterCalc) &&
            numberDaysChecked == numberOfDaysWithoutOfficeHours) {
            realTerm = realTerm.plusDays(1)
            numberOfDaysWithoutOfficeHours++
        }
        numberDaysChecked++
    } while (dateAfterCalc.isBefore(realTerm))

    return nextDayWithOfficeHours(dateAfterCalc)
}

fun nextDayWithOfficeHours(someDate:LocalDate): LocalDate = if (!isDayOffBy(someDate) ) someDate else {
    var nextDate = LocalDate.of(someDate.year, someDate.month, someDate.dayOfMonth)
    while (isDayOffBy(nextDate))
        nextDate = nextDate.plusDays(1)
    nextDate
}

fun isDayOffBy(someDate:LocalDate):Boolean = isWeekend(someDate) || isHoliday(someDate)

fun isWeekend(dataCorrente: LocalDate): Boolean =
    dataCorrente.dayOfWeek == DayOfWeek.SATURDAY || dataCorrente.dayOfWeek == DayOfWeek.SUNDAY


fun isHoliday(currentDate: LocalDate): Boolean {
    val holidaysMock = listOf(
        LocalDate.of(2019, 11, 15),
        LocalDate.of(2019, 11, 2)
    )
    return holidaysMock.contains(currentDate)
}

fun numberBusinessDaysUntilCurrentDate(referenceDate: LocalDate, holidaysMock: List<LocalDate>): Long {

    val currentDateMock = LocalDate.of(2017, 2, 28) // Fake
    var dateAfterCalc = LocalDate.of(referenceDate.year, referenceDate.month, referenceDate.dayOfMonth)

    var counting = TERM_IN_DAYS

    while (dateAfterCalc.isBefore(currentDateMock)) {

        if (!isWeekend(dateAfterCalc) || isHoliday(dateAfterCalc)) {
            counting--
        }

        dateAfterCalc = dateAfterCalc.plusDays(1)
    }

    return counting
}