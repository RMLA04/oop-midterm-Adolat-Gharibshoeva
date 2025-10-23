package composite;

import model.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the family tree structure.
 * Implements Composite pattern - can contain children.
 */
public class PersonNode {
    private final Person person;
    private final List<PersonNode> children;

    public PersonNode(Person person) {
        this.person = person;
        this.children = new ArrayList<>();
    }

    public Person getPerson() {
        return person;
    }

    public void addChild(PersonNode child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public List<PersonNode> getChildren() {
        return new ArrayList<>(children);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Composite operation - apply operation to this node and all descendants.
     */
    public void traverse(NodeVisitor visitor) {
        visitor.visit(this);
        for (PersonNode child : children) {
            child.traverse(visitor);
        }
    }

    /**
     * Build a tree structure for descendants up to specified generations.
     */
    public static PersonNode buildDescendantTree(Person root, int generations) {
        PersonNode node = new PersonNode(root);
        if (generations > 0) {
            for (Person child : root.getChildren()) {
                PersonNode childNode = buildDescendantTree(child, generations - 1);
                node.addChild(childNode);
            }
        }
        return node;
    }

    /**
     * Build a tree structure for ancestors up to specified generations.
     */
    public static PersonNode buildAncestorTree(Person root, int generations) {
        PersonNode node = new PersonNode(root);
        if (generations > 0) {
            if (root.getParent1() != null) {
                PersonNode parent1Node = buildAncestorTree(root.getParent1(), generations - 1);
                node.addChild(parent1Node);
            }
            if (root.getParent2() != null) {
                PersonNode parent2Node = buildAncestorTree(root.getParent2(), generations - 1);
                node.addChild(parent2Node);
            }
        }
        return node;
    }
}