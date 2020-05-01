import java.time.LocalDate

fun main() {
    val novoPrazo = obterPrazoEmDiasUteis(LocalDate.of(2019,11,8))
    println("Novo prazo: $novoPrazo")
}