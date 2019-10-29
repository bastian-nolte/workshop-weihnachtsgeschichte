package app.codedojo.kata.fizzbuzz.kotlinws

interface Hund {
    val fellfarbe: String // nur getter
    var alter: Int        // getter und setter

    fun `gib Futter`(menge: Int): Unit { // void in Java
        println("Wuff… wedel…") // system.out.println
    }
}

class Terrier : Hund {
    override val fellfarbe: String
        get() = this.fellfarbe

    override var alter: Int
        get() = this.alter
        set(value) {
            this.alter = value
        }

    override fun `gib Futter`(menge: Int) {
        println("wuff…")
    }
}

class Dackel(override val fellfarbe: String, override var alter: Int = 0) : Hund

// data classes implementieren automatisch
// equals() und hashCode()
// toString() und copy()
data class GoldenRetriever(
        override val fellfarbe: String,
        override var alter: Int) : Hund

interface HatBeine {}
sealed class Haustier(open val alter: Int)
data class Katze(val fellfarbe: String, override val alter: Int) : Haustier(alter), HatBeine
data class Kaiman(val gefresseneGnus: Int, override val alter: Int) : Haustier(alter), HatBeine

fun `beschreibe Haustier`(haustier: Haustier) = when (haustier) {
    is Katze -> println("Der Fell der Katze ist $haustier.fellfarbe.")
    is Kaiman -> print("Der Kaiman hat ${haustier.gefresseneGnus} Gnus gefressen.")
}
