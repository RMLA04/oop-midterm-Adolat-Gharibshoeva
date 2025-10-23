package strategy;

import composite.PersonNode;
import model.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * Renders a person tree as compact lines.
 * Concrete Strategy implementation.
 */
public class LineRenderer implements Renderer {

    @Override
    public String render(PersonNode root) {
        List<String> lines = new ArrayList<>();
        collectLines(root, lines);
        return String.join("\n", lines);
    }

    private void collectLines(PersonNode node, List<String> lines) {
        Person person = node.getPerson();

        StringBuilder sb = new StringBuilder();
        sb.append(person.getId()).append(" - ")
                .append(person.getFullName())
                .append(" (").append(person.getGender())
                .append(", b.").append(person.getBirthYear());

        if (person.getDeathYear() != null) {
            sb.append(", d.").append(person.getDeathYear());
        }

        sb.append(")");
        lines.add(sb.toString());

        // Collect children
        for (PersonNode child : node.getChildren()) {
            collectLines(child, lines);
        }
    }
}