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

// TODO - Add Regex to validate sequence
// Regex [A-GmM79#b,]+
// Validate List of Sequence Chords like: "C,G C,G,Am,F" or like sequence "C,G,Am,F"

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

fun String.dataStringToMutableSet(): MutableSet<String> {
    return if (this.isNotBlank()) {
        val listOfString = this.split(" ")
        listOfString.toMutableSet()
    } else {
        mutableSetOf()
    }
}

fun MutableSet<String>.dataMutableSetToString(): String {
    val newMutableList = mutableListOf<String>()
    return if (this.size != 0) {
        for (each in this) {
            newMutableList.add(each.replace(",", ";"))
        }
        newMutableList.toString().mutableListStringToString()
    } else {
        ""
    }
}

private fun String.mutableListStringToString(): String {
    return this.replace(" ", "")
        .removePrefix("[").removeSuffix("]")
        .replace(",", " ").replace(";", ",")
}
