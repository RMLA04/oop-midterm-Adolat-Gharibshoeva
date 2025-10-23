# Family Tree OOP Project

## Overview
This is an in-memory Family Tree application that models people and their relationships. It demonstrates object-oriented programming principles including encapsulation, inheritance, polymorphism, and design patterns.

## Features
- Add people with basic information (name, gender, birth/death years)
- Establish parent-child relationships with validation
- Record marriages between people
- Query ancestors and descendants up to specified generations
- Find siblings based on shared parents
- Display person information
- Prevent invalid operations (cycles, duplicate parents, bigamy)

## OOP Concepts Demonstrated

### 1. Encapsulation
- All fields in `Person` class are private with controlled access through getters/setters
- Validation enforced in setters (e.g., birth year must be before death year)
- Internal collections returned as copies to prevent external modification

### 2. Inheritance & Polymorphism
- Abstract base class: `Person`
- Concrete subclasses: `Adult` and `Minor`
- Polymorphic method: `getPersonType()` returns different values based on actual type
- Factory pattern determines which subclass to instantiate based on age

### 3. Abstraction
- Interfaces define contracts: `Renderer`, `NodeVisitor`
- Abstract class `Person` defines common behavior for all people
- Implementation details hidden behind interfaces

### 4. Composition & Aggregation
- `FamilyTree` aggregates multiple `Person` objects
- `PersonNode` uses composition to build tree structures
- `Person` has relationships with other `Person` objects

## Design Patterns Implemented

### 1. Composite Pattern (Required)
**Location:** `composite/PersonNode.java`

The `PersonNode` class represents both leaf nodes (people without descendants) and composite nodes (people with descendants) uniformly. This enables:
- Recursive tree traversal using `traverse()` method
- Building ancestor/descendant trees with `buildAncestorTree()` and `buildDescendantTree()`
- Uniform operations on any subtree

### 2. Strategy Pattern
**Location:** `strategy/` package

Different rendering strategies can be plugged in:
- `Renderer` interface defines the contract
- `IndentedTreeRenderer` - displays tree with indentation
- `LineRenderer` - displays as compact lines
- `FamilyTree` uses a default renderer but can switch strategies at runtime

### 3. Factory Method Pattern
**Location:** `factory/PersonFactory.java`

Centralizes object creation with validation:
- Generates unique IDs automatically
- Determines whether to create `Adult` or `Minor` based on age
- Validates all inputs before object creation
- Encapsulates creation logic

### 4. Singleton Pattern (Implicit)
**Location:** `util/IdGenerator.java`

Static utility class that maintains a single counter for generating unique IDs:
- Private constructor prevents instantiation
- Static methods provide global access point
- Maintains state across the application

## Project Structure

```
FamilyTreeProject/
├── src/
│   ├── Main.java                          # Entry point
│   ├── model/
│   │   ├── Person.java                    # Abstract base class
│   │   ├── Adult.java                     # Concrete subclass
│   │   ├── Minor.java                     # Concrete subclass
│   │   └── Gender.java                    # Enum
│   ├── factory/
│   │   └── PersonFactory.java             # Factory Method pattern
│   ├── composite/
│   │   ├── PersonNode.java                # Composite pattern
│   │   └── NodeVisitor.java               # Visitor interface
│   ├── strategy/
│   │   ├── Renderer.java                  # Strategy interface
│   │   ├── IndentedTreeRenderer.java      # Concrete strategy
│   │   └── LineRenderer.java              # Concrete strategy
│   ├── core/
│   │   └── FamilyTree.java                # Main registry
│   ├── util/
│   │   └── IdGenerator.java               # ID generation utility
│   ├── cli/
│   │   └── CLI.java                       # Command-line interface
│   └── test/
│       └── FamilyTreeTest.java            # Unit tests
├── README.md
└── UML_Diagram.png
```

## Available Commands

```
ADD_PERSON "<Full Name>" <Gender> <BirthYear> [DeathYear]
  - Gender: MALE, FEMALE, OTHER
  - Example: ADD_PERSON "John Doe" MALE 1980

ADD_PARENT_CHILD <parentId> <childId>
  - Example: ADD_PARENT_CHILD P001 P002

MARRY <personAId> <personBId> <Year>
  - Example: MARRY P001 P002 2005

ANCESTORS <personId> <generations>
  - Example: ANCESTORS P003 2

DESCENDANTS <personId> <generations>
  - Example: DESCENDANTS P001 2

SIBLINGS <personId>
  - Example: SIBLINGS P003

SHOW <personId>
  - Example: SHOW P001

HELP
  - Display command list

EXIT
  - Quit application
```

## Sample Session

```
> ADD_PERSON "Aizada Toktobekova" FEMALE 1975
-> P001

> ADD_PERSON "Nurlan Shaidullaev" MALE 1983
-> P002

> ADD_PERSON "Aman Shaidullaev" MALE 2010
-> P003

> MARRY P001 P002 2009
OK

> ADD_PARENT_CHILD P001 P003
OK

> ADD_PARENT_CHILD P002 P003
OK

> ANCESTORS P003 2
- P003 Aman Shaidullaev (b.2010)
  - P001 Aizada Toktobekova (b.1975)
  - P002 Nurlan Shaidullaev (b.1983)

> SIBLINGS P003
<none>

> SHOW P001
P001 | Aizada Toktobekova | FEMALE | b.1975 | spouse=P002 | children=1
```

## Edge Cases Handled

1. **Third Parent Prevention**: Attempting to add a third parent throws `IllegalArgumentException`
2. **Cycle Prevention**: Cannot make a child become their own ancestor
3. **Bigamy Prevention**: Cannot marry someone who is already married
4. **Invalid Years**: Death year cannot be before birth year
5. **Unknown IDs**: Clear error messages for non-existent person IDs
6. **Duplicate IDs**: Prevented through factory-generated unique IDs

## Validation Rules

- Name cannot be blank
- Birth year must be between 1800 and 2100
- Death year must be after birth year (if provided)
- Maximum 2 parents per person
- Only one active spouse at a time
- No cycles in family tree

## Technical Details

- **Language**: Java 17+
- **Libraries**: Standard Java library only (no external dependencies)
- **Storage**: In-memory using Java Collections (HashMap, ArrayList, HashSet)
- **Performance**: Optimized for up to 100 people
- **Error Handling**: IllegalArgumentException for invalid operations

## Testing

The `FamilyTreeTest` class includes unit tests for:
- Adding persons
- Parent-child relationships
- Maximum two parents constraint
- Cycle prevention
- Marriage functionality
- Double marriage prevention
- Sibling queries
- Ancestor/descendant queries
- Invalid input validation
- Unknown ID handling

Run tests to verify all core functionality works correctly.

## ADOLAT GHARIBSHOEVA, CS 2028

Midterm Practical Project - Family Tree OOP Application
