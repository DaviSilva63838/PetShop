package br.com.pataepelo

// Os preços são valores inteiros em reais.
data class Servico(
    val id: Int,
    val nome: String,
    val descricao: String,
    val preco: Int
)

object Catalogo {
    val servicos = listOf(
        Servico(1, "Banho", "Banho e secagem para deixar seu pet limpinho.", 40),
        Servico(2, "Tosa", "Corte dos pelos para o conforto do seu pet.", 50),
        Servico(3, "Banho e tosa", "Banho, secagem e corte dos pelos.", 75)
    )

    fun buscar(id: Int): Servico? {
        return servicos.find { it.id == id }
    }
}
