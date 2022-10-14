package br.com.suacifra.models

data class Cifras(
    val id: Long,
    val name: String,
    val tone: Tones,
    val singerName: String,
    val chordsSequence: MutableList<MutableList<Tones>>
)
