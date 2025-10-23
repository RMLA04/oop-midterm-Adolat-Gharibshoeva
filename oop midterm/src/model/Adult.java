package model;

/**
 * Concrete implementation representing an adult person.
 * Demonstrates inheritance and polymorphism.
 */
public class Adult extends Person {

    public Adult(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    @Override
    public String getPersonType() {
        return "Adult";
    }
}