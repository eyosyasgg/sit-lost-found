# SIT Campus Lost & Found System

A Java desktop application that helps students and staff at **Shaggar Institute of Technology (SIT)** report, search for, and recover lost belongings on campus.

This is the final group project for **CS2010: Object-Oriented Programming** (Fall 2026, Group 7, Department of Artificial Intelligence).

---

## Why we built this

When something goes missing on campus, finding it usually means asking around, checking different offices, and hoping someone remembers seeing it. Our goal is to put all of that in one place. If you lose something, you post it. If you find something, you post that too. Anyone can search the listings, and once an item is back with its owner, the post gets closed.

## What it does

- **Report a lost item** with a title, description, category, the place you last saw it, and an optional photo. Losses like student IDs, keys, or phones can be flagged as urgent.
- **Register a found item** and say where it is now, either still with you or dropped off at a security desk. Finders can add an optional verification question (for example, "What's on the lock screen?") so only the real owner can claim it.
- **Search and filter** reports by keyword (matched against title, description, and location) and by category.
- **See who to contact.** Every post automatically carries the reporter's name and contact details, so there's no need to type them in again.
- **Close a case.** An item can be marked as resolved by the person who reported it, or by a staff member with retrieval approval rights. Nobody else can close it.

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java |
| User interface | JavaFX |
| Data storage | Supabase (PostgreSQL) |
| Version control | Git & GitHub |

One rule shaped the whole design: **Java OOP is the heart of the project.** All business logic, such as validation, permissions, and search, lives in our Java classes. The database is only there to store and load data. We deliberately avoided pushing logic into SQL or Supabase features, so the interesting part of the system stays in code we wrote and can explain.

## How the code is organized

The core of the system is the `core.model` package. It holds the domain classes and does **not** import any UI library, so the same model can sit behind any interface we build on top of it.

```
core/model/
├── User.java        abstract base for everyone on campus
├── Student.java     extends User
├── Staff.java       extends User
├── Item.java        abstract base for every post
├── LostItem.java    extends Item
└── FoundItem.java   extends Item
```

### Users

`User` is an abstract class holding the shared identity and contact details: id, full name, email, password hash, phone number, and Telegram tag. Each subclass decides how it shows up in the app through `getDisplayBadge()`.

- **`Student`** adds a student ID, dorm block, and room number. Commuter students can be created without dorm details, in which case they default to "Off-Campus".
- **`Staff`** adds a staff role (lecturer, lab assistant, admin, security, and so on) and a `canApproveRetrieval` flag. That flag is `false` by default, so moderation rights always have to be granted on purpose.

### Items

`Item` is the abstract base for every post in the feed. It stores the title, description, category, location, optional image, resolved status, and creation time. It also keeps a snapshot of the reporter's id, name, and contact info.

- **`LostItem`** adds when the item was last seen and an urgency flag. Its report type is `LOST`.
- **`FoundItem`** adds a drop-off location (defaults to "With finder") and an optional verification question. Its report type is `FOUND`.

Both subclasses implement `getReportType()` and `getStatusSummary()` in their own way, so the feed can treat every post as an `Item` and still show the right details for each one.

## OOP concepts in practice

We tried to make each concept do real work rather than appear just for the sake of it.

| Concept | Where you can see it |
|---|---|
| **Abstraction** | `User` and `Item` are abstract. They define what every user or post must be able to do, without saying how. |
| **Inheritance** | `Student` and `Staff` extend `User`; `LostItem` and `FoundItem` extend `Item`. |
| **Encapsulation** | All fields are private. Setters reject blank values with an `IllegalArgumentException`, and fields that should never change (like `id` and `createdAt`) are `final`. |
| **Polymorphism** | `getDisplayBadge()`, `getReportType()`, and `getStatusSummary()` behave differently depending on the actual object. |
| **Composition** | An `Item` is created from a `User` object. The constructor pulls the reporter's id, name, and contact info straight from it. |
| **Constructor overloading and chaining** | Each class has one constructor for brand-new objects and another for rebuilding objects loaded from the database. `Student` and `Staff` chain with `this(...)` to fill in sensible defaults. |

A small example of how these fit together is `Item.markAsResolved(User actor)`. It accepts any `User`, but only lets the call through if that user is the original reporter, or is a `Staff` member whose `canApproveRetrieval()` returns `true`. Anyone else gets a `SecurityException`.

## Getting started

You'll need:

- JDK 17 or newer
- JavaFX SDK
- A Supabase project (for the database)
- Git

```bash
git clone <repository-url>
cd <project-folder>
```

Open the project in your IDE, add the JavaFX SDK to the project libraries, and configure your Supabase connection details. Full build and run steps will be added here as the UI and database layers are finished.

## Team

| Name | Student ID | Contribution |
|---|---|---|
| Sarda Amin Kedir | SIT-ST-2029-00212 | `Item.java`: abstract item model, reporter composition, resolve permissions, search filtering |
| Eman Ahmed Suliman | SIT-ST-2029-00031 | `Student.java`: student accounts and dorm details |
| Natnael Dessalegn Eba | SIT-ST-2029-00091 | `Staff.java`: staff accounts and retrieval approval rights |
| Elias Mengie Nigatie | SIT-ST-2029-00045 | `LostItem.java` and `FoundItem.java`: lost and found item types |
| Eyosyas Guush Gebreweld | SIT-ST-2029-00123 | |

## Course

CS2010: Object-Oriented Programming, Fall 2026
Department of Artificial Intelligence, Shaggar Institute of Technology