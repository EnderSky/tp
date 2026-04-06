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
* Images used: [Footprint Icon](https://www.flaticon.com/free-icons/footprint") (created by Daniel ceha)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** shows the high-level design of the app.
It is a Model-View-Controller design, where
* The user interacts with the UI...
* Requested changes are passed through the Logic interface...
* These changes are reflected in the Model and Storage...
* And the UI listens for and displays changes accordingly.

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
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

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

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

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

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("deleteClient 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `deleteClient 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeletePersonCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeletePersonCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeletePersonCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddPersonCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPersonCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddPersonCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S2-CS2103T-F14-2/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

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

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

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

## **Documentation, logging, testing, configuration, dev-ops**

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

| Priority | As a …  ​ | I want to …​                | So that I can…​ |
|----------|--------------------------------------------|------------------------------|------------------------------------------------------------------------|
| `* * *`  | new user | view the user guide easily | learn more about how to use the product |
| `* * *`  | user     | add a new pet              | keep track of a new pet |
| `* * *`  | user     | record a pet's details (name, breed, date of birth) | identify each pet easily |
| `* * *`  | user     | add a new client and their contact information | keep track of a new client |
| `* * *`  | user     | view a pet's information   | view the information of a pet that I need to groom |
| `* * *`  | user     | view a client's details    | see the details of a client to contact |
| `* * *`  | user     | link a client to their pet | record who the pet's owner is |
| `* * *`  | user     | remove a pet               | delete outdated pet information |
| `* * *`  | user     | remove a client            | remove clients who no longer visit my shop |
| `* * *`  | user     | ensure my data persists automatically | preserve my records after closing the app |
| `* *`    | user     | edit a client's information | keep my client records up to date |
| `* *`    | user     | edit a pet's details       | update pet information when needed |
| `* *`    | user     | search for clients by name | quickly locate a specific client's details |
| `* *`    | user     | search for pets by name    | quickly find a specific pet's details |
| `* *`    | user     | search for a client and see all the pets they own | quickly retrieve their pets' information when they arrive |
| `* *`    | user     | search for a pet and see their owner's details | quickly retrieve the owner's contact information |
| `* *`    | user     | search for clients by contact details | quickly locate a specific client's details |
| `* *`    | user     | search for pets by attributes    | quickly find a specific pet's details |
| `* *`    | user     | attach photos to pets      | record what the pets look like in real life |
| `* *`    | user     | view a pet's photos        | identify pets quickly in real life | 
| `* *`    | user     | edit a pet's photos      | update pet photos with more recent versions |
| `* *`    | user     | delete a pet's photos      | remove outdated pet photos |
| `* *`    | new user | purge (delete all) existing pets | clear any dummy pet information once I have familiarised with the app |
| `* *`    | new user | purge (delete all) existing clients | clear any dummy client information once I have familiarised with the app |
| `* *`    | user     | record grooming notes for each pet | record each pet's special requirements |
| `* *`    | user     | view grooming notes for a pet | recall a pet's special requirements |
| `* *`    | user     | update grooming notes for a pet | update a pet's requirements as they change |
| `* *`    | user     | delete grooming notes for a pet | remove outdated information |
| `* *`    | experienced user | attach tags to clients | categorize different types of clients | 
| `*`    | experienced user | attach tags to pets    | flag out pets with special requirements |   |
| `*`    | user     | add appointment | schedule future appointments |
| `*`    | user     | view all appointments | see what upcoming appointments I have |
| `*`    | user     | edit appointment time and day | keep track of changes in future appoinements |
| `*`    | user     | delete appointment | keep track of cancellation of appointments |
| `*`    | user     | filter appointments by day | see what appointments I have for that day |
| `*`    | user     | mark the appointment as completed | keep track of what appointments have been completed |
| `*`    | user     | undo my last command | undo mistakes and typos |
| `*`    | user     | redo my last command | redo commands I accidentally undoed |

*{More to be added}*

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

* 1a. There are no existing clients.

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

     Use case ends.

* 2a. The given parameters are invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to delete a client.

      Steps 2a1-2a2 are repeated until the parameters are valid.

      Use case resumes at step 3.


**Use case 4: Delete a pet**

**MSS**

1.  User finds a pet to delete.
2.  User requests to delete a pet from a client.
3.  System deletes the pet and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no pets to delete.

     Use case ends.

* 2a. The given parameters are invalid.

    * 2a1. System shows an error message.
    * 2a2. User makes new request to delete a pet from a client.
      Steps 2a1-2a2 are repeated until the parameters are valid.

      Use case resumes at step 3.

**Use case 5: Edit details of a client**

**MSS**

1.  User finds the client to edit.
2.  User deletes the client (UC3).
3.  System deletes the client and displays the updated list.
4.  User adds the updated client and their pets (UC1).
5.  System adds the client and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no clients to edit.

     Use case ends.

**Use case 6: Edit details of a pet**

**MSS**

1.  User finds the pet to edit.
2.  User deletes the pet (UC4).
3.  System deletes the pet and displays the updated list.
4.  User adds the updated pet to the client (UC2).
5.  System adds the pet and displays the updated list.

    Use case ends.

**Extensions**

* 1a. There are no pets to edit.

     Use case ends.

**Use case 7: Search for pet of client**

**MSS**

1.  User looks for the client.
2.  User looks for their pet.

    Use case ends.

**Extensions**

* 2a. The pet does not exist.

     Use case ends.

**Use case 8: Search for client who owns pet**

**MSS**

1.  User looks for the pet.
2.  User looks for the client.

    Use case ends.

**Extensions**

* 1a. There are many pets with the same name.

    * 1a1. User decides which client is the one they want.

      Use case resumes at step 2.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The response to any use action should become visible within 5 seconds.
5. The app should not crash due to a user action (e.g., entering an invalid command, or deleting a person that does not exist).
6. The app should not crash due to a system error (e.g., hard disk failure, or running out of memory).
7. The app should not crash due to a programmer error (e.g., null pointer exception, or array index out of bounds exception).
8. Should not have a steep learning curve for users who are reasonably comfortable using CLI apps.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
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

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.
