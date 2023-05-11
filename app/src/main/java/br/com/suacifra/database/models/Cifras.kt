package br.com.suacifra.database.models

data class Cifras(
    var id: Int,
    var name: String,
    var tone: String,
    var singerName: String,
    var sequenceList: MutableList<String>
)
