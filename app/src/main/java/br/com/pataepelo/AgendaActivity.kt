package br.com.pataepelo

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

class AgendaActivity : Activity() {
    private lateinit var servico: Servico
    private lateinit var botaoData: Button
    private lateinit var horarios: Spinner
    private var dataSelecionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        val servicoEscolhido = Catalogo.buscar(intent.getIntExtra("servico_id", -1))
        if (servicoEscolhido == null) {
            Toast.makeText(this, R.string.agenda_servico_invalido, Toast.LENGTH_LONG).show()
            finish()
            return
        }
        servico = servicoEscolhido

        findViewById<Button>(R.id.btnVoltarAgenda).setOnClickListener { finish() }
        findViewById<TextView>(R.id.txtServicoAgenda).text = servico.nome
        findViewById<TextView>(R.id.txtPrecoAgenda).text = getString(R.string.agenda_preco, servico.preco)
        findViewById<Button>(R.id.btnCadastrarPetAgenda).setOnClickListener {
            startActivity(Intent(this, PetActivity::class.java))
        }

        dataSelecionada = savedInstanceState?.getString("data_selecionada") ?: ""
        botaoData = findViewById(R.id.btnDataAgenda)
        atualizarData()
        botaoData.setOnClickListener { escolherData() }

        horarios = findViewById(R.id.spnHorarioAgenda)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, RegrasAgendamento.horarios)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        horarios.adapter = adaptador
        findViewById<Button>(R.id.btnConfirmarAgenda).setOnClickListener { confirmar() }
    }

    override fun onResume() {
        super.onResume()
        if (isFinishing) return
        // Atualiza o pet quando o usuário retorna da tela de cadastro.
        val pet = PetStorage(this).carregar()
        findViewById<TextView>(R.id.txtPetAgenda).text = if (pet == null) {
            getString(R.string.agenda_sem_pet)
        } else {
            getString(R.string.agenda_pet_resumo, pet.nome, pet.especie)
        }
        findViewById<Button>(R.id.btnCadastrarPetAgenda).visibility = if (pet == null) View.VISIBLE else View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("data_selecionada", dataSelecionada)
        super.onSaveInstanceState(outState)
    }

    private fun escolherData() {
        val inicial = if (dataSelecionada.isEmpty()) LocalDate.now() else LocalDate.parse(dataSelecionada)
        val calendario = DatePickerDialog(this, { _, ano, mes, dia ->
            dataSelecionada = LocalDate.of(ano, mes + 1, dia).toString()
            atualizarData()
        }, inicial.year, inicial.monthValue - 1, inicial.dayOfMonth)

        val hoje = Calendar.getInstance()
        hoje.set(Calendar.HOUR_OF_DAY, 0)
        hoje.set(Calendar.MINUTE, 0)
        hoje.set(Calendar.SECOND, 0)
        hoje.set(Calendar.MILLISECOND, 0)
        calendario.datePicker.minDate = hoje.timeInMillis
        calendario.show()
    }

    private fun atualizarData() {
        botaoData.text = if (dataSelecionada.isEmpty()) {
            getString(R.string.agenda_escolher_data)
        } else {
            LocalDate.parse(dataSelecionada).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }

    private fun confirmar() {
        val pet = PetStorage(this).carregar()
        if (pet == null) {
            AlertDialog.Builder(this)
                .setTitle(R.string.agenda_cadastro_necessario)
                .setMessage(R.string.agenda_sem_pet)
                .setNegativeButton(R.string.agenda_agora_nao, null)
                .setPositiveButton(R.string.agenda_cadastrar_pet) { _, _ ->
                    startActivity(Intent(this, PetActivity::class.java))
                }
                .show()
            return
        }

        val hora = horarios.selectedItem.toString()
        val erro = RegrasAgendamento.erro(dataSelecionada, hora)
        if (erro != null) {
            Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
            return
        }

        val botaoConfirmar = findViewById<Button>(R.id.btnConfirmarAgenda)
        botaoConfirmar.isEnabled = false
        try {
            val armazenamento = AgendaStorage(this)
            if (RegrasAgendamento.horarioOcupado(armazenamento.listar(), dataSelecionada, hora)) {
                Toast.makeText(this, R.string.agenda_horario_ocupado, Toast.LENGTH_LONG).show()
                botaoConfirmar.isEnabled = true
                return
            }

            // O nome do pet é salvo junto ao agendamento, preservando o registro.
            val agendamento = Agendamento(
                UUID.randomUUID().toString(), pet.nome, servico.id,
                servico.nome, servico.preco, dataSelecionada, hora
            )
            if (!armazenamento.adicionar(agendamento)) {
                Toast.makeText(this, R.string.agenda_horario_ocupado, Toast.LENGTH_LONG).show()
                botaoConfirmar.isEnabled = true
                return
            }

            Toast.makeText(this, R.string.agenda_salvo, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AgendamentosActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        } catch (erro: IllegalStateException) {
            Toast.makeText(this, erro.message ?: getString(R.string.agenda_erro_salvar), Toast.LENGTH_LONG).show()
            botaoConfirmar.isEnabled = true
        }
    }
}
