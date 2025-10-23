package core;

import composite.PersonNode;
import model.Person;
import strategy.IndentedTreeRenderer;
import strategy.Renderer;

import java.util.*;

/**
 * Main registry for managing the family tree.
 * Demonstrates composition and aggregation.
 */
public class FamilyTree {
    private final Map<String, Person> people;
    private final Renderer defaultRenderer;

    public FamilyTree() {
        this.people = new HashMap<>();
        this.defaultRenderer = new IndentedTreeRenderer();
    }

    /**
     * Add a person to the tree.
     */
    public void addPerson(Person person) {
        if (people.containsKey(person.getId())) {
            throw new IllegalArgumentException("Person with ID " + person.getId() + " already exists");
        }
        people.put(person.getId(), person);
    }

    /**
     * Get a person by ID.
     */
    public Person getPerson(String id) {
        Person person = people.get(id);
        if (person == null) {
            throw new IllegalArgumentException("Person with ID " + id + " not found");
        }
        return person;
    }

    /**
     * Link parent and child, enforcing rules.
     */
    public void addParentChild(String parentId, String childId) {
        Person parent = getPerson(parentId);
        Person child = getPerson(childId);

        // Check for cycles
        if (isAncestor(child, parent)) {
            throw new IllegalArgumentException("Cannot create cycle: " + childId + " is ancestor of " + parentId);
        }

        // Add relationships
        child.addParent(parent);
        parent.addChild(child);
    }

    /**
     * Check if person1 is an ancestor of person2.
     */
    private boolean isAncestor(Person person1, Person person2) {
        if (person1.equals(person2)) {
            return true;
        }

        Set<Person> visited = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();
        queue.add(person2);

        while (!queue.isEmpty()) {
            Person current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            if (current.equals(person1)) {
                return true;
            }

            if (current.getParent1() != null) {
                queue.add(current.getParent1());
            }
            if (current.getParent2() != null) {
                queue.add(current.getParent2());
            }
        }

        return false;
    }

    /**
     * Marry two people.
     */
    public void marry(String personAId, String personBId, int year) {
        Person personA = getPerson(personAId);
        Person personB = getPerson(personBId);

        personA.setSpouse(personB, year);
        personB.setSpouse(personA, year);
    }

    /**
     * Get ancestors up to specified generations.
     */
    public List<Person> ancestorsOf(String personId, int generations) {
        Person person = getPerson(personId);
        List<Person> ancestors = new ArrayList<>();
        collectAncestors(person, generations, ancestors, new HashSet<>());
        return ancestors;
    }

    private void collectAncestors(Person person, int generations, List<Person> result, Set<Person> visited) {
        if (person == null || visited.contains(person) || generations < 0) {
            return;
        }

        visited.add(person);
        result.add(person);

        if (generations > 0) {
            collectAncestors(person.getParent1(), generations - 1, result, visited);
            collectAncestors(person.getParent2(), generations - 1, result, visited);
        }
    }

    /**
     * Get descendants up to specified generations.
     */
    public List<Person> descendantsOf(String personId, int generations) {
        Person person = getPerson(personId);
        List<Person> descendants = new ArrayList<>();
        collectDescendants(person, generations, descendants, new HashSet<>());
        return descendants;
    }

    private void collectDescendants(Person person, int generations, List<Person> result, Set<Person> visited) {
        if (person == null || visited.contains(person) || generations < 0) {
            return;
        }

        visited.add(person);
        result.add(person);

        if (generations > 0) {
            for (Person child : person.getChildren()) {
                collectDescendants(child, generations - 1, result, visited);
            }
        }
    }

    /**
     * Get siblings (share at least one parent).
     */
    public List<Person> siblingsOf(String personId) {
        Person person = getPerson(personId);
        Set<Person> siblings = new HashSet<>();

        if (person.getParent1() != null) {
            for (Person child : person.getParent1().getChildren()) {
                if (!child.equals(person)) {
                    siblings.add(child);
                }
            }
        }

        if (person.getParent2() != null) {
            for (Person child : person.getParent2().getChildren()) {
                if (!child.equals(person)) {
                    siblings.add(child);
                }
            }
        }

        return new ArrayList<>(siblings);
    }

    /**
     * Get children of a person.
     */
    public List<Person> childrenOf(String personId) {
        Person person = getPerson(personId);
        return person.getChildren();
    }

    /**
     * Get spouse of a person.
     */
    public Person spouseOf(String personId) {
        Person person = getPerson(personId);
        return person.getSpouse();
    }

    /**
     * Render ancestors tree.
     */
    public String renderAncestors(String personId, int generations) {
        Person person = getPerson(personId);
        PersonNode tree = PersonNode.buildAncestorTree(person, generations);
        return defaultRenderer.render(tree);
    }

    /**
     * Render descendants tree.
     */
    public String renderDescendants(String personId, int generations) {
        Person person = getPerson(personId);
        PersonNode tree = PersonNode.buildDescendantTree(person, generations);
        return defaultRenderer.render(tree);
    }
}