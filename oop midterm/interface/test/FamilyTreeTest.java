package test;

import core.FamilyTree;
import factory.PersonFactory;
import model.Gender;
import model.Person;
import util.IdGenerator;

/**
 * Unit tests for Family Tree functionality.
 */
public class FamilyTreeTest {

    public static void main(String[] args) {
        System.out.println("Running Family Tree Tests...\n");

        int passed = 0;
        int total = 0;

        total++; if (testAddPerson()) passed++;
        total++; if (testParentChildRelationship()) passed++;
        total++; if (testMaxTwoParents()) passed++;
        total++; if (testCyclePrevention()) passed++;
        total++; if (testMarriage()) passed++;
        total++; if (testDoubleMarriage()) passed++;
        total++; if (testSiblings()) passed++;
        total++; if (testAncestors()) passed++;
        total++; if (testDescendants()) passed++;
        total++; if (testInvalidYears()) passed++;
        total++; if (testUnknownId()) passed++;

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Tests passed: " + passed + "/" + total);
        System.out.println("=".repeat(50));
    }

    private static boolean testAddPerson() {
        System.out.print("Test: Add Person... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();
            Person p = PersonFactory.createPerson("John Doe", Gender.MALE, 1980, null);
            tree.addPerson(p);

            Person retrieved = tree.getPerson(p.getId());
            assert retrieved.getFullName().equals("John Doe");
            assert retrieved.getGender() == Gender.MALE;
            assert retrieved.getBirthYear() == 1980;

            System.out.println("PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParentChildRelationship() {
        System.out.print("Test: Parent-Child Relationship... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person parent = PersonFactory.createPerson("Parent", Gender.FEMALE, 1970, null);
            Person child = PersonFactory.createPerson("Child", Gender.MALE, 2000, null);

            tree.addPerson(parent);
            tree.addPerson(child);
            tree.addParentChild(parent.getId(), child.getId());

            assert child.hasParent(parent);
            assert parent.getChildren().contains(child);

            System.out.println("PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMaxTwoParents() {
        System.out.print("Test: Max Two Parents... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person parent1 = PersonFactory.createPerson("Parent1", Gender.FEMALE, 1970, null);
            Person parent2 = PersonFactory.createPerson("Parent2", Gender.MALE, 1970, null);
            Person parent3 = PersonFactory.createPerson("Parent3", Gender.MALE, 1970, null);
            Person child = PersonFactory.createPerson("Child", Gender.MALE, 2000, null);

            tree.addPerson(parent1);
            tree.addPerson(parent2);
            tree.addPerson(parent3);
            tree.addPerson(child);

            tree.addParentChild(parent1.getId(), child.getId());
            tree.addParentChild(parent2.getId(), child.getId());

            try {
                tree.addParentChild(parent3.getId(), child.getId());
                System.out.println("FAILED: Should have thrown exception");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("PASSED");
                return true;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCyclePrevention() {
        System.out.print("Test: Cycle Prevention... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person grandparent = PersonFactory.createPerson("Grandparent", Gender.MALE, 1950, null);
            Person parent = PersonFactory.createPerson("Parent", Gender.FEMALE, 1975, null);
            Person child = PersonFactory.createPerson("Child", Gender.MALE, 2000, null);

            tree.addPerson(grandparent);
            tree.addPerson(parent);
            tree.addPerson(child);

            tree.addParentChild(grandparent.getId(), parent.getId());
            tree.addParentChild(parent.getId(), child.getId());

            try {
                tree.addParentChild(child.getId(), grandparent.getId());
                System.out.println("FAILED: Should have thrown exception");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("PASSED");
                return true;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMarriage() {
        System.out.print("Test: Marriage... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person person1 = PersonFactory.createPerson("Person1", Gender.MALE, 1980, null);
            Person person2 = PersonFactory.createPerson("Person2", Gender.FEMALE, 1982, null);

            tree.addPerson(person1);
            tree.addPerson(person2);
            tree.marry(person1.getId(), person2.getId(), 2010);

            assert person1.getSpouse() == person2;
            assert person2.getSpouse() == person1;
            assert person1.getMarriageYear() == 2010;

            System.out.println("PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDoubleMarriage() {
        System.out.print("Test: Prevent Double Marriage... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person person1 = PersonFactory.createPerson("Person1", Gender.MALE, 1980, null);
            Person person2 = PersonFactory.createPerson("Person2", Gender.FEMALE, 1982, null);
            Person person3 = PersonFactory.createPerson("Person3", Gender.FEMALE, 1985, null);

            tree.addPerson(person1);
            tree.addPerson(person2);
            tree.addPerson(person3);

            tree.marry(person1.getId(), person2.getId(), 2010);

            try {
                tree.marry(person1.getId(), person3.getId(), 2015);
                System.out.println("FAILED: Should have thrown exception");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("PASSED");
                return true;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSiblings() {
        System.out.print("Test: Siblings... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person parent = PersonFactory.createPerson("Parent", Gender.FEMALE, 1970, null);
            Person child1 = PersonFactory.createPerson("Child1", Gender.MALE, 2000, null);
            Person child2 = PersonFactory.createPerson("Child2", Gender.FEMALE, 2002, null);

            tree.addPerson(parent);
            tree.addPerson(child1);
            tree.addPerson(child2);

            tree.addParentChild(parent.getId(), child1.getId());
            tree.addParentChild(parent.getId(), child2.getId());

            var siblings1 = tree.siblingsOf(child1.getId());
            var siblings2 = tree.siblingsOf(child2.getId());

            assert siblings1.size() == 1;
            assert siblings1.contains(child2);
            assert siblings2.size() == 1;
            assert siblings2.contains(child1);

            System.out.println("PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAncestors() {
        System.out.print("Test: Ancestors Query... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person grandparent = PersonFactory.createPerson("Grandparent", Gender.MALE, 1950, null);
            Person parent = PersonFactory.createPerson("Parent", Gender.FEMALE, 1975, null);
            Person child = PersonFactory.createPerson("Child", Gender.MALE, 2000, null);

            tree.addPerson(grandparent);
            tree.addPerson(parent);
            tree.addPerson(child);

            tree.addParentChild(grandparent.getId(), parent.getId());
            tree.addParentChild(parent.getId(), child.getId());

            var ancestors = tree.ancestorsOf(child.getId(), 2);

            assert ancestors.size() == 3;
            assert ancestors.contains(child);
            assert ancestors.contains(parent);
            assert ancestors.contains(grandparent);

            System.out.println("PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDescendants() {
        System.out.print("Test: Descendants Query... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            Person grandparent = PersonFactory.createPerson("Grandparent", Gender.MALE, 1950, null);
            Person parent = PersonFactory.createPerson("Parent", Gender.FEMALE, 1975, null);
            Person child = PersonFactory.createPerson("Child", Gender.MALE, 2000, null);

            tree.addPerson(grandparent);
            tree.addPerson(parent);
            tree.addPerson(child);

            tree.addParentChild(grandparent.getId(), parent.getId());
            tree.addParentChild(parent.getId(), child.getId());

            var descendants = tree.descendantsOf(grandparent.getId(), 2);

            assert descendants.size() == 3;
            assert descendants.contains(grandparent);
            assert descendants.contains(parent);
            assert descendants.contains(child);

            System.out.println("PASSED");
            return true;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInvalidYears() {
        System.out.print("Test: Invalid Years... ");
        try {
            try {
                Person p = PersonFactory.createPerson("Test", Gender.MALE, 2000, 1999);
                System.out.println("FAILED: Should have thrown exception");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("PASSED");
                return true;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }

    private static boolean testUnknownId() {
        System.out.print("Test: Unknown ID... ");
        try {
            IdGenerator.reset();
            FamilyTree tree = new FamilyTree();

            try {
                tree.getPerson("UNKNOWN");
                System.out.println("FAILED: Should have thrown exception");
                return false;
            } catch (IllegalArgumentException e) {
                System.out.println("PASSED");
                return true;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            return false;
        }
    }
}