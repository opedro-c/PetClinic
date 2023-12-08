package br.ufrn.petclinic;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.petclinic.models.Pet;

public class PetsSingleton {

    private static PetsSingleton instance;
    public final List<Pet> pets = new ArrayList<>();

    public static PetsSingleton getInstance() {
        if (instance == null) {
            instance = new PetsSingleton();
        }
        return instance;
    }
}
