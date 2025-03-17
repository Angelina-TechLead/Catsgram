package ru.yandex.practicum.catsgram.model;

public class Practicum {
    public static void main(String[] args) {
        Person roman1 = new Person("Roman", "Igorev", 38, "+78889991234");
        Person roman2 = new Person("Roman", "Igorev", 38, "");
        if(roman1.equals(roman2)) {
            System.out.println("Это один и тот же человек");
        } else {
            System.out.println("Это разные люди");
        }
    }
}