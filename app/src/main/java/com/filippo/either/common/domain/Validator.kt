package com.filippo.either.common.domain

interface Validator {
    infix fun then(other: Validator) =
        if (other == Validator) this else CombinedValidator(this, other)

    fun validate(): List<Boolean>

    companion object : Validator {
        override infix fun then(other: Validator) = other
        override fun validate() = emptyList<Boolean>()
    }
}

class CombinedValidator(private val previous: Validator, private val new: Validator) : Validator {
    override fun validate(): List<Boolean> =
        previous.validate() + new.validate()
}

class FirstValidator(private val input: String) : Validator {
    override fun validate(): List<Boolean> {
        println("DUPA validatingFirst")
        return listOf(true)
    }
}

fun Validator.firstValidation(input: String) = then(FirstValidator(input))

class SecondValidator(private val input: String) : Validator {
    override fun validate(): List<Boolean> {
        println("DUPA validatingSecond")
        return listOf(false)
    }
}

fun Validator.secondValidation(input: String) = then(SecondValidator(input))

class ThirdValidator(private val input: String) : Validator {
    override fun validate(): List<Boolean> {
        println("DUPA validatingThird")
        return listOf(false)
    }
}

fun Validator.thirdValidation(input: String) = then(SecondValidator(input))
