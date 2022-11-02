package br.com.suacifra.data.repository

import br.com.suacifra.data.model.Cifra
import br.com.suacifra.screens.registration.CifraViewParams

interface CifraRepository {

    fun createOneCifra(cifraViewParams: CifraViewParams)

    fun readOneCifra(id: Long): Cifra

    fun readAllCifras() : MutableList<Cifra>

}