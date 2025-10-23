package strategy;

import composite.PersonNode;
import model.Person;

/**
 * Renders a person tree with indentation.
 * Concrete Strategy implementation.
 */
public class IndentedTreeRenderer implements Renderer {

    @Override
    public String render(PersonNode root) {
        StringBuilder sb = new StringBuilder();
        renderNode(root, 0, sb);
        return sb.toString().trim();
    }

    private void renderNode(PersonNode node, int level, StringBuilder sb) {
        Person person = node.getPerson();

        // Add indentation
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }

        sb.append("- ").append(person.getId()).append(" ")
                .append(person.getFullName())
                .append(" (b.").append(person.getBirthYear());

        if (person.getDeathYear() != null) {
            sb.append(", d.").append(person.getDeathYear());
        }

        sb.append(")\n");

        // Render children
        for (PersonNode child : node.getChildren()) {
            renderNode(child, level + 1, sb);
        }
    }
}