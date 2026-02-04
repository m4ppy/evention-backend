# DDD – Exposing Identity vs Exposing Internals

This document summarizes a **core DDD encapsulation rule**:

> **Exposing identity is allowed. Exposing internals is not.**

Understanding this distinction prevents broken aggregates and leaky domain models.

---

## 1. The Core Principle

In DDD:

* **Entities are defined by identity**
* **Aggregates protect internal state**

So:

* Identity may be exposed
* State and structure must be guarded by the aggregate root

---

## 2. What “Exposing Identity” Means

**Identity answers one question only:**

> *Who is this entity?*

It does **not** answer:

* What data does it hold?
* How is it structured?
* How should it be modified?

### Examples (Allowed)

```java
UUID getId();
```

```java
CommentId getId();
```

```java
Ticket.createComment(...) → CommentId
```

Why this is safe:

* Identity is immutable
* Identity does not leak behavior
* Identity enables future commands

---

## 3. What “Exposing Internals” Means

**Internals answer questions about state or structure:**

> *What is inside the entity or aggregate?*

This is what DDD tries to prevent.

### Examples (Not Allowed)

```java
String getContent();
Member getAuthor();
LocalDateTime getCreatedAt();
```

```java
List<Comment> getComments();
```

Why this is dangerous:

* Breaks aggregate encapsulation
* Allows logic outside the aggregate
* Couples callers to internal structure
* Makes invariants unenforceable

---

## 4. Aggregate Rule of Thumb

> **Aggregates expose behavior and identity — never structure.**

### Good Aggregate API

```java
CommentId createComment(actor, content);
void updateComment(actor, commentId, newContent);
void deleteComment(actor, commentId);
```

### Bad Aggregate API

```java
List<Comment> getComments();
Comment findComment(UUID id);
```

The bad examples force callers to reason about internals.

---

## 5. Internal vs External View (Mental Model)

```
Ticket (Aggregate Root)
 ├── List<Comment>   ← internal (hidden)
 │     ├── Comment(id=1)
 │     └── Comment(id=2)
 └── API
       ├── createComment() → CommentId
       └── updateComment(CommentId)
```

* Internally: full entities
* Externally: identity only

---

## 6. Common Misconceptions

### ❌ “If I return an ID, I must store IDs”

Wrong.

* Return values are for **communication**
* Fields are for **ownership**

### ❌ “getId() leaks internal details”

Wrong.

* Identity is not internal state
* It is the definition of an entity

---

## 7. When Exposing Internals *Is* Acceptable (Rare)

Only when:

* The object is a **Value Object**
* The object is **immutable**
* No invariants depend on it

Comments are **entities**, not value objects.

---

## 8. One-Line Rules to Remember

* **Identity is safe to expose**
* **State must be protected**
* **If you add a getter just for tests, stop**
* **Commands go through the aggregate root**

---

## 9. Why This Matters Long-Term

Following this rule gives you:

* Strong aggregate boundaries
* Clear ownership of rules
* Refactor-safe models
* Easier persistence and APIs

Violating it leads to:

* Anemic models
* Hidden coupling
* Rule duplication

---

Use this document as a checklist whenever you feel tempted to add getters or expose collections.
