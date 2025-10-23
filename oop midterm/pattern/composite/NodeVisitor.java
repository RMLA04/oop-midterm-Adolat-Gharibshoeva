package composite;

/**
 * Visitor interface for traversing PersonNode trees.
 */
public interface NodeVisitor {
    void visit(PersonNode node);
}