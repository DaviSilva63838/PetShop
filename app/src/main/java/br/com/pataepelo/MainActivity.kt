package br.com.pataepelo

import android.content.Intent
import android.widget.Button
import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.botaoPet).setOnClickListener {
            startActivity(Intent(this, PetActivity::class.java))
        }
        findViewById<Button>(R.id.botaoServicos).setOnClickListener {
            startActivity(Intent(this, ServicosActivity::class.java))
        }
    }
}
