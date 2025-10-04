package org.fomenko.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "\"pets\"")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String type;

    @Column(name = "owner_id")
    private Integer ownerId;

    public Pet(String name, int age, String type, Integer ownerId) {
        validateName(name);
        validateAge(age);

        this.name = name;
        this.age = age;
        this.type = type;
        this.ownerId = ownerId;
    }

    public Pet() {}

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
        validateType(type);
        this.type = type;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        validateOwnerId(ownerId);
        this.ownerId = ownerId;
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

    private void validateType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return id != null && id.equals(pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
