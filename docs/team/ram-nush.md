---
  layout: default.md
  title: "Subramanian Ramasamy's Project Portfolio Page"
---

### Project: Hairy Pawter

Hairy Pawter is a desktop application for pet groomers to manage client records and pet details quickly using command-based interaction.

It has been adapted from [AddressBook - Level 3](https://se-education.org/addressbook-level3/), a desktop address book application used for teaching Software Engineering principles.

The user interacts with Hairy Pawter using a command-line interface (CLI), and it has a graphical user interface (GUI) created with JavaFX. It is written in Java, and has about 9 kLoC in functional code and 8 kLoC in test code.

Given below are my contributions to the project.

* **Revamped UI**: Displayed pet information beside client information.
    * What it does: allows the user to view pet information for each pet that a client has, beside the client.
    * Justification: This feature improves the product because a user can quickly identify the pets of a client or the owner of a pet through a side-by-side comparison.
    * Enhancements: The UI components have been reordered to have the command box and result display on the bottom, which moves the lists to the top. This design change is to portray our application as an assistant. The command box at the bottom simulates a friendly and welcoming environment, which encourages users to use our application similar to a messaging app.
    * Recolor UI: `xiuxikwey` and I finalized on a [colour scheme](https://coolors.co/palette/ccd5ae-e9edc9-fefae0-faedcd-d4a373) to use for Hairy Pawter. We found that people are familiar with light-coloured background and dark-coloured text. We chose a colour palette which complemented our target audience by bringing in elements of nature.
    * Credits: [The UX Reflex Theory](https://uxdesign.cc/the-ux-reflex-theory-lessons-from-apples-search-bar-experiment-8870a7068496)

* **Standardized command error messages**: Displayed same error message format when the user enters the command with an invalid format or out-of-range parameters.
   * What it does: When a user enters some parameters in an incorrect format, I ensured that the error messages displayed the specific error as well as the message usage, as much as possible.
   * Justification: We had 6 issues reported by testers during PE-D, regarding inconsistent and sometimes misleading error messages when running different commands.
   * Scope: I standardized the error messages between the `addClient`, `editClient`, `deleteClient`, `addPet`, `editPet` and `deletePet` commands.
   * Highlights: I also displayed the range of indices to choose from, when the user enters a `POSITION` larger than the number of clients / pets currently displayed in the list. Therefore, the user is aware of the valid range of indices they can input.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=ram-nush&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

* **Project management**:
    * Managed release `v1.5.1` (1 release) on GitHub

* **Enhancements to existing features**:
    * Revamped and recolored the GUI with a new color scheme (Pull requests [\#53](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/53), [\#143](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/143), [\#146](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/146))
    * Worked on bug fixes regarding UI indices not refreshing on command execution (Pull requests [\#55](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/55), [\#73](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/73), [\#145](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/145))

* **Documentation**:
    * User Guide:
        * Fixed broken links and updated style to match application: [\#143](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/143)
        * Added screenshots for `addPet`, `editPet`, `deleteClient` and `deletePet`: [\#246](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/246)
    * Developer Guide:
        * Updated the UML diagram displayed in the UI component section: [\#101](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/101)
    * Team-based tasks:
        * Assisted to deploy the project website: [1](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/426)

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#55](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/55), [\#73](https://github.com/AY2526S2-CS2103T-F14-2/tp/pull/73)
      * Contributed to forum discussions (examples: [1](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/35#issuecomment-3788045259), [2](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/40), [3](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/133#issuecomment-4020734832), [4](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/177#issuecomment-4020792787))
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/210#issuecomment-3908703071), [2](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/477#issuecomment-3980564166))

* 
