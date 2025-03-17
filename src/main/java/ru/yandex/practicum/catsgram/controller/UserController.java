package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private Long idCounter = 1L;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        }
        return ResponseEntity.ok(new ArrayList<>(users.values())); // Конвертация Map в List
    }



    // POST /users — Добавление нового пользователя
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) throws ConditionsNotMetException, DuplicatedDataException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }

        if (users.values().stream().anyMatch(existingUser -> existingUser.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);

        return ResponseEntity.ok().body(user); // Возвращает созданного пользователя
    }

    // PUT /users — Обновление данных пользователя
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) throws ConditionsNotMetException, DuplicatedDataException {
        if (updatedUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        User existingUser = users.get(updatedUser.getId());
        if (existingUser == null) {
            throw new ConditionsNotMetException("Пользователь с указанным Id не найден");
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            if (users.values().stream().anyMatch(user -> user.getEmail().equals(updatedUser.getEmail()) && !user.getId().equals(updatedUser.getId()))) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        return ResponseEntity.ok(existingUser); // Возвращает обновленного пользователя
    }
    
    // Метод для генерации уникального идентификатора
    private Long getNextId() {
        return idCounter++;
    }
}
