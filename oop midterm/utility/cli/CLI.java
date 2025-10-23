package cli;

import core.FamilyTree;
import factory.PersonFactory;
import model.Gender;
import model.Person;

import java.util.List;
import java.util.Scanner;

/**
 * Command-line interface for the Family Tree application.
 */
public class CLI {
    private final FamilyTree familyTree;
    private final Scanner scanner;

    public CLI() {
        this.familyTree = new FamilyTree();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Family Tree Application");
        System.out.println("Type 'HELP' for commands, 'EXIT' to quit");
        System.out.println();

        while (true) {
            try {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue;
                }

                if (input.equalsIgnoreCase("EXIT")) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (input.equalsIgnoreCase("HELP")) {
                    printHelp();
                    continue;
                }

                processCommand(input);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void processCommand(String input) {
        String[] parts = splitCommand(input);
        String command = parts[0].toUpperCase();

        switch (command) {
            case "ADD_PERSON":
                handleAddPerson(parts);
                break;
            case "ADD_PARENT_CHILD":
                handleAddParentChild(parts);
                break;
            case "MARRY":
                handleMarry(parts);
                break;
            case "ANCESTORS":
                handleAncestors(parts);
                break;
            case "DESCENDANTS":
                handleDescendants(parts);
                break;
            case "SIBLINGS":
                handleSiblings(parts);
                break;
            case "SHOW":
                handleShow(parts);
                break;
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Type 'HELP' for available commands");
        }
    }

    private String[] splitCommand(String input) {
        // Handle quoted strings
        List<String> parts = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (current.length() > 0) {
                    parts.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            parts.add(current.toString());
        }

        return parts.toArray(new String[0]);
    }

    private void handleAddPerson(String[] parts) {
        if (parts.length < 4) {
            System.out.println("Usage: ADD_PERSON \"<Full Name>\" <Gender> <BirthYear> [DeathYear]");
            return;
        }

        String name = parts[1];
        Gender gender = Gender.valueOf(parts[2].toUpperCase());
        int birthYear = Integer.parseInt(parts[3]);
        Integer deathYear = parts.length > 4 ? Integer.parseInt(parts[4]) : null;

        Person person = PersonFactory.createPerson(name, gender, birthYear, deathYear);
        familyTree.addPerson(person);

        System.out.println("-> " + person.getId());
    }

    private void handleAddParentChild(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: ADD_PARENT_CHILD <parentId> <childId>");
            return;
        }

        familyTree.addParentChild(parts[1], parts[2]);
        System.out.println("OK");
    }

    private void handleMarry(String[] parts) {
        if (parts.length < 4) {
            System.out.println("Usage: MARRY <personAId> <personBId> <Year>");
            return;
        }

        familyTree.marry(parts[1], parts[2], Integer.parseInt(parts[3]));
        System.out.println("OK");
    }

    private void handleAncestors(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: ANCESTORS <personId> <generations>");
            return;
        }

        String result = familyTree.renderAncestors(parts[1], Integer.parseInt(parts[2]));
        System.out.println(result);
    }

    private void handleDescendants(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: DESCENDANTS <personId> <generations>");
            return;
        }

        String result = familyTree.renderDescendants(parts[1], Integer.parseInt(parts[2]));
        System.out.println(result);
    }

    private void handleSiblings(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: SIBLINGS <personId>");
            return;
        }

        List<Person> siblings = familyTree.siblingsOf(parts[1]);

        if (siblings.isEmpty()) {
            System.out.println("<none>");
        } else {
            for (Person sibling : siblings) {
                System.out.println(sibling.getId() + " - " + sibling.getFullName());
            }
        }
    }

    private void handleShow(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: SHOW <personId>");
            return;
        }

        Person person = familyTree.getPerson(parts[1]);
        System.out.println(person.toString());
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  ADD_PERSON \"<Full Name>\" <Gender> <BirthYear> [DeathYear]");
        System.out.println("    Gender: MALE, FEMALE, OTHER");
        System.out.println("  ADD_PARENT_CHILD <parentId> <childId>");
        System.out.println("  MARRY <personAId> <personBId> <Year>");
        System.out.println("  ANCESTORS <personId> <generations>");
        System.out.println("  DESCENDANTS <personId> <generations>");
        System.out.println("  SIBLINGS <personId>");
        System.out.println("  SHOW <personId>");
        System.out.println("  EXIT");
    }
}