package br.com.suacifra.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun stringToTextViewString(string: String): String {
    return string.replace(",", " ")
}

fun stringOfMutableListToEditTextString(sequenceStringOfMutableList: String): String {
    return sequenceStringOfMutableList.removeAffixOfStringOfList()
}

fun mutableSetToString(mutableSet: MutableSet<String>): String {
    val newMutableSet = mutableSetOf<String>()
    for (eachSet in mutableSet) {
        newMutableSet.add(eachSet.replace(",", ";").removeAffixOfStringOfList())
    }
    return newMutableSet.toString().removeAffixOfStringOfList()
}

fun stringToMutableSet(string: String): MutableSet<String> {
    val mutableSet: MutableSet<String> = string.split(",").toMutableSet()
    val newMutableSet = mutableSetOf<String>()
    for (eachSet in mutableSet) {
        newMutableSet.add(eachSet.replace(";", ","))
    }
    return newMutableSet
}

// Regex [A-GmM79#b,]+
// Validate List of Sequence Chords like: "C,G C,G,Am,F"

// Regex [A-G#b]{1,2}
// Validate tone of Song: "C" "G#" "Db"

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
