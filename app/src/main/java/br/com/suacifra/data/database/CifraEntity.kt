package br.com.suacifra.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.suacifra.data.model.Cifra
import br.com.suacifra.screens.registration.CifraViewParams

@Entity
data class CifraEntity(
    @PrimaryKey val id: Long = 0,
    val name: String,
    val tone: String,
    val singerName: String,
    val chordsSequence: String
)

fun CifraViewParams.toCifraEntity(): CifraEntity {
    return CifraEntity(
        name = this.name,
        tone = this.tone,
        singerName = this.singerName,
        chordsSequence = this.chordsSequence
    )
}

fun CifraEntity.toCifra(): Cifra {
    return Cifra(
        id = this.id.toString(),
        name = this.name,
        tone = this.tone,
        singerName = this.singerName,
        chordsSequence = this.chordsSequence
    )
}

fun MutableList<CifraEntity>.toMutableListOfCifra() : MutableList<Cifra> {
    val aux = mutableListOf<Cifra>()
    for (cifra in this) {
        aux.add(cifra.toCifra())
    }
    return aux
}
