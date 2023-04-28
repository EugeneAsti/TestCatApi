package ru.aeyu.catapitestapp.domain.models

data class Weight(
    val imperial: String,
    val metric: String
){
    constructor() : this("", "")
}