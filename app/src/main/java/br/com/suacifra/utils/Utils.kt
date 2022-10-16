package br.com.suacifra.utils

fun mutableListToString(mutableList: MutableList<String>): String {
    var stringList = ""
    for (eachString in mutableList) {
        stringList += "$eachString  "
    }
    return stringList
}
