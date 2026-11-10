package br.com.pataepelo

// Guardamos o nome do pet e o serviço para preservar o histórico do agendamento.
data class Agendamento(
    val id: String,
    val petNome: String,
    val servicoId: Int,
    val servicoNome: String,
    val preco: Int,
    val data: String, // Formato usado para salvar: yyyy-MM-dd.
    val hora: String // Formato usado para salvar: HH:mm.
)
