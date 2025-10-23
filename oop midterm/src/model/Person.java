package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a person in the family tree.
 * Demonstrates abstraction and encapsulation.
 */
public abstract class Person {
    private final String id;
    private String fullName;
    private Gender gender;
    private int birthYear;
    private Integer deathYear;

    private Person parent1;
    private Person parent2;
    private final List<Person> children;
    private Person spouse;
    private Integer marriageYear;
    private Integer divorceYear;

    protected Person(String id, String fullName, Gender gender, int birthYear, Integer deathYear) {
        validateName(fullName);
        validateYears(birthYear, deathYear);

        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.children = new ArrayList<>();
    }

    // Validation methods
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    private void validateYears(int birth, Integer death) {
        if (birth < 1800 || birth > 2100) {
            throw new IllegalArgumentException("Birth year must be between 1800 and 2100");
        }
        if (death != null && death < birth) {
            throw new IllegalArgumentException("Death year cannot be before birth year");
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public Person getParent1() {
        return parent1;
    }

    public Person getParent2() {
        return parent2;
    }

    public List<Person> getChildren() {
        return new ArrayList<>(children);
    }

    public Person getSpouse() {
        return spouse;
    }

    public Integer getMarriageYear() {
        return marriageYear;
    }

    public Integer getDivorceYear() {
        return divorceYear;
    }

    // Setters with validation
    public void setFullName(String fullName) {
        validateName(fullName);
        this.fullName = fullName;
    }

    public void setDeathYear(Integer deathYear) {
        validateYears(this.birthYear, deathYear);
        this.deathYear = deathYear;
    }

    // Computed methods
    public boolean isAlive() {
        return deathYear == null;
    }

    public int ageIn(int year) {
        if (year < birthYear) {
            throw new IllegalArgumentException("Year cannot be before birth");
        }
        int endYear = (deathYear != null && deathYear < year) ? deathYear : year;
        return endYear - birthYear;
    }

    // Relationship management
    public void addParent(Person parent) {
        if (parent1 == null) {
            parent1 = parent;
        } else if (parent2 == null) {
            parent2 = parent;
        } else {
            throw new IllegalArgumentException("Person already has two parents");
        }
    }

    public void addChild(Person child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public void setSpouse(Person spouse, int year) {
        if (this.spouse != null && (divorceYear == null || divorceYear >= year)) {
            throw new IllegalArgumentException("Person is already married");
        }
        this.spouse = spouse;
        this.marriageYear = year;
        this.divorceYear = null;
    }

    public void removeSpouse() {
        this.spouse = null;
    }

    public boolean hasParent(Person person) {
        return person.equals(parent1) || person.equals(parent2);
    }

    // Abstract method for polymorphism
    public abstract String getPersonType();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" | ")
                .append(fullName).append(" | ")
                .append(gender).append(" | ")
                .append("b.").append(birthYear);

        if (deathYear != null) {
            sb.append(" | d.").append(deathYear);
        }

        if (spouse != null) {
            sb.append(" | spouse=").append(spouse.getId());
        }

        sb.append(" | children=").append(children.size());

        return sb.toString();
    }
}