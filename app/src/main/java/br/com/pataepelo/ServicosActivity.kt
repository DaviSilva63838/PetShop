package br.com.pataepelo

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ServicosActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicos)
        findViewById<Button>(R.id.botaoVoltar).setOnClickListener { finish() }

        val lista = findViewById<LinearLayout>(R.id.listaServicos)
        for (servico in Catalogo.servicos) {
            val item = layoutInflater.inflate(R.layout.item_servico, lista, false)
            val imagem = when (servico.id) {
                1 -> R.drawable.pet_banho
                2 -> R.drawable.pet_tosa
                else -> R.drawable.pets_inicio
            }
            item.findViewById<ImageView>(R.id.imagemServico).setImageResource(imagem)
            item.findViewById<TextView>(R.id.nomeServico).text = servico.nome
            item.findViewById<TextView>(R.id.descricaoServico).text = servico.descricao
            item.findViewById<TextView>(R.id.precoServico).text = getString(R.string.preco_formatado, servico.preco)
            val botaoAgendar = item.findViewById<Button>(R.id.botaoAgendar)
            botaoAgendar.text = getString(R.string.servico_agendar, servico.nome.lowercase())
            botaoAgendar.setOnClickListener {
                val intent = Intent(this, AgendaActivity::class.java)
                intent.putExtra("servico_id", servico.id)
                startActivity(intent)
            }
            lista.addView(item)
        }
    }
}
