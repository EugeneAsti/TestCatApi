package ru.aeyu.catapitestapp.domain.models

import java.util.Objects

data class Cat(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
){
    constructor() : this(listOf(), 0, "", "", 0)
    override fun hashCode(): Int {
        return Objects.hash(id, url)
    }

    override fun equals(other: Any?): Boolean {

        return when {
            (other == null) -> {
                false
            }
            (other === this) -> {
                true
            }
            (other !is Cat) -> {
                false
            }
            else -> {
                other.id == id
            }
        }
    }
}