package br.com.suacifra.models

data class Cifras(
    val id: Long,
    var name: String,
    var tone: String,
    var singerName: String,
    var chordsSequence: MutableList<MutableList<String>>
)
