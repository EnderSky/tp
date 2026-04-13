---
  layout: default.md
  title: "User Guide"
  pageNav: 4
---

# Hairy Pawter User Guide

Hairy Pawter is a desktop app for **pet groomers** to manage client and pet records.

Use it to:
- Record the contact details and pets of clients when they walk in
- Track each pet's species, breed, notes, and photos for easy identification
- Quickly find a client's contact details after finishing a grooming session
- Manage both walk-in and appointment clients in one place

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Who is this guide for?

This guide is written for **pet groomers** who manage their own client base.

**No programming experience is needed.** However, you should be comfortable typing short commands into a text field, as Hairy Pawter is a keyboard-first app.

If you are setting up Hairy Pawter for the first time, start with the [Quick Start](#quick-start) section below.

--------------------------------------------------------------------------------------------------------------------

## Quick Start

### Installation

1. [Install](https://se-education.org/guides/tutorials/javaInstallation.html) `Java 17` or higher on your computer.

   * `Java 17` is the software that Hairy Pawter needs to run.
<br><br>

1. Download `hairypawter.jar` from the latest release [here](https://github.com/AY2526S2-CS2103T-F14-2/tp/releases).

   * It will appear in your Downloads folder.
<br><br>

1. Move `hairypawter.jar` to the folder you want to use as the _home folder_ for Hairy Pawter.

   * You can create a new folder called `HairyPawter` and move the file inside it.
<br><br>

1. Double-click `hairypawter.jar` to run Hairy Pawter.

<box type="info" seamless>

**If double-clicking does not open the app:**

Open a command terminal:
* **Windows:** Search for `Command Prompt` in the taskbar search.
* **Mac:** Press `Cmd` + `Space` and search for `Terminal`.
* **Linux:** Try `Ctrl` + `Alt` + `T`.

In the terminal, navigate to your home folder using `cd PATH_TO_HOME_FOLDER`.<br>
e.g. `cd C:\Users\jeff\Desktop\HairyPawter\`

Then run: `java -jar hairypawter.jar`

You can ignore any other output in the terminal while the app is running. Closing the terminal will also close the app.

</box>

### Overview of the interface

<img src="images/Ui.png" class="app-screenshot" alt="Hairy Pawter interface">

When Hairy Pawter opens, you will see four areas:

| Area | Location | Purpose |
|------|----------|---------|
| **Client list** | Left panel | Displays all clients and their contact details |
| **Pet list** | Right panel | Displays the pets belonging to each client |
| **Result box** | Above the command box | Shows the outcome of your last command |
| **Command box** | Bottom of the window | Type your commands here and press Enter |

### Try it yourself: a first session

Follow these steps to get started. Type each command into the command box and press Enter.

1. `addClient n/John Tan p/91234567 e/john@email.com`<br>
   Adds a client named John Tan with phone number 91234567.

2. `addPet n/Biscuit p/91234567 s/Dog b/Golden Retriever nt/Loves belly rubs`<br>
   Adds a dog named Biscuit under John Tan's account (matched by phone number).

3. `find Biscuit`<br>
   Filters the list to show only records matching "Biscuit".

4. `list`<br>
   Returns to the full list of all clients and pets.

5. `deleteClient 1`<br>
   Deletes the client at position 1 and all their pets.

--------------------------------------------------------------------------------------------------------------------

## Commands

Type a command into the command box and press Enter to run it. For example, typing `help` and pressing Enter opens the help window.

<box type="info" seamless>

**Reading the command format:**<br>

* Words in `UPPER_CASE` are placeholders — replace them with real values.<br>
  e.g. `addClient n/NAME` should be typed as `addClient n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `[t/TAG]` can be left out entirely.

* Items with `…` can be repeated multiple times.<br>
  e.g. `[t/TAG]…` can be typed as `t/friend t/family`.

* Parameters can be in any order.<br>
  e.g. if the format shows `n/NAME p/PHONE`, typing `p/PHONE n/NAME` also works.

* **`POSITION`** refers to the number displayed next to a client or pet in the list. It changes whenever you use `find` or `list`, so always check the current number before editing or deleting.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters around line-breaks may be dropped.
</box>

<box type="tip" seamless>

**Tip:** Most commands have a short-form alias for faster typing. See the [Short-form commands](#short-form-commands) section.

</box>

### Viewing help : `help`

You would use the `help` command when you don't remember the command to perform a specific action.

This command shows a message explaining how to access the user guide (this document), to view the list of commands and how to use them.

<img src="images/helpMessage.png" class="app-screenshot" alt="help message">

Format: `help`

<br><br>

### Adding a client: `addClient`

Registers a new client. The new client appears at the top of the list.

<box type="warning" seamless>

**Constraint:** Each client must have a unique phone number. You cannot add two clients with the same phone number.

</box>

Format: `addClient p/PHONE [n/NAME] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

Examples:
* `addClient n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `addClient n/Betsy Crowe t/friend e/betsycrowe@example.com a/Crown street p/1234567`

**Expected output:**
```
New client added: John Doe; Phone: 98765432; Email: johnd@example.com; Address: John street, block 123, #01-01; Tags:
```

<box type="info" seamless>

**Note:** If you have used `find` and the new client does not match your search terms, they will not appear in the current view. Type `list` to see all clients.

Omitted fields will appear as `-`.

</box>

<box type="tip" seamless>

**Tip — using `[t/TAG]…` effectively:**
* Add multiple tags to record extra information, such as a client's grooming preferences or the date of their last visit (e.g. `t/2025-03-15`).
* Tags are searchable with the `find` command, making it easy to filter by visit date or any other label.

</box>

<br><br>

### Adding a pet: `addPet`

Registers a new pet under an existing client. The client is identified by their phone number.

<box type="warning" seamless>

**Constraints:**
* The client must already be registered in Hairy Pawter before you can add their pet. Use [`addClient`](#adding-a-client-addclient) first.
* A client cannot have two pets with the same name.

</box>

Format: `addPet n/NAME p/PHONE [s/SPECIES] [b/BREED] [nt/NOTES] [pic/PICTURE]`

Examples:
* `addPet n/Snowy p/0000 s/Dog b/Wire Fox Terrier (White)`
* `addPet n/Meowy p/123456`
* `addPet n/Doggy p/81234567 pic/doggy.png`

**Expected output:**
```
New pet added: Snowy; Species: Dog; Breed: Wire Fox Terrier (White); Notes: None; Picture: No picture provided
```

<box type="info" seamless>

Omitted fields will appear as `Unknown` or `None`.

</box>

<box type="info" seamless>

#### **More about `[pic/PICTURE]`:**<br>

* Locate the auto-generated photos subdirectory `[hairypawter.jar file location]/data/photos/` (if it has not been generated yet, run any command that adds or deletes an entry first)
* Copy a photo you wish to add into that subdirectory, and take note of its filename
* Include this filename after the `pic/` tag when adding a photo using the `addPet` or `editPet` command (eg. `pic/doggy.png`)
* You may choose to organise the images within your `data/photos/` subdirectory into subfolders. In that case, take note that the filepath you are required to provide will be `[subdirectory name]/[image filename]`. Be careful not to end the subdirectory name with any other tag name (eg. the filepath `subfolder nt/pet.png` is not allowed)
* A picture will show as the placeholder pawprint icon in 2 cases:
  1. No picture has been added to the pet.
  1. A valid picture was initially added, but was later unable to be located (either due to the picture being deleted or due to the filepath being manually edited to an invalid filepath in the JSON file after the initial adding)
  * If this happens, try to re-add the photo with the `editPet` command with the corrected filename and filepath

#### **Using `[nt/NOTES]` to your advantage:**<br>

* `[nt/NOTES]` exists to record down important information about the pet. This can be identifying information
like leash colour, or allergies and quirks of the pet. Use this flexibly!

</box>

<br><br>

### Listing all clients and pets : `list`

Shows all clients and their pets.

Format: `list`

**Expected output:**
```
Listed all clients
```

<br><br>

### Editing a client : `editClient`

Edits the details of an existing client.

* Edits the client at the specified `POSITION`.
* Only the fields you provide will be updated; all other fields remain unchanged.
* Editing tags replaces all existing tags. To remove all tags, use `t/` with no value.

Format: `editClient POSITION [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

At least one field (`n/`, `p/`, `e/`, `a/`, or `t/`) must be provided.

Examples:
* `editClient 1 p/91234567 e/johndoe@example.com` — updates the phone and email of the client at position 1.
* `editClient 2 n/Betsy Crower t/` — renames the client at position 2 and removes all their tags.

**Expected output:**
```
Edited Client: Betsy Crower; Phone: 1234567; Email: betsycrowe@example.com; Address: Crown street; Tags:
```

<box type="info" seamless>

**Note:** Changing a client’s phone number does not affect their pets — the pets remain associated with that client under the new phone number.

</box>

<br><br>

### Editing a pet : `editPet`

Edits the details of an existing pet.

* Edits the pet at the specified `POSITION`.
* Only the fields you provide will be updated; all other fields remain unchanged.
* To remove a pet's photo, use `pic/` with no value.
* A pet's owner cannot be changed with `editPet`. To reassign a pet to a different owner, delete the pet with [`deletePet`](#deleting-a-pet-deletepet) and re-add it with [`addPet`](#adding-a-pet-addpet).

Format: `editPet POSITION [n/NAME] [s/SPECIES] [b/BREED]​ [nt/NOTES] [pic/PICTURE]`

At least one field must be provided.

Examples:
* `editPet 1 s/Cat` — updates the species of the pet at position 1.
* `editPet 2 n/Gunner nt/Nervous around strangers` — renames the pet at position 2 and updates their notes.

**Expected output:**
```
Edited Pet: Gunner; Species: Unknown; Breed: Unknown; Notes: Nervous around strangers; Picture: No picture provided
```

<box type="info" seamless>

Click [here](#more-about-picpicture) for more details on using the `pic/` tag.

Click [here](#using-ntnotes-to-your-advantage) to see how to use the `nt/` tag.

</box>

<br><br>

### Locating clients and pets by keywords: `find`

Filters the list to show only clients and pets that match **all** of the given keywords.

* The search covers all fields: client name, phone, email, address, tags, and pet name, species, breed, and notes.
* Matching is partial and case-insensitive (e.g. `Roy` matches `Leroy`).
* All keywords must match — e.g. `Hans Bo` only shows results where both `Hans` and `Bo` appear somewhere in the record.
* Keyword order does not matter.
* This filter stays active until you run `list`.

Format: `find KEYWORD…`

Examples:
* `find Yu` — shows clients named `Yusuf` or `Yuri` and their pets.
* `find Yu cat` — shows records where both `yu` and `cat` appear anywhere across the client's or their pets' details.

**Expected output:**
```
2 clients listed!
```

<box type="info" seamless>

**Note:** The result count shows the number of **clients** matched, not pets.

</box>

<img src="images/findYuCatResult.png" class="app-screenshot" alt="result for find Yu Cat">

<br><br>

### Deleting a client : `deleteClient`

Deletes the specified client.

<box type="warning" seamless>

**Warning:** Deleting a client also permanently deletes all of their pets. This cannot be undone.

</box>

Format: `deleteClient POSITION`

Examples:
* `list` followed by `deleteClient 2` — deletes the client at position 2 in the full list.
* `find Betsy` followed by `deleteClient 1` — deletes the first client in the filtered results.

**Expected output:**
```
Deleted Client: Betsy Crowe; Phone: 1234567; Email: betsycrowe@example.com; Address: Crown street; Tags: friend
```

<br><br>

### Deleting a pet : `deletePet`

Deletes the specified pet.

Format: `deletePet POSITION`

<box type="info" seamless>

**Note:** Pet positions are numbered sequentially across all clients in the list, not per client. For example, if client 1 has 2 pets and client 2 has 1 pet, the pets are at positions 1, 2, and 3 respectively. Always check the current position number before deleting.

Unlike `addPet` (which identifies the owner by phone number), `deletePet` uses the pet's position number shown in the list.

</box>

Examples:
* `list` followed by `deletePet 2` — deletes the pet at position 2 in the full list.
* `find Biscuit` followed by `deletePet 1` — deletes the first pet in the filtered results.

**Expected output:**
```
Deleted Pet: Biscuit; Species: Dog; Breed: Golden Retriever; Notes: Loves belly rubs; Picture: No picture provided
```

<br><br>

### Clearing all records : `clear`

Deletes all clients and pets from Hairy Pawter.

<box type="warning" seamless>

**Warning:** This permanently deletes every client and pet record. This cannot be undone. Consider [backing up your data file](#editing-the-data-file) before using this command.

</box>

Format: `clear`

**Expected output:**
```
Hairy Pawter has been cleared!
```

<br><br>

### Exiting the app : `exit`

Exits the app.

Format: `exit`

<br><br>
--------------------------------------------------------------------------------------------------------------------

## Storing data

Data is saved automatically after every command. There is no need to save manually.

<br><br>

### Editing the data file

Data is stored as a JSON file at `[hairypawter.jar location]/data/addressbook.json`. You can edit this file directly if needed, but this is not recommended.

<box type="warning" seamless>

**Caution:** If your edits make the file format invalid, the entire file will be discarded the next time the app opens. Back up the file before editing it.

Certain edits may also cause the app to behave unexpectedly (e.g. if a value is outside the accepted range). Only edit the data file if you are confident you can update it correctly.

</box>

<br><br>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: Is it safe to click the close button instead of typing `exit`?<br>
**A**: Yes. Your data is saved automatically, so closing the window normally will not result in data loss.


**Q**: What should I do if a client does not have a phone number?<br>
**A**: You can enter their preferred contact method (e.g. email address or messaging handle) in the `p/PHONE` field instead.


**Q**: How do I reorder my clients?<br>
**A**: Reordering is not currently supported. As a workaround, close the app and manually reorder the client entries in the data file at `data/addressbook.json`.


**Q**: How do I transfer my data to another computer?<br>
**A**: Install Hairy Pawter on the other computer, then copy the entire `data/` folder from your current home folder to the same location on the other computer.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the app to a secondary screen and later return to using only the primary screen, the window may open off-screen. Fix: delete the `preferences.json` file in your home folder before restarting the app.
2. **If you minimise the Help Window** and then run the `help` command again (or press `F1`), the existing minimised window will not reappear and no new window will open. Fix: manually restore the minimised Help Window from your taskbar.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
-------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**[addClient](#adding-a-client-addclient)** | `addClient p/PHONE [n/NAME] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g. `addClient n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend`
**[addPet](#adding-a-pet-addpet)** | `addPet n/NAME p/PHONE​ [s/SPECIES] [b/BREED] [nt/NOTES] [pic/PICTURE]` <br> e.g. `addPet n/Meowy p/22224444`
**[clear](#clearing-all-records-clear)** | `clear`
**[deleteClient](#deleting-a-client-deleteclient)** | `deleteClient POSITION`<br> e.g. `deleteClient 3`
**[deletePet](#deleting-a-pet-deletepet)** | `deletePet POSITION`<br> e.g. `deletePet 1`
**[editClient](#editing-a-client-editclient)** | `editClient POSITION [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g. `editClient 2 n/James Lee e/jameslee@example.com`
**[editPet](#editing-a-pet-editpet)** | `editPet POSITION [n/NAME] [s/SPECIES] [b/BREED] [nt/NOTES] [pic/PICTURE]`<br> e.g. `editPet 2 n/Pongo`
**[exit](#exiting-the-app-exit)** | `exit`
**[find](#locating-clients-and-pets-by-keywords-find)** | `find KEYWORD…`<br> e.g. `find James dog`
**[help](#viewing-help-help)** | `help`
**[list](#listing-all-clients-and-pets-list)** | `list`


## Short-form commands

For faster typing, Hairy Pawter supports short-form aliases for the most common commands. Short-forms accept the same parameters as their full equivalents.

| Action | Short-form | Example |
|--------|------------|---------|
| `addClient` | `ac` | `ac n/John Doe p/98765432` |
| `addPet` | `ap` | `ap n/Meowy p/98765432` |
| `deleteClient` | `dc` | `dc 1` |
| `deletePet` | `dp` | `dp 2` |
| `editClient` | `ec` | `ec 1 n/Jane Doe` |
| `editPet` | `ep` | `ep 1 s/Cat` |
