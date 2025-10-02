package org.fomenko.model;

import java.util.Objects;

public class Pet {
    private final int id;
    private String name;
    private int age;
    private String type;
    private Integer ownerId;

    public Pet(int id, String name, int age, String type, Integer ownerId) {
        validateId(id);
        validateName(name);
        validateAge(age);

        this.id = id;
        this.name = name;
        this.age = age;
        this.type = type;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        validateAge(age);
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        validateOwnerId(ownerId);
        this.ownerId = ownerId;
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (!Character.isUpperCase(name.charAt(0))) {
            throw new IllegalArgumentException("Name must start with an uppercase letter");
        }
    }

    private void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }

    private void validateOwnerId(Integer ownerId) {
        if (ownerId != null && ownerId <= 0) {
            throw new IllegalArgumentException("Owner ID must be greater than 0 or null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Pet pet = (Pet) o;
        return id == pet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pet: " + name +
                " (ID: " + id +
                ", age: " + age +
                ", type: " + type +
                ", ownerId: " + (ownerId != null ? ownerId : "no owner") +
                ")";
    }
}
