package br.ufpr.hpapi.api

enum class HogwartsHouse(val displayName: String) {
    GRYFFINDOR("gryffindor"),
    SLYTHERIN("slytherin"),
    RAVENCLAW("ravenclaw"),
    HUFFLEPUFF("hufflepuff");

    companion object {
        fun from(value: String): HogwartsHouse? =
            values().find { it.displayName.equals(value, ignoreCase = true) }
    }
}