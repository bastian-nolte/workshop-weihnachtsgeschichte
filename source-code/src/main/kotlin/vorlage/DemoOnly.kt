@file:Suppress("unused", "FunctionName", "SpellCheckingInspection")

package app.codedojo.kata.weihnachtsgeschichte.vorlage

import java.util.*

interface Hund {
    val fellfarbe: String // nur getter
    var alter: Int        // getter und setter

    fun `gib Futter`(menge: Int): Unit { // void in Java
        println("Wuff… wedel…") // system.out.println
    }
}

class Terrier : Hund {
    override val fellfarbe: String
        get() = "schwarz"

    override var alter: Int
        get() {
            TODO()
        }
        set(value) {
            TODO()
        }

    override fun `gib Futter`(menge: Int) {
        println("wuff…")
    }
}
