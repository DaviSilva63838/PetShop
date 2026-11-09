package br.com.pataepelo

import android.content.Context

class PetStorage(context: Context) {
    private val preferencias = context.getSharedPreferences("pataepelo", Context.MODE_PRIVATE)

    fun salvar(pet: Pet): Boolean {
        // commit() informa se os dados foram gravados no aparelho.
        return preferencias.edit()
            .putString("pet_nome", pet.nome)
            .putString("pet_especie", pet.especie)
            .putString("pet_tutor", pet.tutor)
            .commit()
    }

    fun carregar(): Pet? {
        val nome = preferencias.getString("pet_nome", null) ?: return null
        val especie = preferencias.getString("pet_especie", "Cachorro") ?: "Cachorro"
        val tutor = preferencias.getString("pet_tutor", "") ?: ""
        return Pet(nome, especie, tutor)
    }
}
