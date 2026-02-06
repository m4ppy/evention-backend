# Evention - Domain Rules

this is list of domain rules include link to it's tests.

---

## Ticket Scope

domain rules about Ticket domain.

---

### "non-maintainer cannot change ticket status" ✅

### "non-project-member cannot write comments" ✅

### "ticket cannot allow non-author to edit comment" ✅

### "ticket cannot be created by non-project-member" ✅

---

## Project Scope

domain rules about Project domain.

---

### "non-project-owner cannot add members to project" ✅
- add isProjectOwner() in Project.java
- check the authority when calling addMaintainer() and addContributor()

### "non-project-owner cannot remove members from project" ✅

-> "member-cannot-be-added-twice-to-project"

### "member cannot be added twice to project" ✅

### "project cannot remove non-project-member" ✅

### "project cannot remove final project-owner" ✅