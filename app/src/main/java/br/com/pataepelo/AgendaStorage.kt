package br.com.pataepelo

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AgendaStorage(context: Context) {
    private val preferencias = context.getSharedPreferences("pataepelo", Context.MODE_PRIVATE)

    fun listar(): List<Agendamento> {
        val texto = preferencias.getString("agendamentos", "[]") ?: "[]"
        val agendamentos = mutableListOf<Agendamento>()

        try {
            val lista = JSONArray(texto)
            for (i in 0 until lista.length()) {
                val item = lista.getJSONObject(i)
                agendamentos.add(
                    Agendamento(
                        id = item.getString("id"),
                        petNome = item.getString("petNome"),
                        servicoId = item.getInt("servicoId"),
                        servicoNome = item.getString("servicoNome"),
                        preco = item.getInt("preco"),
                        data = item.getString("data"),
                        hora = item.getString("hora")
                    )
                )
            }
        } catch (erro: JSONException) {
            // Não substituir dados que não puderam ser lidos por uma lista vazia.
            throw IllegalStateException("Não foi possível ler os agendamentos salvos.", erro)
        }

        return agendamentos.sortedWith(compareBy({ it.data }, { it.hora }))
    }

    fun adicionar(agendamento: Agendamento): Boolean {
        val agendamentos = listar().toMutableList()
        if (agendamentos.any { it.data == agendamento.data && it.hora == agendamento.hora }) {
            return false
        }
        agendamentos.add(agendamento)
        gravar(agendamentos)
        return true
    }

    fun cancelar(id: String): Boolean {
        val agendamentos = listar().toMutableList()
        val removido = agendamentos.removeAll { it.id == id }
        if (!removido) return false
        gravar(agendamentos)
        return true
    }

    private fun gravar(agendamentos: List<Agendamento>) {
        val lista = JSONArray()
        for (agendamento in agendamentos) {
            val item = JSONObject()
            item.put("id", agendamento.id)
            item.put("petNome", agendamento.petNome)
            item.put("servicoId", agendamento.servicoId)
            item.put("servicoNome", agendamento.servicoNome)
            item.put("preco", agendamento.preco)
            item.put("data", agendamento.data)
            item.put("hora", agendamento.hora)
            lista.put(item)
        }

        if (!preferencias.edit().putString("agendamentos", lista.toString()).commit()) {
            throw IllegalStateException("Não foi possível salvar os agendamentos. Tente novamente.")
        }
    }
}
