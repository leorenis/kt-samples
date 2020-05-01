import java.time.DayOfWeek
import java.time.LocalDate
import java.util.ArrayList

const val PRAZO_CIENCIA: Long = 5L

fun obterPrazoEmDiasUteis(dataReferencia: LocalDate): LocalDate {

    var prazoReal = dataReferencia.plusDays(PRAZO_CIENCIA)
    var dataAposContagem = LocalDate.of(dataReferencia.year, dataReferencia.month, dataReferencia.dayOfMonth)
    var (numeroDiasChecados, numeroDiasSemExpediente) = listOf(0, 0)
    do {
        dataAposContagem = dataAposContagem.plusDays(1)

        if (ehDiaSemExpedienteTCU(dataAposContagem) && numeroDiasChecados == numeroDiasSemExpediente) {
            prazoReal = prazoReal.plusDays(1)
            numeroDiasSemExpediente++
        }
        numeroDiasChecados++
    } while (dataAposContagem.isBefore(prazoReal))

    return proximoDiaComExpedienteTCU(dataAposContagem)
}

fun proximoDiaComExpedienteTCU(data:LocalDate): LocalDate = if (!ehDiaSemExpedienteTCU(data) ) data else {
    var proximaData = LocalDate.of(data.year, data.month, data.dayOfMonth)
    while (ehDiaSemExpedienteTCU(proximaData))
        proximaData = proximaData.plusDays(1)
    proximaData
}

fun ehDiaSemExpedienteTCU(data:LocalDate):Boolean = eFimDeSemana(data) || eFeriado(data)

fun eFimDeSemana(dataCorrente: LocalDate): Boolean {

    return dataCorrente.dayOfWeek == DayOfWeek.SATURDAY || dataCorrente.dayOfWeek == DayOfWeek.SUNDAY
}

fun eFeriado(dataCorrente: LocalDate): Boolean {
    val feriados = ArrayList<LocalDate>()
    feriados.add(LocalDate.of(2019, 11, 15))
    feriados.add(LocalDate.of(2019, 11, 2))

    return feriados.contains(dataCorrente)
}

fun obterDiasUteisAteDataCorrente(dataReferencia: LocalDate, feriados: List<LocalDate>): Long {

    val dataAtual = LocalDate.of(2017, 2, 28) // Fake
    var dataAposContagem = LocalDate.of(dataReferencia.year, dataReferencia.month, dataReferencia.dayOfMonth)

    var contagemRegional = PRAZO_CIENCIA

    while (dataAposContagem.isBefore(dataAtual)) {

        if (!(eFimDeSemana(dataAposContagem) || eFeriado(dataAposContagem))) {
            contagemRegional--
        }


        dataAposContagem = dataAposContagem.plusDays(1)
    }

    return contagemRegional
}