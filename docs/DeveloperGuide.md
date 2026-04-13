---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Hairy Pawter Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)
* Images used: [Dog - Bella](https://unsplash.com/photos/golden-retriever-puppy-on-focus-photo-9LkqymZFLrE) (Bill Stephan), [Cat - Milo](https://mypetandi.elanco.com/au/new-owners/so-you-re-thinking-about-getting-siamese-cat) (my pet & i), [Rabbit - Dove](https://www.jigidi.com/jigsaw-puzzle/86yg3txk/english-angora-rabbit/) (Jigidi), [Rabbit - Carrots](https://www.peakpx.com/en/hd-wallpaper-desktop-odqid) (Peakpx), [Shih Tzu - Fluffy](https://www.vecteezy.com/photo/73870857-adorable-shih-tzu-puppy-in-soft-pastel-background-portrait-photography-cozy-indoor-setting-charming-viewpoint) (Littlestar 0816), [Beagle - Snoopy](https://www.instagram.com/bayley.sheepadoodle/) (bayley.sheepadoodle), [Placeholder Image](https://www.stfrancisanimalwelfare.co.uk/home/placeholder-logo-1/) (St. Francis Animal Welfare)
* Icons used: [Footprint](https://www.flaticon.com/free-icons/footprint) (Daniel ceha), [Bunny](https://www.flaticon.com/free-icons/bunny) (Freepik), [Genes](https://www.flaticon.com/free-icons/genes) (Icon home), [Notepad](https://www.flaticon.com/free-icons/notepad) (Freepik), [Phone call](https://www.flaticon.com/free-icons/phone-call) (Ilham Fitrotul Hayat), [Home address](https://www.flaticon.com/free-icons/home-address) (KP Arts), [Email](https://www.flaticon.com/free-icons/email) (Freepik)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** shows the high-level design of the app.
It is a Model-View-Controller design, where
* The user interacts with the [**`UI`**](#ui-component), which accepts text commands and displays results.
* Requested changes are passed through the [**`Logic`**](#logic-component) interface, which parses and executes commands.
* These changes are reflected in the [**`Model`**](#model-component) (in-memory state) and [**`Storage`**](#storage-component) (persisted JSON file).
* The [**`UI`**](#ui-component) listens for changes to `Model` data and updates the display accordingly.

### Sequence of program execution

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

[**`Commons`**](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/commons) provides functionalities to other components.

User commands generally follow the execution sequence below.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface
and implements its API in the `LogicManager.java` class.
Components are called through their interfaces to abstract implementation details from other classes.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PetPersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

Additionally, `PetPersonCard` displays a client on the right and all the pets they own on the left. Hence, it contains 1 `PersonCard` and any number of `PetCard` objects.
This ensures that all pets of a client are shown together, which is easier for our users to handle.

The `UI` component uses the JavaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays both `Pet` and `Person` objects residing in the `Model`.

* For more information about executing user commands, refer to classes `MainWindow` and `CommandBox`.
* For more information about listening for changes, refer to class `PetPersonListPanel`.
* For more information about the `File` and `Help` buttons, refer to `MainWindow.fxml`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a **simplified** class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

<box type="info" seamless>
This diagram groups similar classes (e.g., all command parsers, all command subclasses) and omits repetitive details for clarity. Only key relationships and a few representative commands/parsers are shown. Other command and parser classes are omitted for simplicity.
</box>

The diagram shows representative commands in the system:
* **Client/Person Commands**: e.g., `AddPersonCommand`, `DeletePersonCommand`
* **Pet Commands**: e.g., `AddPetCommand`, `DeletePetCommand`
* **Utility Commands**: e.g., `FindCommand`, `ListCommand`

Each command has a corresponding parser class that handles parsing user input (e.g., `AddPersonCommandParser`, `AddPetCommandParser`). These are shown as grouped in the diagram for simplicity.

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("deleteClient 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `deleteClient 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeletePersonCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeletePersonCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeletePersonCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

**Supported Commands:**

* **Client Management**: `addclient` (ac), `deleteclient`, `editclient` (ec), `find`, `list`
* **Pet Management**: `addpet` (ap), `deletepet` (dp), `editpet` (ep)
* **System**: `help`, `exit`, `clear`

The pet-related commands (`addpet`, `deletepet`, `editpet`) are custom features added to link pets with their owners using the owner's phone number.

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates a matching parser (e.g., `AddPersonCommandParser`, `AddPetCommandParser`, `DeletePetCommandParser`, etc.) which uses the `ArgumentTokenizer` and `ParserUtil` to parse the user command and create the corresponding `Command` object.
* All parser classes inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` (client) objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPrefs` objects.
* stores all `Pet` objects embedded within `Person` objects. Each `Person` holds a list of `Pet` objects, each with a `Name`, `Species`, `Breed`, `Note`, and optionally a `PhotoPath`.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).

<puml src="diagrams/ModelSequenceDiagram.puml" alt="Interactions Inside the Model Component for the `deleteClient 1` Command" />

How the `Model` component works:

1. A command (e.g., `DeletePersonCommand`) calls the `Model` to delete a person.
2. The `ModelManager` calls the `AddressBook` to delete the person from the `UniquePersonList`.
3. The `UniquePersonList` deletes the person.

### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefsStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* serializes `Pet` objects via `JsonAdaptedPet`, nested within `JsonAdaptedPerson`, allowing the full client-pet hierarchy to be saved and restored from a single JSON file (`data/addressbook.json`).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Actual\] Person-pet relationship

Each `Person` (client) contains a `List<Pet>` representing their owned pets. This is a **one-to-many** relationship: one client can own multiple pets.

**Key characteristics of the Person-Pet model:**

* **Immutability**: `Person` objects are immutable. Operations like `addPet(Pet)` and `removePet(Pet)` return new `Person` instances rather than modifying the existing object. This design ensures thread safety and simplifies state management.

* **Client identity**: A client's uniqueness is determined by their **phone number**. Two clients with the same phone number are considered the same person, regardless of other fields. This is implemented in `Person#isSamePerson()`.

* **Pet identity**: A pet's uniqueness within an owner is determined by the **pet name**. Two pets belonging to the same owner cannot have the same name. This is validated when adding or editing pets.

* **Pet indexing**: Pets are indexed **globally** across all clients in the displayed list. For example, if Client 1 has pets A and B, and Client 2 has pet C, their indexes would be 1, 2, and 3 respectively. This sequential indexing is used by `editPet` and `deletePet` commands.

* **Pet-Client linking**: When adding a pet via `addPet`, the pet is linked to a client using the client's **phone number** (not the client's index). This ensures the link remains stable even when the client list is filtered or reordered.

The sequence diagram below illustrates the interactions within the `Logic` and `Model` components when executing `addPet n/Max p/87438807 s/Dog`:

<puml src="diagrams/AddPetSequenceDiagram.puml" alt="Interactions for the `addPet n/Max p/87438807 s/Dog` Command" />

Unlike `deleteClient` (which uses a list index), `addPet` uses a **phone number lookup**. The command first checks that a client with the given phone exists (`Model#hasPhone()`), then checks that the pet name is not a duplicate for that owner (`Model#hasPet()`), before finally calling `Model#addPet()`.

### \[Actual\] Find feature

The `find` command filters the displayed list of clients (and their pets) based on one or more keywords.

**How `FindPredicate` works:**

`FindPredicate` (in `model/person/FindPredicate.java`) implements `Predicate<Person>`. It requires **all** keywords to match (AND logic), but each keyword can match **any** field — a keyword is satisfied if it appears in:
* the client's name, phone number, email, or address
* any of the client's tags
* any of the client's pets' name, species, breed, or grooming notes

Matching is **case-insensitive** and **partial-word** (e.g., `find alex` matches a client named "Alexander").

**Filtering is applied at the `Person` level:** if a `Person` (client) satisfies all keywords, the entire entry — including all their pets — is shown. This means a search for `find alex dog` shows any client whose fields (or whose pets' fields) together contain both "alex" and "dog".

**Design consideration:**

| Alternative | Pros | Cons |
|---|---|---|
| Current: filter at `Person` level | Simple; only one `ObservableList<Person>` needed | Shows all of a client's pets even if only one pet matched |
| Filter at `(Person, Pet)` pair level | More precise — only matching pets shown | Requires structural changes to `Model` and `UI` |

The pair-level approach is listed as a planned enhancement (see Appendix: Planned Enhancements).

### \[Actual\] Pet photo feature

Pets can optionally have a photo attached, stored as a `PhotoPath` value.

**How `PhotoPath` validation works:**

When a `pic/` argument is provided to `addPet` or `editPet`, the `PhotoPath` class validates it:
1. The file extension must be one of: `.jpg`, `.jpeg`, `.jfif`, `.png`, `.gif`, `.bmp`.
2. It first attempts to load the path as a **classpath resource** (used for bundled sample images).
3. If that fails, it resolves the filename against the `data/photos/` directory and checks the file exists on disk.
4. Path traversal is blocked — any path that escapes `data/photos/` after normalisation is rejected.

**Storage:** Only the filename string is saved in JSON. The actual image file must be present in `data/photos/` at runtime.

**Fallback:** If a pet has no `PhotoPath`, or if the referenced file is missing at startup, the UI displays a placeholder paw icon instead.

**Design consideration:**

Embedding image data (e.g., Base64) in the JSON would eliminate the need for users to manually manage `data/photos/`, but would significantly increase file size and make the JSON unreadable. Storing only the path keeps the data file lightweight and human-editable.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `deleteClient 5` command to delete the 5th client. Internally, the command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `deleteClient 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `addClient n/David p/PHONE …​` to add a new person. The command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the client was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, DevOps**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* runs a pet grooming business
* manages many repeat clients
* requires fast access to pet and client information during work
* needs reliable record keeping
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable typing simple commands

**Value proposition**:

* fast retrieval of pet and client information
* clear pet -> client (owner) relationship tracking
* more efficient info management using keyboard-driven commands


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​ | So that I can…​ | Status |
|----------|---------|--------------|-----------------|--------|
| `* * *`  | new user | view the user guide easily | learn more about how to use the product | Implemented |
| `* * *`  | user     | add a new pet              | keep track of a new pet | Implemented |
| `* * *`  | user     | record a pet's details (name, breed) | identify each pet easily | Implemented |
| `* * *`  | user     | add a new client and their contact information | keep track of a new client | Implemented |
| `* * *`  | user     | view a pet's information   | view the information of a pet that I need to groom | Implemented |
| `* * *`  | user     | view a client's details    | see the details of a client to contact | Implemented |
| `* * *`  | user     | link a client to their pet | record who the pet's owner is | Implemented |
| `* * *`  | user     | remove a pet               | delete outdated pet information | Implemented |
| `* * *`  | user     | remove a client            | remove clients who no longer visit my shop | Implemented |
| `* * *`  | user     | ensure my data persists automatically | preserve my records after closing the app | Implemented |
| `* *`    | user     | edit a client's information | keep my client records up to date | Implemented |
| `* *`    | user     | edit a pet's details       | update pet information when needed | Implemented |
| `* *`    | user     | search for clients by name | quickly locate a specific client's details | Implemented |
| `* *`    | user     | search for pets by name    | quickly find a specific pet's details | Implemented |
| `* *`    | user     | search for a client and see all the pets they own | quickly retrieve their pets' information when they arrive | Implemented |
| `* *`    | user     | search for a pet and see their owner's details | quickly retrieve the owner's contact information | Implemented |
| `* *`    | user     | search for clients by contact details | quickly locate a specific client's details | Implemented |
| `* *`    | user     | search for pets by attributes | quickly find a specific pet's details | Implemented |
| `* *`    | user     | attach photos to pets      | record what the pets look like in real life | Implemented |
| `* *`    | user     | view a pet's photos        | identify pets quickly in real life | Implemented |
| `* *`    | user     | edit a pet's photos        | update pet photos with more recent versions | Implemented |
| `* *`    | user     | delete a pet's photos      | remove outdated pet photos | Implemented |
| `* *`    | new user | purge (delete all) existing pets | clear any dummy pet information once I have familiarised with the app | Implemented |
| `* *`    | new user | purge (delete all) existing clients | clear any dummy client information once I have familiarised with the app | Implemented |
| `* *`    | user     | record grooming notes for each pet | record each pet's special requirements | Implemented |
| `* *`    | user     | view grooming notes for a pet | recall a pet's special requirements | Implemented |
| `* *`    | user     | update grooming notes for a pet | update a pet's requirements as they change | Implemented |
| `* *`    | user     | delete grooming notes for a pet | remove outdated information | Implemented |
| `* *`    | experienced user | attach tags to clients | categorize different types of clients | Implemented |
| `*`      | experienced user | attach tags to pets | flag out pets with special requirements | Not implemented |
| `*`      | user     | add appointment | schedule future appointments | Not implemented |
| `*`      | user     | view all appointments | see what upcoming appointments I have | Not implemented |
| `*`      | user     | edit appointment time and day | keep track of changes in future appointments | Not implemented |
| `*`      | user     | delete appointment | keep track of cancellation of appointments | Not implemented |
| `*`      | user     | filter appointments by day | see what appointments I have for that day | Not implemented |
| `*`      | user     | mark the appointment as completed | keep track of what appointments have been completed | Not implemented |
| `*`      | user     | undo my last command | undo mistakes and typos | Not implemented |
| `*`      | user     | redo my last command | redo commands I accidentally undid | Not implemented |

### Use cases

(For all use cases below, the **System** is `Hairy Pawter` and the **Actor** is the `user`, unless specified otherwise.
A **Precondition** is that the system is displaying the list of clients and pets)


**Use case 1: Add a client and their pet**

**MSS**

1.  User requests to add a client.
2.  System adds the client and displays the updated list.
3.  User requests to add a pet to the client.
4.  System adds the pet and displays the updated list.

    Use case ends.

**Extensions**

* 1a. The client already exists.

   * 1a1.  System notifies user.

     Use case resumes at step 3.

* 1b. The given parameters are invalid.

    * 1b1. System shows an error message.
    * 1b2. User makes new request to add a client.
      Steps 1b1-1b2 are repeated until the parameters are valid.

      Use case resumes at step 2.

* 3a. The given parameters are invalid.

    * 3a1. System shows an error message.
    * 3a2. User makes new request to add a pet to a client.
      Steps 3a1-3a2 are repeated until the parameters are valid.

      Use case resumes at step 4.

**Use case 2: Add a pet to an existing client**

**MSS**

1.  User finds an existing client.
2.  User requests to add a pet to the client.
3.  System adds the pet and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no existing clients to add pets to.

    * 1a1. System notifies user.
     Use case ends.

* 2a. The given parameters are invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to add a pet.

      Steps 2a1-2a2 are repeated until the parameters are valid.

      Use case resumes at step 3.

**Use case 3: Delete a client**

**MSS**

1.  User finds a client to delete.
2.  User requests to delete a client.
3.  System deletes the client and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no clients to delete.

    * 1a1. System notifies user.
     Use case ends.

* 2a. The given index is invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to delete a client.

      Steps 2a1-2a2 are repeated until the index is valid.

      Use case resumes at step 3.


**Use case 4: Delete a pet**

**MSS**

1.  User finds a pet to delete.
2.  User requests to delete a pet from a client.
3.  System deletes the pet and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no pets to delete.

    * 1a1. System notifies user.
     Use case ends.

* 2a. The given index is invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to delete a pet from a client.
      Steps 2a1-2a2 are repeated until the index is valid.

      Use case resumes at step 3.

**Use case 5: Edit details of a client**

**MSS**

1.  User finds a client to edit.
2.  User requests to edit the client.
3.  System edits the client and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no clients to edit.

    * 1a1. System notifies user.
     Use case ends.

* 2a. The given index is invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to edit a client.
      Steps 2a1-2a2 are repeated until the index is valid.

      Use case resumes at step 3.

**Use case 6: Edit details of a pet**

**MSS**

1.  User finds a pet to edit.
2.  User requests to edit the pet.
3.  System edits the pet and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no pets to edit.

    * 1a1. System notifies user.
     Use case ends.

* 2a. The given index is invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to edit a pet.
      Steps 2a1-2a2 are repeated until the index is valid.

      Use case resumes at step 3.

**Use case 7: Search for clients and pets by keywords**

**MSS**

1.  User provides one or more keywords to the `find` command.
2.  System displays all clients where **all** keywords match at least one of: the client's name, phone, email, address, tags, or any of their pets' name, species, breed, or grooming notes.
3.  User locates the desired client or pet in the filtered list.

    Use case ends.

**Extensions**

* 2a. No client matches all the keywords.

    * 2a1. System displays an empty list with a "0 clients listed" message.

      Use case ends.

* 2b. User wants a broader search.

    * 2b1. User reruns `find` with fewer keywords.

      Use case resumes at step 2.

* 2c. User wants to restore the full list after searching.

    * 2c1. User runs `list`.

      Use case ends.

**Use case 8: Delete all records**

**MSS**

1.  User requests to clear all stored information.
2.  System deletes all records and displays the empty list.

    Use case ends.

**Extensions**

* 1a. The records are already empty.

     Use case resumes at step 2.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has `Java 17` or above installed.
2.  Should be able to hold up to 200 clients with 3 pets each without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The response to any user action should become visible within 2 seconds.
5. The app should not crash due to a user action (e.g., entering an invalid command, or deleting a client that does not exist).
6. The app should not crash due to a programmer error (e.g., null pointer exception, or array index out of bounds exception).
7. Should not have a steep learning curve for users who are reasonably comfortable using CLI apps.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Above average typing speed**: 40 words per minute (wpm) or above, where a word is defined as 5 characters including spaces.
* **Noticeable sluggishness**: A noticeable delay in the response of the app to user actions, such as a delay in showing the result of a command, or a delay in updating the UI after a command is executed.
* **Reasonably comfortable using CLI apps**: A user who is familiar with using command-line interfaces (CLI) and can use them to perform basic tasks such as navigating directories, creating files, and running commands without needing extensive guidance or support.
* **Crashing**: The app becoming unresponsive, freezing, or terminating unexpectedly due to an error or exception that is not handled properly by the app.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a client

1. Test case: `addClient n/name p/12345678 e/a@gmail.com a/#11-11 11 Eleven Road, 111111 t/11/11/11`<br>
      Expected: Client is added to the list. Details of the client shown in the status message.

    * Other correct commands to try: case-insensitive `addclient`, alias `ac`, reordered fields, missing fields<br>
      Expected: Similar to previous.

2. Test case: `addClient  n/name e/1@gmail.com a/#11-11 11 Eleven Road, 111111 t/11/11/11`<br>
      Expected: No client is added. Error details shown in the status message.

   * Other incorrect commands to try: `addClient`, repeat parameters<br>
         Expected: Similar to previous.

### Editing a client

1. Test case: `editClient 1 n/new name p/22 e/2@gmail.com a/"22 Next Door" t/`<br>
      Expected: Client with index 1 is changed. Details of the client shown in the status message.

   * Other correct commands to try: case-insensitive `editclient`, alias `ec`, reordered fields, missing fields<br>
         Expected: Similar to previous.

2. Test case: `editClient 1 p/123`, where client at index 1 already has phone 123<br>
      Expected: Success message shown in the status message, but no change in the list. Details of the client shown in the status message.

3. Test case: `editClient 2 p/123`, where there already is a client with phone number 123 that is not in index 1<br>
   Expected: No change in the list. Error details shown in the status message.

    * Other incorrect commands to try: `editClient`, repeat parameters<br>
      Expected: Similar to previous.
   
### Deleting a client
Prerequisites: List all clients using the `list` command. Multiple clients in the list.

   1. Test case: `deleteClient 1`<br>
      Expected: Client with index 1 is deleted from the list. Details of the deleted client shown in the status message.

      * Other correct commands to try: case-insensitive `deleteclient`, alias `dc`, filtered list using `find`<br>
            Expected: Similar to previous.

   1. Test case: `deleteClient 0`<br>
      Expected: No client is deleted. Error details shown in the status message.

      * Other incorrect commands to try: `deleteClient`, `deleteClient x`, `...` (where x is larger than the list size)<br>
         Expected: Similar to previous.

### Adding a pet

   1. Test case: `addPet n/name p/87438807 s/dog b/beagle nt/paws sensitive` where the client with the phone number already exists<br>
      Expected: Pet is added beside the client. Details of the pet shown in the status message.

      * Other correct commands to try: case-insensitive `addpet`, alias `ap`, reordered fields, missing fields, adding photo path<br>
                  Expected: Similar to previous.

   1. Test case: `addPet n/name`<br>
      Expected: No pet is added. Error details shown in the status message.

      1. Other incorrect commands to try: `addPet`, repeat parameters<br>
         Expected: Similar to previous.

### Editing a pet
Prerequisites: List all pets using the `list` command. Multiple pets in the list.

   1. Test case: `editPet 1 n/nyeow s/Cat b/Tabby nt/skin allergies`<br>
      Expected: Pet with index 1 is edited. Details of the pet shown in the status message.

      * Other correct commands to try: case-insensitive `editpet`, alias `ep`, filtered list using `find`<br>
                  Expected: Similar to previous.

   2. Test case: `editPet 0 n/that`<br>
            Expected: No edit happens. Error details shown in the status message.

      * Other incorrect commands to try: `editPet`, `editPet x`, (where x is larger than the list size), editing to a pet with the same name and owner as another<br>
                  Expected: Similar to previous.

### Deleting a pet

Prerequisites: List all pets using the `list` command. Multiple pets in the list.

1. Test case: `deletePet 1`<br>
      Expected: Pet with index 1 is deleted from the list. Details of the deleted pet shown in the status message.

   * Other correct commands to try: case-insensitive `deletepet`, alias `dp`, filtered list using `find`<br>
      Expected: Similar to previous.

2. Test case: `deletePet 0`<br>
      Expected: No pet is deleted. Error details shown in the status message.

   * Other incorrect commands to try: `deletePet`, `deletePet x`, `...` (where x is larger than the list size)<br>
            Expected: Similar to previous.

### Finding clients and pets

Finding clients and pets that (partially) match **all** keywords.

   1. Test case: `find alex`, given client `Alex Yeoh` exists and nobody else matches `alex`<br>
      Expected: Only client `Alex Yeoh` is displayed.

   1. Test case: `find alex dog`, given `Alex Yeoh` owns a pet with species `Dog`<br>
      Expected: `Alex Yeoh` and all their pets are displayed, because both keywords are satisfied across the client and pet fields.

   1. Test case: `find xyzzy`<br>
      Expected: No clients displayed. Status message shows `0 clients listed`.

      * Other correct commands to try: Partial matches across all keywords, matching across owner and pet<br>
            Expected: If a client or pet matches all keywords, the client and all their pets are displayed.

### Listing all clients and pets
Restoring the full list after a `find` command.

Prerequisites: Run `find alex` so the list is filtered.

   1. Test case: `list`<br>
      Expected: All clients and their pets are shown. Status message confirms the full list is displayed.

### Adding grooming notes

Adding and clearing a grooming note on a pet.

Prerequisites: At least one client exists. Use `list` to confirm.

   1. Test case: `addPet n/Max p/87438807 s/Dog b/Beagle nt/Sensitive to loud noises` where the client with phone `87438807` exists<br>
      Expected: Pet is added with the grooming note visible in the UI.

   1. Test case: `editPet 1 nt/`<br>
      Expected: Grooming note is cleared for the pet at index 1. Pet entry updates immediately.

   1. Test case: `find Sensitive` after adding a pet with `nt/Sensitive to loud noises`<br>
      Expected: The client owning that pet is shown, because grooming notes are included in search.

### Adding and editing a pet photo
Prerequisites: Place an image file (e.g., `max.png`) inside the `data/photos/` directory. Use `list` to confirm at least one client exists.

   1. Test case: `addPet n/Max p/87438807 s/Dog b/Beagle pic/max.png`<br>
      Expected: Pet is added with the photo displayed in the UI.

   1. Test case: `editPet 1 pic/max.png`<br>
      Expected: Photo is updated for the pet at index 1.

   1. Test case: `addPet n/Ghost p/87438807 s/Cat pic/nonexistent.png`<br>
      Expected: No pet added. Error message shown indicating the photo file was not found.

   1. Test case: `addPet n/Ghost p/87438807 s/Cat pic/../../secret.png`<br>
      Expected: No pet added. Error message shown (path traversal outside `data/photos/` is blocked).

### Using tags
Adding and clearing tags on a client.

   1. Test case: `addClient n/Alice p/91234567 e/alice@example.com a/123 Street t/VIP`<br>
      Expected: Client added with a `VIP` tag shown.

   1. Test case: `editClient 1 t/Regular t/Discount`<br>
      Expected: Tags on client at index 1 are replaced with `Regular` and `Discount`.

   1. Test case: `editClient 1 t/`<br>
      Expected: All tags removed from client at index 1.

   1. Test case: `find VIP` after adding a client tagged `VIP`<br>
      Expected: That client is shown, because tags are included in search.

### Clearing all records

Prerequisites: At least one client exists.

   1. Test case: `clear`<br>
      Expected: All clients and pets are deleted. An empty list is shown.

   1. Test case: `clear` when the list is already empty<br>
      Expected: The list remains empty. No error shown.

### Help and exit

   1. Test case: `help`<br>
      Expected: A help window opens showing command usage information.

   1. Test case: `exit`<br>
      Expected: The application closes cleanly.

## **Appendix: Effort**

**Difficulty level:** Moderate to high, relative to AB3.

AB3 manages a single entity (`Person`). Hairy Pawter manages two entities (`Person` and `Pet`) in a **one-to-many** relationship, requiring changes across every component of the application.

**Challenges encountered and effort required:**

* **Global pet indexing.** AB3 uses a simple list index for persons. Hairy Pawter needs a global sequential index across all pets (e.g., if Client 1 has pets A and B, and Client 2 has pet C, their indexes are 1, 2, 3). Since only `ObservableList<Person>` is exposed, computing these indexes required iterating over all persons and their embedded pet lists, which was not trivial to integrate cleanly with commands and the UI.

* **Immutable `Person` with embedded pets.** `Person` objects are immutable. Any pet modification — add, edit, or delete — requires creating a new `Person` with the updated pet list and replacing it in the `UniquePersonList`. This pattern had to be implemented consistently across `AddPetCommand`, `EditPetCommand`, and `DeletePetCommand`.

* **Adapting existing tests.** Every existing AB3 test assumed `Person` had no pets. Updating them — and writing new tests for pet-related commands — accounted for a large share of total effort, more than writing the production code itself.

* **Phone-based pet linking.** The decision to link pets to clients by phone number (not index) was necessary for stability under filtering, but it required `AddPetCommand` to perform a lookup that is structurally different from all other commands, and this difference had to be clearly communicated to contributors.

* **Photo feature.** Implementing `PhotoPath` validation — covering file extensions, path traversal prevention, classpath resources, and graceful fallback to a placeholder icon — was more involved than a typical optional field.

* **Storage for nested entities.** Adding `JsonAdaptedPet` nested within `JsonAdaptedPerson` while maintaining JSON compatibility required careful handling of optional fields (species, breed, notes, photo path).

**Appendix: Effort:**

* **UI restructuring.** The UI restructuring (showing `PetPersonCard` with a `PersonCard` and one or more `PetCard` objects side by side) was an additional non-trivial effort.

**Achievements:**

* Fully functional two-entity CRUD system with client-pet linking.
* Unified `find` command searching across all client and pet fields.
* Pet photo support with runtime validation and graceful fallback.
* Grooming notes integrated into search.
* Custom UI layout tailored to a pet grooming workflow.

## **Appendix: Planned enhancements**

Team size: 5

1. **Make find function more specific:** The current `find` command returns a client and **all** their pets if any field matches, even if only one pet was the actual match. The fix is to introduce a pair-predicate in the `Model` that filters at the `(Person, Pet)` level. `getFilteredPersonList()` would expose only the clients that matched, and the UI would render only the matched pets for each such client. This requires changes to `ModelManager`, the filtered list logic, and `PetPersonListPanel`.

2. **Allow `pic/` to clear a photo:** Currently there is no way to remove a photo once it has been set on a pet — `editPet` requires `pic/` to point to a valid file. The fix is to treat `pic/` (with no argument) as a sentinel that clears the photo, consistent with how `t/` clears tags in `editClient`. This requires a change in `EditPetCommandParser` and `EditPetCommand` to distinguish between an absent `pic/` prefix and a present-but-empty one.

3. **Improve duplicate-pet error message:** When `addPet` or `editPet` is rejected because a pet with the same name already exists for that owner, the error message says only "This pet already exists." The fix is to include the duplicate's name and owner in the message, e.g. `A pet named 'Max' already exists for client 87438807.`, so users can quickly identify the conflict.

4. **Enforce minimum phone number length:** Currently any non-empty string of digits is accepted as a phone number by `Phone`, including single-digit values. The fix is to enforce a minimum of 3 digits in `Phone#isValidPhone()`. Sample input that should be rejected: `p/1`, `p/12`.

5. **Show pet count in status bar:** The current status bar shows only the data file path. Adding a live count such as `5 clients · 12 pets` would give users at-a-glance information about the size of their database without needing to scroll. This requires a listener on the `ObservableList<Person>` in `StatusBarFooter` and a utility to sum pet counts across all persons.

6. **Improve phone-conflict error message in `editClient`:** When a client's phone is changed to one already used by another client, the error says "This person already exists in the address book." The fix is to make the message more specific: `Phone number 87438807 is already in use by another client.`

7. **Prevent accidental `clear` with a confirmation step:** The `clear` command permanently deletes all clients and pets with no warning. The fix is to require users to confirm by typing `clear --confirm`, or to display a confirmation prompt in the result display that must be acknowledged before the deletion proceeds.

8. **Support prefix-based `find` for specific fields:** Currently `find dog` matches "dog" anywhere — names, notes, species, etc. — which can return unexpected results. The fix is to allow optional prefixes such as `find s/Dog b/Beagle` to restrict the search to specific fields, while keeping bare keyword search as a fallback for the general case.

9. **Make address and email optional in `addClient`:** Both `a/` and `e/` are currently required parameters, which forces users to enter placeholder values when a client's email or address is unknown. The fix is to make these fields optional, defaulting to empty strings when omitted, matching how `Species`, `Breed`, `Note`, and `PhotoPath` are optional in `addPet`.

10. **Improve `find` result message to include pet count:** The current result message after `find` says `N clients listed`, but gives no information about how many pets were matched. The fix is to update the message to `N clients listed (M pets)` by counting pets in the filtered list, providing more feedback without requiring the user to count manually.
