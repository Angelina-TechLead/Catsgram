package ru.yandex.practicum.catsgram.model;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class Person {
    private String firstName;
    private String lastName;
    private int age;
    @EqualsAndHashCode.Exclude
    private String phone;

    public Person(String firstName, String lastName, int age, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
    }
}