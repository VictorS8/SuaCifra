package br.com.suacifra.data.models

data class Cifra(
    val id: Int,
    var name: String,
    var tone: String?,
    var singerName: String?,
    var sequenceList: List<String>?
)
