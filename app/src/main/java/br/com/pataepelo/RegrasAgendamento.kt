package br.com.pataepelo

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException

object RegrasAgendamento {
    // Cada atendimento ocupa uma hora, com intervalo de almoço das 12h às 14h.
    val horarios = listOf("09:00", "10:00", "11:00", "14:00", "15:00", "16:00")

    fun erro(data: String, hora: String, agora: LocalDateTime = LocalDateTime.now()): String? {
        if (data.isBlank()) return "Escolha a data do atendimento."
        if (hora !in horarios) return "Escolha um dos horários disponíveis."

        val dataEscolhida = try {
            LocalDate.parse(data)
        } catch (erro: DateTimeParseException) {
            return "Escolha uma data válida."
        }

        val atendimento = dataEscolhida.atTime(LocalTime.parse(hora))
        if (!atendimento.isAfter(agora)) {
            return "Escolha uma data e um horário no futuro."
        }
        return null
    }

    fun horarioOcupado(agendamentos: List<Agendamento>, data: String, hora: String): Boolean {
        return agendamentos.any { it.data == data && it.hora == hora }
    }
}
