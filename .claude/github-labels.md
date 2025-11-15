# GitHub Labels Reference

Quick reference for all available labels in the Tech Interview System repository.

Last updated: 2025-11-14

## Type Labels

| Label | Description | Color |
|-------|-------------|-------|
| `type:bug` | Bug fix | #d73a4a |
| `type:feature` | New feature | #a2eeef |
| `type:docs` | Documentation | #0075ca |
| `type:refactor` | Code refactoring / tech debt | #1d76db |
| `type:test` | Test coverage | #0e8a16 |
| `type:infrastructure` | Infrastructure / CI/CD | #5319e7 |

**Usage**: Every issue should have exactly ONE type label.

## Priority Labels

| Label | Description | Color |
|-------|-------------|-------|
| `priority:p0` | Critical - do first | #b60205 |
| `priority:p1` | High - do soon | #d93f0b |
| `priority:p2` | Medium - do later | #fbca04 |
| `priority:p3` | Low - nice to have | #c5def5 |

**Usage**: Most issues should have a priority label. P0 = blocks other work, P1 = important but not blocking, P2/P3 = nice to have.

## Agent Assignment Labels

| Label | Description | Color |
|-------|-------------|-------|
| `agent:backend` | Backend specialist work | #0366d6 |
| `agent:frontend` | Frontend specialist work | #0366d6 |
| `agent:testing` | Testing specialist work | #0366d6 |
| `agent:database` | Database specialist work | #0366d6 |
| `agent:devops` | DevOps specialist work | #0366d6 |
| `agent:docs` | Documentation specialist work | #0366d6 |

**Usage**: Use when spawning sub-agents for work. Can have multiple agent labels if work spans domains.

## Status Labels

| Label | Description | Color |
|-------|-------------|-------|
| `status:not-started` | Work not yet started | #d4c5f9 |
| `status:in-progress` | Work currently in progress | #fbca04 |
| `status:blocked` | Work blocked by dependency or issue | #d93f0b |
| `status:pr-ready` | PR created, awaiting review | #0e8a16 |
| `status:merged` | PR merged to dev/main | #6f42c1 |

**Usage**: Status labels track work progress. Update as work progresses.

## Epic Labels

| Label | Description | Color |
|-------|-------------|-------|
| `epic` | Epic tracking multiple related issues | #7057ff |

**Usage**: Use for large initiatives that span multiple issues/PRs.

## Category Labels (Tech Interview System-specific)

| Label | Description | Color |
|-------|-------------|-------|
| `category:interview-evaluation` | Interview problem evaluation work | #1d76db |

**Usage**: Optional. Use to highlight domain-specific work areas.

## Meta Labels (Cross-project)

| Label | Description | Color |
|-------|-------------|-------|
| `meta:workflow` | Cross-project workflow/methodology improvements | #8b5cf6 |

**Usage**: Use for work that improves development processes across all projects (not Tech Interview System-specific). Examples: agent hallucination investigation, D009 protocol refinements, multi-agent coordination patterns.

## Default GitHub Labels

| Label | Description | Color |
|-------|-------------|-------|
| `bug` | Something isn't working | #d73a4a |
| `documentation` | Improvements or additions to documentation | #0075ca |
| `duplicate` | This issue or pull request already exists | #cfd3d7 |
| `enhancement` | New feature or request | #a2eeef |
| `good first issue` | Good for newcomers | #7057ff |
| `help wanted` | Extra attention is needed | #008672 |
| `invalid` | This doesn't seem right | #e4e669 |
| `question` | Further information is requested | #d876e3 |
| `wontfix` | This will not be worked on | #ffffff |

**Usage**: These are GitHub defaults. Prefer using the `type:*` labels instead for consistency.

## Common Label Combinations

### Feature Work
```bash
gh issue create --label "type:feature,priority:p1,agent:backend"
```

### Bug Fix
```bash
gh issue create --label "type:bug,priority:p0,agent:frontend"
```

### Documentation
```bash
gh issue create --label "type:docs,priority:p2,agent:docs"
```

### Database Schema Work
```bash
gh issue create --label "type:feature,priority:p1,category:interview-evaluation,agent:database"
```

### Interview Evaluation Features
```bash
gh issue create --label "type:feature,priority:p1,category:interview-evaluation,agent:backend"
```

## Querying Issues by Label

```bash
# View all P0 issues
gh issue list --label "priority:p0"

# View all backend work in progress
gh issue list --label "agent:backend,status:in-progress"

# View all bugs
gh issue list --label "type:bug"

# View PRs ready for review
gh issue list --label "status:pr-ready"

# View all interview evaluation work
gh issue list --label "category:interview-evaluation"
```

## Label Management

```bash
# List all labels
gh label list

# Create new label
gh label create "new-label" --description "Description" --color "0075ca"

# Delete label
gh label delete "old-label"

# Add labels to issue
gh issue edit 123 --add-label "type:feature,priority:p1"

# Remove labels from issue
gh issue edit 123 --remove-label "status:not-started"
```

## Automation Integration

The label taxonomy integrates with automation scripts:

- **agent-worktree.sh**: Automatically applies `agent:*` and `status:*` labels when creating agent worktrees
- **GitHub Actions**: CI/CD pipelines can trigger based on labels (e.g., auto-deploy on `status:pr-ready`)
- **Issue Templates**: Pre-populate labels based on issue type

## Label Lifecycle

**Status labels track work progress from creation to completion**

### State Diagram

```
[Issue Created]
    ↓
status:not-started ──→ [Nexus spawns agent] ──→ status:in-progress
                                                        ↓
                                    [Agent blocked] ──→ status:blocked
                                                        ↓
                                    [Blocker resolved] ──→ status:in-progress
                                                        ↓
                                    [PR created] ──→ status:pr-ready
                                                        ↓
                                    [PR merged] ──→ status:merged ──→ [Close Issue]
```

### Key Rules

1. **One status label at a time** - Remove old status when adding new
2. **Update immediately** - Change labels when status changes (not later)
3. **Query before spawning** - `gh issue list --label "status:in-progress"` shows active work
4. **Close only after merge** - Issues stay open until PR merged to main

### Quick Reference

| Transition | Command |
|------------|---------|
| Create issue | `gh issue create --label "type:*,priority:*,status:not-started"` |
| Start work | `gh issue edit N --add-label "status:in-progress" --remove-label "status:not-started"` |
| Get blocked | `gh issue edit N --add-label "status:blocked" --remove-label "status:in-progress"` |
| Resume work | `gh issue edit N --add-label "status:in-progress" --remove-label "status:blocked"` |
| Create PR | `gh issue edit N --add-label "status:pr-ready" --remove-label "status:in-progress"` |
| Merge PR | `gh issue edit N --add-label "status:merged" --remove-label "status:pr-ready"` |
| Close issue | `gh issue close N --comment "Merged in PR #X"` |

## Label Lifecycle Example

```bash
# 1. Create issue with initial labels
gh issue create --title "Design database schema for interview problems" \
  --label "type:feature,priority:p0,agent:database,category:interview-evaluation,status:not-started"

# 2. Start work
gh issue edit 1 --add-label "status:in-progress" --remove-label "status:not-started"

# 3. Blocked by dependency
gh issue edit 1 --add-label "status:blocked" --remove-label "status:in-progress"

# 4. Create PR
gh issue edit 1 --add-label "status:pr-ready" --remove-label "status:blocked"

# 5. After merge
gh issue edit 1 --add-label "status:merged" --remove-label "status:pr-ready"
```
