package br.com.suacifra.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun mutableCollectionToTextViewString(mutableCollection: MutableCollection<String>): String {
    var stringList = ""
    for (eachString in mutableCollection) {
        stringList += "$eachString  "
    }
    return stringList
}

fun stringOfMutableListToEditTextString(sequenceStringOfMutableList: String): String {
    return sequenceStringOfMutableList.removeAffixOfStringOfList()
}

fun mutableCollectionToMutableListOfString(sequenceMutableCollection: MutableCollection<String>): MutableList<String> {
    return sequenceMutableCollection.stringOfListToMutableList()
}

fun mutableCollectionToString(sequenceMutableCollection: MutableCollection<String>): String {
    return sequenceMutableCollection.stringOfListToMutableList().toString()
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
            eachString.removeAffixOfStringOfList().split(",")
                .toMutableList()
        )
    }
    return mutableList
}

// Extensions
fun MutableSet<String>?.addStringAt(editSequenceIndex: Int, value: String): MutableSet<String> {
    val auxMutableList: MutableList<String> = mutableListOf()
    val stringAsMutableList: String = value.split(",").toString()
    var counter = 0
    if (this != null) {
        for (each in this) {
            if (counter != editSequenceIndex)
                auxMutableList.add(each)
            else
                auxMutableList.add(stringAsMutableList)
            counter += 1
        }
    }
    return auxMutableList.toMutableSet()
}

fun MutableCollection<String>.stringOfListToMutableList(): MutableList<String> {
    return this.toString().trim().removePrefix("[").removeSuffix("]")
        .replace(" ", "").split(",")
        .toMutableList()
}

fun String.removeAffixOfStringOfList(): String {
    return this.trim().removePrefix("[").removeSuffix("]").replace(" ", "")
}

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}
