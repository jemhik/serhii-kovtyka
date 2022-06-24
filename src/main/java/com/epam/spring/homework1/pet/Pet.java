package com.epam.spring.homework1.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Pet {
    private List<Animal> pets;

    @Autowired
    public Pet(List<Animal> pets) {
        this.pets = pets;
    }

    public void printPets(){
        for (Animal pet : pets) {
            System.out.println(pet.getClass().getSimpleName());
        }
    }
}
