package ru.yandex.practicum.catsgram.model;
import lombok.EqualsAndHashCode;
import lombok.Data;

@Data
@EqualsAndHashCode
class Person {
    private String firstName;
    private String lastName;
    private int age;
    @EqualsAndHashCode.Exclude
    private String phone;
}