package br.com.pataepelo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AgendamentosActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendamentos)
        findViewById<Button>(R.id.btnVoltarAgendamentos).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnNovoAgendamento).setOnClickListener {
            startActivity(Intent(this, ServicosActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        carregarAgendamentos()
    }

    private fun carregarAgendamentos() {
        val lista = findViewById<LinearLayout>(R.id.listaAgendamentos)
        val mensagem = findViewById<TextView>(R.id.txtAgendaVazia)
        lista.removeAllViews()
        val agendamentos = try {
            AgendaStorage(this).listar()
        } catch (erro: IllegalStateException) {
            mensagem.text = erro.message ?: getString(R.string.agenda_erro_carregar)
            mensagem.visibility = View.VISIBLE
            return
        }

        mensagem.setText(R.string.agenda_vazia)
        mensagem.visibility = if (agendamentos.isEmpty()) View.VISIBLE else View.GONE

        for (agendamento in agendamentos) {
            val item = layoutInflater.inflate(R.layout.item_agendamento, lista, false)
            val data = LocalDate.parse(agendamento.data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            item.findViewById<TextView>(R.id.txtItemServico).text = agendamento.servicoNome
            item.findViewById<TextView>(R.id.txtItemPet).text = getString(R.string.agenda_pet_nome, agendamento.petNome)
            item.findViewById<TextView>(R.id.txtItemData).text = getString(R.string.agenda_data_hora, data, agendamento.hora)
            item.findViewById<TextView>(R.id.txtItemPreco).text = getString(R.string.agenda_preco, agendamento.preco)
            val botaoCancelar = item.findViewById<Button>(R.id.btnCancelarAgendamento)
            botaoCancelar.contentDescription = getString(
                R.string.agenda_cancelar_descricao, agendamento.servicoNome, agendamento.petNome, data, agendamento.hora
            )
            botaoCancelar.setOnClickListener { confirmarCancelamento(agendamento, data) }
            lista.addView(item)
        }
    }

    private fun confirmarCancelamento(agendamento: Agendamento, data: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.agenda_cancelar_titulo)
            .setMessage(getString(R.string.agenda_cancelar_mensagem, agendamento.servicoNome, agendamento.petNome, data, agendamento.hora))
            .setNegativeButton(R.string.agenda_manter, null)
            .setPositiveButton(R.string.agenda_cancelar_confirmar) { _, _ ->
                try {
                    val removido = AgendaStorage(this).cancelar(agendamento.id)
                    val mensagem = if (removido) R.string.agenda_cancelado else R.string.agenda_nao_encontrado
                    Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
                    carregarAgendamentos()
                } catch (erro: IllegalStateException) {
                    Toast.makeText(this, erro.message ?: getString(R.string.agenda_erro_salvar), Toast.LENGTH_LONG).show()
                }
            }
            .show()
    }
}
