package br.com.pataepelo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
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
            item.findViewById<TextView>(R.id.nomeServico).text = servico.nome
            item.findViewById<TextView>(R.id.descricaoServico).text = servico.descricao
            item.findViewById<TextView>(R.id.precoServico).text = getString(R.string.preco_formatado, servico.preco)
            lista.addView(item)
        }
    }
}
