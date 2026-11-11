package br.com.pataepelo

import java.time.LocalDateTime
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RegrasAgendamentoTest {
    private val agora = LocalDateTime.of(2026, 10, 29, 10, 0)

    @Test
    fun aceitaDiaFuturo() {
        assertNull(RegrasAgendamento.erro("2026-10-30", "09:00", agora))
    }

    @Test
    fun aceitaHorarioFuturoNoMesmoDia() {
        assertNull(RegrasAgendamento.erro("2026-10-29", "11:00", agora))
    }

    @Test
    fun rejeitaDiaPassado() {
        assertNotNull(RegrasAgendamento.erro("2026-10-28", "16:00", agora))
    }

    @Test
    fun rejeitaHorarioPassadoNoMesmoDia() {
        assertNotNull(RegrasAgendamento.erro("2026-10-29", "09:00", agora))
    }

    @Test
    fun rejeitaExatamenteOMomentoAtual() {
        assertNotNull(RegrasAgendamento.erro("2026-10-29", "10:00", agora))
    }

    @Test
    fun rejeitaDataAusenteOuInvalida() {
        assertNotNull(RegrasAgendamento.erro("", "09:00", agora))
        assertNotNull(RegrasAgendamento.erro("2026-02-30", "09:00", agora))
        assertNotNull(RegrasAgendamento.erro("30/10/2026", "09:00", agora))
    }

    @Test
    fun rejeitaHorarioForaDasOpcoes() {
        assertNotNull(RegrasAgendamento.erro("2026-10-30", "12:00", agora))
        assertNotNull(RegrasAgendamento.erro("2026-10-30", "09:30", agora))
        assertNotNull(RegrasAgendamento.erro("2026-10-30", "", agora))
    }

    @Test
    fun identificaConflitoMesmoComPetOuServicoDiferente() {
        val agenda = listOf(
            Agendamento("1", "Rex", 1, "Banho", 40, "2026-10-30", "09:00")
        )
        assertTrue(RegrasAgendamento.horarioOcupado(agenda, "2026-10-30", "09:00"))
        assertFalse(RegrasAgendamento.horarioOcupado(agenda, "2026-10-30", "10:00"))
        assertFalse(RegrasAgendamento.horarioOcupado(agenda, "2026-10-31", "09:00"))
    }

    @Test
    fun agendaVaziaNaoTemConflito() {
        assertFalse(RegrasAgendamento.horarioOcupado(emptyList(), "2026-10-30", "09:00"))
    }
}
