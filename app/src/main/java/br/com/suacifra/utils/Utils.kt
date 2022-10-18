package br.com.suacifra.utils

fun mutableCollectionToTextViewString(mutableCollection: MutableCollection<String>): String {
    var stringList = ""
    for (eachString in mutableCollection) {
        stringList += "$eachString  "
    }
    return stringList
}

fun mutableCollectionToEditTextString(sequenceStringOfMutableList: String): String {
    return sequenceStringOfMutableList.removePrefix("[").removeSuffix("]").replace(" ", "")
}

fun mutableCollectionToStringSequence(sequenceMutableCollection: MutableCollection<String>): MutableList<String> {
    return sequenceMutableCollection.toString().trim().removePrefix("[").removeSuffix("]").replace(" ", "").split(",")
        .toMutableList()
}


