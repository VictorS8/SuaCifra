package br.com.suacifra.data.repository

import br.com.suacifra.data.database.dao.CifraDao
import br.com.suacifra.data.database.toCifra
import br.com.suacifra.data.database.toCifraEntity
import br.com.suacifra.data.database.toMutableListOfCifra
import br.com.suacifra.data.model.Cifra
import br.com.suacifra.screens.registration.CifraViewParams

class CifraDatabaseDataSource(
     private val cifraDao: CifraDao
) : CifraRepository {
    override fun createOneCifra(cifraViewParams: CifraViewParams) {
        val cifraEntity = cifraViewParams.toCifraEntity()
        cifraDao.createOne(cifraEntity)
    }

    override fun readOneCifra(id: Long): Cifra {
        return cifraDao.readOne(id).toCifra()
    }

    override fun readAllCifras(): MutableList<Cifra> {
        return cifraDao.readAll().toMutableListOfCifra()
    }

}


