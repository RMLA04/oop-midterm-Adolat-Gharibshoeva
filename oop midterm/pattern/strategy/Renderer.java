package strategy;

import composite.PersonNode;

/**
 * Strategy interface for rendering person trees.
 * Demonstrates Strategy design pattern.
 */
public interface Renderer {
    String render(PersonNode root);
}