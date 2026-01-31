# DDD Failure Types & Exception Naming

## Why Failure Classification Matters

Not all failures are the same.

If you name every exception using one rule, you will:

* Mix domain rules with permission checks
* Leak application concerns into the domain
* Lose clarity when reading code, logs, and tests

DDD works best when **the reason for failure is explicit in the name**.

---

## The Three Core Failure Categories

### ① Missing Data / Invalid Reference

**Question answered:**

> Does the required domain object exist?

**Characteristics:**

* No domain invariant involved
* No business rule violated
* The command refers to something that isn’t there

**Examples:**

* Removing a non‑existing project member
* Updating a ticket that does not exist

**Exception naming style:**

```
<X>NotFoundException
```

**Examples:**

* `ProjectMemberNotFoundException`
* `TicketNotFoundException`

---

### ② Permission / Authorization Failure

**Question answered:**

> Is the actor allowed to perform this action?

**Characteristics:**

* Depends on *who* is acting
* Contextual / policy-based
* Often belongs to the application layer

**Examples:**

* Non-owner tries to remove a project owner
* Read-only user tries to modify a ticket

**Exception naming style:**

```
Unauthorized<X>Exception
```

**Examples:**

* `UnauthorizedProjectOperationException`
* `UnauthorizedTicketUpdateException`

---

### ③ Domain Rule / Invariant Violation

**Question answered:**

> Would this behavior violate a domain rule?

**Characteristics:**

* Independent of UI or API
* Enforced inside the aggregate
* Represents a *forbidden state transition*

**Examples:**

* Removing the last project owner
* Closing a project with open tickets
* Moving a ticket to an invalid state

**Exception naming style:**

```
<Aggregate><Cannot><ForbiddenBehavior>Exception
```

**Examples:**

* `ProjectCannotRemoveLastOwnerException`
* `ProjectCannotCloseWithOpenTicketsException`
* `TicketCannotTransitionFromClosedException`

---

## Summary Table

| Failure Type | What Failed          | Layer          | Naming Pattern                     |
| ------------ | -------------------- | -------------- | ---------------------------------- |
| Missing data | Object doesn’t exist | Domain / App   | `XNotFoundException`               |
| Permission   | Actor not allowed    | App / Security | `UnauthorizedXException`           |
| Domain rule  | Invariant violated   | Domain         | `AggregateCannotBehaviorException` |

---

## Key Rule to Remember

> **Exception names should describe the reason the aggregate refused to act.**

* If there is no invariant → don’t invent one
* If a rule rejected behavior → name the rule
* If permission failed → name the policy

---

## Practical Checklist (Before Naming an Exception)

Ask yourself:

1. *Did the aggregate reject this because of a business rule?*
2. *Or because the actor isn’t allowed?*
3. *Or because required data doesn’t exist?*

The answer determines the name.
