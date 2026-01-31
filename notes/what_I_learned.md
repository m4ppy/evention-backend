# Evention – DDD + TDD Core Learnings

These notes are **not theory**.  
They are reminders of things I struggled with and eventually understood by building.

---

## 1. What a Domain Rule Actually Is

- A domain rule is a **business constraint**, not a feature
- Most domain rules appear as **failing cases**
- If there is no meaningful failure, it’s probably not a domain rule

Examples:
- ❌ "Anyone can edit issue name" → not a rule
- ✅ "Non-maintainer cannot change ticket status" → rule

Reminder:
> Domain rules usually answer: **“What must NOT happen?”**

---

## 2. How to Choose the Scope of a Domain Rule

- A domain rule belongs to **exactly one aggregate**
- That aggregate is responsible for enforcing it
- Other aggregates may be *asked*, not inspected

Example:
- Ticket does NOT check `actor.role`
- Ticket asks Project → `project.isMaintainer(actor)`

Reminder:
> If a rule touches two aggregates, one must delegate to the other.

---

## 3. Why Tests Change When the Domain Grows (And Why That’s OK)

- Tests often change when domain structure evolves
- This is normal and expected
- Domain behavior should stay stable; setup may change

Bad tests:
- Assert internal fields
- Depend on construction details

Good tests:
- Assert domain behavior
- Accept refactoring

Reminder:
> Tests are allowed to move. Domain meaning should not.

---

## 4. My Actual TDD Cycle for DDD

This is the real workflow I used (not textbook):

1. Pick **one** domain rule
2. Write **one failing test**
3. Implement the **minimum** code
4. Let the test force structure
5. Stop — do NOT “complete” the domain

Reminder:
> One rule → one failing test → one small change

---

## 5. Over-Modeling Is a Smell

Common mistakes I made:
- Thinking “what if X happens?” without a test
- Adding validations outside current rule scope
- Creating exceptions “just in case”

Rule:
> If there is no failing test, it is not a rule yet.

---

## 6. Aggregate Root vs Entity (In Practice)

- Aggregate root is the **only public decision maker**
- Internal entities should not leak (`ProjectMember`, `ProjectRole`)
- All invariants are enforced at the aggregate root

Example:
- `Project` is public
- `ProjectMember` is package-private
- Roles don’t escape the aggregate

Reminder:
> If something can bypass invariants, it shouldn’t be public.

---

## 7. Why Domain Methods Live on Entities (Not Services)

- Behavior belongs where the state lives
- `changeStatus()` belongs to `Ticket`
- Services coordinate, entities decide

If logic feels awkward in an entity:
- It may be in the wrong aggregate
- Or the rule isn’t clear yet

Reminder:
> Entities are not data holders. They protect invariants.

---

## 8. How Tests Shape Domain Language

- Test names influenced exception names
- Exception names clarified domain meaning
- Language consistency emerged through tests

Example:
- `project_cannot_remove_non_project_member`
- `DuplicateProjectMemberException`

Reminder:
> Tests are executable domain documentation.

---

## 9. When NOT to Add a Success Test

- Constraint-only rules often need **only failing tests**
- Success path is already covered elsewhere
- Don’t duplicate success tests

Example:
- "member cannot be added twice" → only failing test

Reminder:
> New rule ≠ new success case

---

## 10. Why I Felt Confused About Issue Management

- Confusion came from not knowing real workflows
- Understanding emerged **during implementation**
- Domain knowledge is discovered, not preloaded

Important realization:
> You don’t need full understanding before coding —  
> understanding comes from enforcing rules.

---

## Final Reminder

- Domain rules are small
- One rule at a time is enough
- Clarity comes from building, not planning

If it feels slow, it’s working.

---

## 11. Why Custom Domain Exceptions Exist

- Custom domain exceptions are **not technical**
- They represent **business rule violations**
- They make the domain “speak” clearly

Difference:
- `IllegalStateException` → technical failure
- `DuplicateProjectMemberException` → domain rule broken

Why they matter:
- Tests can assert **meaning**, not mechanics
- Later layers (service / API) can react differently
- Exception names become part of domain language

Important:
- It is OK to start with `IllegalStateException`
- Promote to domain exception **when the rule is real**

Reminder:
> If a product manager asked “what went wrong?”,  
> the exception name should answer it.

---

## 12. How to Name Domain Rules

Best when named as:
>  "what is forbidden / what fails / what cannot happen"

Instead of:
- must
- should

Prefer:
- cannot
- is rejected
- is not allowed

Because
- domain rules are usually constraints
- constraints show up as failures

---

Two valid naming styles exist.

### Actor-focused naming
Examples:
- non-maintainer cannot change ticket status
- non-project-member cannot write comments

Pros:
- Clear about *who* is restricted
- Easy to reason about authorization

Cons:
- Slightly procedural
- Less aggregate-centered

---

### Aggregate-behavior-focused naming
Examples:
- ticket_status_cannot_be_changed_by_non_maintainer
- project_cannot_remove_non_project_member

Pros:
- Aggregate “speaks” the rule
- Aligns strongly with DDD
- Clear responsibility

Cons:
- Longer names
- Requires more thought

---

### Choosing between them

Rule of thumb:
> **The subject of the rule should be the aggregate that enforces it.**

- Rule enforced by `Project` → project is the subject
- Rule enforced by `Ticket` → ticket is the subject

Important:
- Both styles are valid
- Consistency matters more than purity
- Older rules do not need rewriting

Reminder:
> Naming is modeling.  
> Choose clarity, then stay consistent.
