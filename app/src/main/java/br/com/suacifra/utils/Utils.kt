package br.com.suacifra.utils

fun mutableCollectionToString(mutableCollection: MutableCollection<String>): String {
    var stringList = ""
    for (eachString in mutableCollection) {
        stringList += "$eachString  "
    }
    return stringList
}
