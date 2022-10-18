package br.com.suacifra.utils

fun mutableCollectionToTextViewString(mutableCollection: MutableCollection<String>): String {
    var stringList = ""
    for (eachString in mutableCollection) {
        stringList += "$eachString  "
    }
    return stringList
}

fun stringOfMutableListToEditTextString(sequenceStringOfMutableList: String): String {
    return sequenceStringOfMutableList.removePrefix("[").removeSuffix("]").replace(" ", "")
}

fun mutableCollectionToMutableListOfString(sequenceMutableCollection: MutableCollection<String>): MutableList<String> {
    return sequenceMutableCollection.toString().trim().removePrefix("[").removeSuffix("]")
        .replace(" ", "").split(",")
        .toMutableList()
}

fun mutableCollectionToString(sequenceMutableCollection: MutableCollection<String>): String {
    return sequenceMutableCollection.toString().trim().removePrefix("[").removeSuffix("]")
        .replace(" ", "").split(",")
        .toMutableList().toString()
}

fun mutableCollectionToSetOfStringCifras(mutableList: MutableList<MutableList<String>>): MutableSet<String> {
    val mutableSet: MutableSet<String> = mutableSetOf()
    for (eachMutableListOfString in mutableList) {
        mutableSet.add(mutableCollectionToString(eachMutableListOfString))
    }
    return mutableSet
}

fun mutableSetToMutableListOfString(mutableSet: MutableSet<String>): MutableList<MutableList<String>> {
    val mutableList: MutableList<MutableList<String>> = mutableListOf()
    for (eachString in mutableSet) {
        mutableList.add(
            eachString.trim().removePrefix("[").removeSuffix("]").replace(" ", "").split(",")
                .toMutableList()
        )
    }
    return mutableList
}
