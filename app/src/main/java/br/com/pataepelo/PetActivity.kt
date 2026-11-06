package br.com.pataepelo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class PetActivity : Activity() {
    private lateinit var campoNome: EditText
    private lateinit var campoTutor: EditText
    private lateinit var campoEspecie: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        campoNome = findViewById(R.id.campoNome)
        campoTutor = findViewById(R.id.campoTutor)
        campoEspecie = findViewById(R.id.campoEspecie)
        findViewById<Button>(R.id.botaoVoltar).setOnClickListener { finish() }
        findViewById<Button>(R.id.botaoSalvar).setOnClickListener { salvarPet() }
    }

    private fun salvarPet() {
        val nome = campoNome.text.toString().trim()
        val tutor = campoTutor.text.toString().trim()
        if (nome.isBlank()) {
            campoNome.error = getString(R.string.pet_erro_nome)
            campoNome.requestFocus()
            return
        }
        if (tutor.isBlank()) {
            campoTutor.error = getString(R.string.pet_erro_tutor)
            campoTutor.requestFocus()
            return
        }
        val pet = Pet(nome, campoEspecie.selectedItem.toString(), tutor)
        Toast.makeText(this, getString(R.string.pet_salvo), Toast.LENGTH_SHORT).show()
    }
}
