package model;

/**
 * Concrete implementation representing a minor person (under 18).
 * Demonstrates inheritance and polymorphism.
 */
public class Minor extends Person {

    public Minor(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        super(id, fullName, gender, birthYear, deathYear);
    }

    @Override
    public String getPersonType() {
        return "Minor";
    }
}