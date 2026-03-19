---
layout: default.md
title: "UI/UX Design Document"
pageNav: 3
---

# Hairy Pawter - UI/UX Design Document

This document outlines the user interface and user experience design decisions for Hairy Pawter, a pet grooming business management application.

---

## Table of Contents

1. [Design Principles](#design-principles)
2. [Target User Profile](#target-user-profile)
3. [Main Window Layout](#main-window-layout)
4. [List Panel Design](#list-panel-design)
5. [Detail Panel Design](#detail-panel-design)
6. [Command Reference](#command-reference)
7. [Model Changes](#model-changes)
8. [Alternative Layouts](#alternative-layouts)
9. [Implementation Phases](#implementation-phases)

---

## Design Principles

### CLI-First Approach

Hairy Pawter is designed for users who can type fast and prefer keyboard input over mouse interactions. The following principles guide the design:

| Principle | Description |
|-----------|-------------|
| **Single input point** | All actions are performed via the command box only |
| **No mouse-required interactions** | GUI is output/display only - no clickable elements required |
| **Intuitive command naming** | Verb-noun pattern with consistent prefixes |
| **Minimal typing** | Short command words, sensible defaults, shorthand aliases |
| **Discoverability** | `help` shows all commands; `help COMMAND` shows specific usage |

### Visual Hierarchy

The UI prioritizes information based on the pet groomer's workflow:

1. **Primary**: Pet photo + name (What am I grooming?)
2. **Secondary**: Pet details - species, breed, tags (Special requirements?)
3. **Tertiary**: Owner info (Contact details if needed)

---

## Target User Profile

- Runs a pet grooming business
- Manages many repeat clients and their pets
- Requires fast access to pet and client information during work
- Needs reliable record keeping
- Prefers desktop apps over other types
- Can type fast (40+ WPM)
- Prefers typing to mouse interactions
- Is reasonably comfortable typing simple commands

---

## Main Window Layout

### Overview

The application uses a **70/30 split layout** with two main panels:

```
+---------------------------------------------------------------------------------------------+
| Hairy Pawter                                                                 [File] [Help]  |
+---------------------------------------------------------------------------------------------+
| > _                                                                                         |
+---------------------------------------------------------------------------------------------+
| Ready                                                                                       |
+---------------------------------------------------------------------------------------------+
|                                                                           |                 |
|                                                                           |                 |
|                          LIST PANEL (70%)                                 |  DETAIL PANEL   |
|                                                                           |     (30%)       |
|                    Grouped pets-first layout                              |                 |
|                    with horizontally wrapping                             |  Selected pet   |
|                    pet cards                                              |  or client      |
|                                                                           |  details        |
|                                                                           |                 |
|                                                                           |                 |
+---------------------------------------------------------------------------------------------+
| Status Bar: client count, pet count, current view, data file path                           |
+---------------------------------------------------------------------------------------------+
```

### Component Breakdown

| Component | Description | Height |
|-----------|-------------|--------|
| **Title Bar** | Application name, File and Help menus | Fixed |
| **Command Box** | Single-line text input for CLI commands | Fixed (~30px) |
| **Result Display** | Feedback messages from command execution | Fixed (~50px) |
| **List Panel** | Scrollable list of clients and pets (70% width) | Flexible |
| **Detail Panel** | Selected item details with photo (30% width) | Flexible |
| **Status Bar** | Statistics and file path | Fixed (~25px) |

### Split Ratio

- **List Panel**: 70% of available width
- **Detail Panel**: 30% of available width
- Split is fixed (not user-adjustable) to maintain consistency

---

## List Panel Design

### Layout: Option C - Hybrid Grouped Pets-First

The list panel uses a **grouped pets-first** approach where:
- Pets are the visual focus (prominent cards with thumbnails)
- Pets are grouped under their owner (client)
- Pet cards wrap horizontally within each group

### Visual Structure

```
|  LIST PANEL                                                                    |
|--------------------------------------------------------------------------------|
|  > John Doe | 91234567 | john@email.com | 123 Pet Street [VIP] [Regular]       |
|    +------------+ +------------+ +------------+                                |
|    | 1.1  [IMG] | | 1.2  [IMG] | | 1.3  [IMG] |                                |
|    |   Fluffy   | |  Whiskers  | |   Buddy    |                                |
|    | Dog,Golden | | Cat,Persian| | Dog,Poodle |                                |
|    |  [Anxious] | |            | |[Aggressive]|                                |
|    +------------+ +------------+ +------------+                                |
|--------------------------------------------------------------------------------|
|  > Jane Smith | 88887777 | jane@email.com | 456 Avenue [NewClient]             |
|    +------------+                                                              |
|    | 2.1  [IMG] |                                                              |
|    |    Max     |                                                              |
|    | Dog, Husky |                                                              |
|    |            |                                                              |
|    +------------+                                                              |
|--------------------------------------------------------------------------------|
|  > Bob Wilson | 99998888 | bob@email.com | 789 Road                            |
|    (No pets)                                                                   |
|--------------------------------------------------------------------------------|
```

### Client Header Row

Each client is displayed as a header row containing:

| Element | Format | Example |
|---------|--------|---------|
| Index | `N.` | `1.` |
| Name | Full name | `John Doe` |
| Phone | Number | `91234567` |
| Email | Email address | `john@email.com` |
| Address | Full address | `123 Pet Street` |
| Tags | Bracketed labels | `[VIP] [Regular]` |

### Pet Card Design

Each pet is displayed as a card within the client group:

```
+----------------+
| 1.1    [IMG]   |    <- Index + Thumbnail (64x64 px)
|    Fluffy      |    <- Pet name
| Dog, Golden    |    <- Species, Breed
|   [Anxious]    |    <- Tags
+----------------+
```

#### Card Specifications

| Element | Specification |
|---------|---------------|
| **Card width** | ~120 px (fixed) |
| **Card height** | ~100 px (fixed) |
| **Thumbnail size** | 64x64 px (medium) |
| **Cards per row** | 4-5 (depending on window width) |
| **Wrapping** | Horizontal wrap to next row |
| **Placeholder** | Generic pet silhouette when no photo |

### Pet Index Notation

Pet indices use `CLIENT_INDEX.PET_INDEX` format:

| Index | Meaning |
|-------|---------|
| `1.1` | Client 1, Pet 1 |
| `1.2` | Client 1, Pet 2 |
| `2.1` | Client 2, Pet 1 |
| `3.1` | Client 3, Pet 1 |

This notation is displayed on each pet card and used in CLI commands.

### Handling Many Pets

When an owner has multiple pets:

| Pets per Owner | Behavior |
|----------------|----------|
| 1-4 pets | Single row of cards |
| 5-8 pets | Wraps to 2 rows |
| 9+ pets | Continues wrapping, vertically scrollable |

The entire list panel is vertically scrollable. There is no horizontal scrolling within client groups.

### Empty State

When a client has no pets:

```
|  > Bob Wilson | 99998888 | bob@email.com | 789 Road                |
|    (No pets)                                                       |
```

---

## Detail Panel Design

### Overview

The detail panel (30% width) displays expanded information for the currently selected pet or client. It updates automatically based on:

| Trigger | Detail Panel Shows |
|---------|-------------------|
| `view pet INDEX.PETINDEX` | Pet details with photo |
| `view client INDEX` | Client details with pet list |
| `add pet ...` | Newly added pet's details |
| `edit pet ...` | Updated pet's details |
| `add client ...` | Newly added client's details |
| `edit client ...` | Updated client's details |
| `find pet KEYWORD` | First matching pet's details |
| `find client KEYWORD` | First matching client's details |
| `list` | Clears or shows summary |

### Pet Detail View

```
|  DETAIL PANEL              |
|-----------------------------|
|  +---------------------+    |
|  |                     |    |
|  |    [Pet Photo]      |    |
|  |      (Large)        |    |
|  |                     |    |
|  |      Fluffy         |    |
|  +---------------------+    |
|                             |
|  Species: Dog               |
|  Breed:  Golden Retriever   |
|  DOB:    15 Mar 2020        |
|  Tags:   [Anxious]          |
|                             |
|  GROOMING NOTES             |
|  -------------              |
|  Sensitive around ears.     |
|  Use hypoallergenic         |
|  shampoo only.              |
|                             |
|  OWNER                      |
|  -----                      |
|  John Doe                   |
|  Phone: 91234567            |
|  Email: john@email.com      |
|  Addr:  123 Pet Street      |
|-----------------------------|
```

#### Photo Specifications

| Attribute | Value |
|-----------|-------|
| Position | Top of detail panel, prominent |
| Size | Large (~150x150 px or responsive) |
| Placeholder | Generic pet silhouette |
| Caption | Pet name below photo |

### Client Detail View

```
|  DETAIL PANEL              |
|-----------------------------|
|  CLIENT                     |
|  ------                     |
|  John Doe                   |
|                             |
|  Phone:   91234567          |
|  Email:   john@email.com    |
|  Address: 123 Pet Street,   |
|           Singapore 123456  |
|  Tags:    [VIP] [Regular]   |
|                             |
|  PETS (3)                   |
|  --------                   |
|  1.1 Fluffy                 |
|      Dog, Golden Retriever  |
|      [Anxious]              |
|                             |
|  1.2 Whiskers               |
|      Cat, Persian           |
|                             |
|  1.3 Buddy                  |
|      Dog, Poodle            |
|      [Aggressive]           |
|-----------------------------|
```

---

## Command Reference

### Command Naming Convention

Commands follow a **verb-noun** pattern for clarity:

| Pattern | Examples |
|---------|----------|
| `add <entity>` | `add client`, `add pet` |
| `delete <entity>` | `delete client`, `delete pet` |
| `edit <entity>` | `edit client`, `edit pet` |
| `find <entity>` | `find client`, `find pet` |
| `view <entity>` | `view client`, `view pet` |
| `clear <scope>` | `clear all`, `clear pets`, `clear clients` |

### Shorthand Aliases

For power users, shorthand aliases are available:

| Full Command | Alias |
|--------------|-------|
| `add client` | `ac` |
| `add pet` | `ap` |
| `delete client` | `dc` |
| `delete pet` | `dp` |
| `edit client` | `ec` |
| `edit pet` | `ep` |
| `find client` | `fc` |
| `find pet` | `fp` |
| `view client` | `vc` |
| `view pet` | `vp` |
| `add note` | `an` |
| `edit note` | `en` |
| `delete note` | `dn` |
| `add photo` | `aph` |
| `delete photo` | `dph` |
| `filter` | `f` |
| `list` | `l` |
| `undo` | `u` |
| `redo` | `r` |
| `help` | `h` |

### Prefix Reference

| Prefix | Meaning | Used In |
|--------|---------|---------|
| `n/` | Name | client, pet |
| `p/` | Phone | client |
| `e/` | Email | client |
| `a/` | Address | client |
| `t/` | Tag | client, pet, filter |
| `s/` | Species | pet, filter |
| `b/` | Breed | pet |
| `dob/` | Date of birth | pet |
| `note/` | Grooming notes | note commands |
| `path/` | File path | photo commands |

### Client Commands

| Command | Alias | Syntax | Example |
|---------|-------|--------|---------|
| Add client | `ac` | `add client n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...` | `add client n/John Doe p/91234567 e/john@email.com a/123 Street t/VIP` |
| Delete client | `dc` | `delete client INDEX` | `delete client 2` |
| Edit client | `ec` | `edit client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...` | `edit client 1 p/99999999 t/VIP` |
| Find client | `fc` | `find client KEYWORD...` | `find client john doe` |
| View client | `vc` | `view client INDEX` | `view client 1` |

### Pet Commands

| Command | Alias | Syntax | Example |
|---------|-------|--------|---------|
| Add pet | `ap` | `add pet CLIENT_INDEX n/NAME [s/SPECIES] [b/BREED] [dob/DATE] [t/TAG]...` | `add pet 1 n/Fluffy s/Dog b/Golden Retriever dob/2020-03-15 t/Anxious` |
| Delete pet | `dp` | `delete pet INDEX.PETINDEX` | `delete pet 1.2` |
| Edit pet | `ep` | `edit pet INDEX.PETINDEX [n/NAME] [s/SPECIES] [b/BREED] [dob/DATE] [t/TAG]...` | `edit pet 1.1 b/Labrador` |
| Find pet | `fp` | `find pet KEYWORD...` | `find pet fluffy` |
| View pet | `vp` | `view pet INDEX.PETINDEX` | `view pet 1.1` |

### Grooming Notes Commands

| Command | Alias | Syntax | Example |
|---------|-------|--------|---------|
| Add note | `an` | `add note INDEX.PETINDEX note/TEXT` | `add note 1.1 note/Sensitive ears, use gentle shampoo` |
| Edit note | `en` | `edit note INDEX.PETINDEX note/TEXT` | `edit note 1.1 note/Updated grooming instructions` |
| Delete note | `dn` | `delete note INDEX.PETINDEX` | `delete note 1.1` |

### Photo Commands

| Command | Alias | Syntax | Example |
|---------|-------|--------|---------|
| Add photo | `aph` | `add photo INDEX.PETINDEX path/FILEPATH` | `add photo 1.1 path/C:/photos/fluffy.jpg` |
| Delete photo | `dph` | `delete photo INDEX.PETINDEX` | `delete photo 1.1` |

### Filter Commands

Filters are **additive** (they stack). Use `list` to clear all filters.

| Command | Alias | Syntax | Example |
|---------|-------|--------|---------|
| Filter | `f` | `filter [s/SPECIES] [t/TAG]...` | `filter s/Dog t/Anxious` |

**Stacking Example:**
```
> filter s/Dog
Showing 2 clients, 2 pets (filtered: species=Dog)

> filter t/Anxious
Showing 1 client, 1 pet (filtered: species=Dog, tags=Anxious)

> list
Showing 3 clients, 3 pets
```

### General Commands

| Command | Alias | Syntax | Description |
|---------|-------|--------|-------------|
| List | `l` | `list` | Show all clients and pets, clear filters |
| Clear all | - | `clear all` | Delete all data |
| Clear pets | - | `clear pets` | Delete all pets only |
| Clear clients | - | `clear clients` | Delete all clients (and their pets) |
| Undo | `u` | `undo` | Undo last modifying command |
| Redo | `r` | `redo` | Redo last undone command |
| Help | `h` | `help [COMMAND]` | Show help; `help add pet` for specific command |
| Exit | - | `exit` | Close application |

### Help Command Output

The `help` command displays a quick reference:

```
> help
===============================================================================
HAIRY PAWTER - COMMAND REFERENCE
===============================================================================

CLIENT COMMANDS                          PET COMMANDS
-----------------                        ------------
add client (ac)  - Add new client        add pet (ap)    - Add pet to client
delete client (dc) - Remove client       delete pet (dp) - Remove pet
edit client (ec) - Update client         edit pet (ep)   - Update pet
find client (fc) - Search clients        find pet (fp)   - Search pets
view client (vc) - View client details   view pet (vp)   - View pet details

NOTES & PHOTOS                           FILTERS
--------------                           -------
add note (an)    - Add grooming note     filter (f)      - Filter by species/tag
edit note (en)   - Update note           (filters stack; 'list' clears)
delete note (dn) - Remove note
add photo (aph)  - Attach photo          GENERAL
delete photo (dph) - Remove photo        -------
                                         list (l)  | undo (u) | redo (r)
                                         clear all | clear pets | clear clients
                                         help (h)  | exit

INDEX FORMAT: Pets use CLIENT.PET notation (e.g., 1.2 = Client 1, Pet 2)

Type 'help <command>' for detailed usage. Example: help add pet
===============================================================================
```

---

## Model Changes

### Extended Pet Class

The `Pet` class is extended with additional fields:

| Field | Type | Required | Validation | Example |
|-------|------|----------|------------|---------|
| `petName` | `Name` | Yes | Existing validation | "Fluffy" |
| `species` | `Species` | No | Non-blank string (free-form) | "Dog", "Cat", "Rabbit" |
| `breed` | `Breed` | No | Alphanumeric with spaces | "Golden Retriever" |
| `dateOfBirth` | `DateOfBirth` | No | Valid past date (YYYY-MM-DD) | 2020-03-15 |
| `tags` | `Set<Tag>` | No | Existing tag validation | [Anxious, SensitiveSkin] |
| `groomingNotes` | `GroomingNotes` | No | Free text (single field) | "Sensitive ears..." |
| `photoPath` | `PhotoPath` | No | Valid file path | "data/photos/fluffy.jpg" |

### New Value Classes

| Class | Field | Validation |
|-------|-------|------------|
| `Species` | `value: String` | Non-blank, any text |
| `Breed` | `value: String` | Alphanumeric with spaces |
| `DateOfBirth` | `value: LocalDate` | Valid date, not in future |
| `GroomingNotes` | `value: String` | Any text |
| `PhotoPath` | `value: String` | Valid file path, file exists |

### Constraints

- **All pets must have an owner**: Enforced at creation via `add pet CLIENT_INDEX ...`
- **Orphan pets are prevented**: Deleting a client deletes all their pets
- **Photo storage**: Local file path reference (not embedded in JSON)
- **Grooming notes**: Single text field (not timestamped history)

### JSON Storage Format

```json
{
  "persons": [
    {
      "name": "John Doe",
      "phone": "91234567",
      "email": "john@email.com",
      "address": "123 Pet Street",
      "tags": ["VIP", "Regular"],
      "pets": [
        {
          "name": "Fluffy",
          "species": "Dog",
          "breed": "Golden Retriever",
          "dateOfBirth": "2020-03-15",
          "tags": ["Anxious", "SensitiveSkin"],
          "groomingNotes": "Sensitive around ears. Use hypoallergenic shampoo.",
          "photoPath": "data/photos/fluffy.jpg"
        }
      ]
    }
  ]
}
```

---

## Alternative Layouts

### Option B: Pure Pets-First (Flat List)

An alternative layout where each row represents one pet, with owner info displayed alongside.

#### Visual Structure

```
| PET (left, prominent)                        | OWNER (right)           |
|----------------------------------------------|-------------------------|
| 1.1 +------+ Fluffy                          | Owner: John Doe         |
|     | [IMG]| Dog, Golden Retriever           | 91234567                |
|     +------+ [Anxious]                       | john@email.com          |
|----------------------------------------------|-------------------------|
| 1.2 +------+ Whiskers                        | Owner: John Doe         |
|     | [IMG]| Cat, Persian                    | 91234567                |
|     +------+                                 |                         |
|----------------------------------------------|-------------------------|
| 2.1 +------+ Buddy                           | Owner: Jane Smith       |
|     | [IMG]| Dog, Poodle                     | 88887777                |
|     +------+ [Aggressive]                    |                         |
```

#### Comparison with Option C

| Aspect | Option B (Flat) | Option C (Grouped) |
|--------|-----------------|-------------------|
| Pet visibility | Prominent, one per row | Prominent, cards in groups |
| Owner info | Repeated for each pet | Shown once per group |
| Client grouping | Not visually grouped | Clear group headers |
| Vertical space | More scrolling | More compact |
| "All pets of client" | Requires scanning | Visible in one group |

#### When to Consider Option B

- When the number of pets per owner is typically 1
- When pet identification is the primary workflow
- When owner information repetition is not a concern

---

## Implementation Phases

### Phase 1: Core Features (Must Have - Priority `***`)

1. **Extend Pet model**
   - Add `species`, `breed`, `dateOfBirth` fields
   - Update JSON storage/parsing

2. **Update `add pet` command**
   - Accept new fields: `s/`, `b/`, `dob/`

3. **Implement new UI layout**
   - 70/30 split with SplitPane
   - Grouped pets-first list panel
   - Basic detail panel

4. **Add `view` commands**
   - `view client INDEX`
   - `view pet INDEX.PETINDEX`

5. **Update help system**
   - Command-specific help: `help COMMAND`

### Phase 2: Enhanced Features (Nice to Have - Priority `**`)

1. **Pet editing**
   - `edit pet` command

2. **Search commands**
   - `find pet KEYWORD`
   - Enhanced `find client`

3. **Filter commands**
   - `filter s/SPECIES`
   - `filter t/TAG`
   - Additive filter behavior

4. **Grooming notes**
   - `add note`, `edit note`, `delete note` commands
   - Display in detail panel

5. **Photo support**
   - `add photo`, `delete photo` commands
   - Thumbnail in list, large in detail panel
   - Placeholder image

6. **Clear commands**
   - `clear pets`
   - `clear clients`

7. **Undo/Redo**
   - Implement as per existing DeveloperGuide proposal

### Phase 3: Polish

1. **Shorthand aliases**
   - All command aliases

2. **Pet tags**
   - Tag support for pets
   - Filter by pet tags

3. **UI refinements**
   - Card styling
   - Responsive layout

---

## Appendix: User Stories Coverage

| User Story | Priority | Addressed By |
|------------|----------|--------------|
| View user guide easily | `***` | `help` command, command reference |
| Add new pet with details | `***` | Extended `add pet` command |
| Add new client | `***` | `add client` command (existing) |
| View pet's information | `***` | Detail panel, `view pet` command |
| View client's details | `***` | Detail panel, `view client` command |
| Link client to pet | `***` | `add pet CLIENT_INDEX ...` |
| Remove pet | `***` | `delete pet` command (existing) |
| Remove client | `***` | `delete client` command (existing) |
| Edit client | `**` | `edit client` command (existing) |
| Edit pet | `**` | `edit pet` command (new) |
| Search clients by name | `**` | `find client` command (existing) |
| Search pets by name | `**` | `find pet` command (new) |
| Search client see pets | `**` | Grouped list + `view client` |
| Search pet see owner | `**` | Detail panel shows owner |
| Attach photos to pets | `**` | `add photo` command |
| View pet photos | `**` | Thumbnail in list, large in detail |
| Delete pet photos | `**` | `delete photo` command |
| Purge all pets | `**` | `clear pets` command |
| Purge all clients | `**` | `clear clients` command |
| Record grooming notes | `**` | `add note` command |
| View grooming notes | `**` | Detail panel |
| Update grooming notes | `**` | `edit note` command |
| Delete grooming notes | `**` | `delete note` command |
| Filter pets by species | `**` | `filter s/SPECIES` command |
| Attach tags to clients | `**` | Existing tag support |
| Attach tags to pets | `**` | Pet tags (new) |
| Filter clients by tags | `**` | `filter t/TAG` command |
| Filter pets by tags | `**` | `filter t/TAG` command |
| Data persistence | `***` | Existing JSON storage |
| Undo/Redo | `**` | `undo`/`redo` commands |

