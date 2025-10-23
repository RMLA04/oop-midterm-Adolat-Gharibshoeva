package factory;

import model.Adult;
import model.Gender;
import model.Minor;
import model.Person;
import util.IdGenerator;

/**
 * Factory class for creating Person instances.
 * Demonstrates Factory Method design pattern.
 */
public final class PersonFactory {

    private PersonFactory() {
        // Prevent instantiation
    }

    /**
     * Creates a Person (Adult or Minor based on age) with auto-generated ID.
     */
    public static Person createPerson(String fullName, Gender gender, int birthYear, Integer deathYear) {
        String id = IdGenerator.generateId();
        int currentYear = 2025;

        // Determine if person is/was a minor
        if (deathYear == null) {
            // Person is alive
            int age = currentYear - birthYear;
            if (age < 18) {
                return new Minor(id, fullName, gender, birthYear, deathYear);
            } else {
                return new Adult(id, fullName, gender, birthYear, deathYear);
            }
        } else {
            // Person is deceased - check age at death
            int ageAtDeath = deathYear - birthYear;
            if (ageAtDeath < 18) {
                return new Minor(id, fullName, gender, birthYear, deathYear);
            } else {
                return new Adult(id, fullName, gender, birthYear, deathYear);
            }
        }
    }
}