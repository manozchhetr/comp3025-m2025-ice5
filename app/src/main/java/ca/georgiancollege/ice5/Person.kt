package ca.georgiancollege.ice5

class Person(private var name: String, private var age: Float)
{
    // Properties with custom getters and setters
    var Name: String
        get() = name
        set(value) {
            if (value.isNotBlank()) name = value
        }
    var Age: Float
        get() = age
        set(value) {
            if (value >= 0) age = value
        }
}