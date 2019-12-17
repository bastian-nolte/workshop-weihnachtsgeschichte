package app.codedojo.kata.weihnachtsgeschichte.vorbereitet

enum class Fall { NOMINATIV, GENITIV, DATIV, AKKUSATIV }

enum class Geschlecht {
    MÄNNLICH {
        override fun unbestimmterArtikel(fall: Fall) = when (fall) {
            Fall.NOMINATIV -> "ein"
            Fall.AKKUSATIV -> "einen"
            Fall.DATIV -> "einem"
            Fall.GENITIV -> "eines"
        }
    },
    WEIBLICH {
        override fun unbestimmterArtikel(fall: Fall) = when (fall) {
            Fall.NOMINATIV, Fall.AKKUSATIV -> "eine"
            Fall.DATIV, Fall.GENITIV -> "einer"
        }
    },
    SACHLICH {
        override fun unbestimmterArtikel(fall: Fall) = when (fall) {
            Fall.NOMINATIV, Fall.AKKUSATIV -> "ein"
            Fall.DATIV -> "einem"
            Fall.GENITIV -> "eines"
        }
    };

    abstract fun unbestimmterArtikel(fall: Fall): String
}
