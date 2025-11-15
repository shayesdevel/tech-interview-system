# Quality Gates - Tech Interview System

**Purpose**: Mandatory checkpoints all agents must pass before committing, merging, and completing work.

**Scope**: Applies to ALL agents (Backend, Frontend, Testing, Database, DevOps, Docs, Orchestrator)

**Tech Stack**: Java/Spring (backend) + TypeScript/React (frontend)

---

## Gate 1: Pre-Flight Check (BEFORE Starting Work)

### Tool Verification
- [ ] Required tools installed and accessible:
  ```bash
  java -version    # Java 17+
  mvn --version    # Maven 3.8+
  node --version   # Node 18+
  npm --version    # npm 9+
  ```

### Workspace Validation
- [ ] Working in correct directory (worktree for specialists, main repo for orchestrator)
- [ ] Verify location: `pwd` shows expected path
- [ ] On correct branch: `git branch --show-current`
- [ ] Clean starting state: `git status` (commit or stash existing changes)

### Documentation Access
- [ ] Can access quality gates: `ls ../../docs/00-active/quality-gates.md`
- [ ] Can access cognitive framework: `ls ../cognitive-framework/`
- [ ] Can access project docs: `ls ../../docs/00-active/`

**If ANY pre-flight check fails: STOP and report error before starting work**

---

## Gate 2: During Development (Continuous Monitoring)

### File Hygiene (Check with `git status` frequently)
Prevent committing:
- ❌ Binary files: `.class`, `.jar`, `.pyc`, `.exe`, `.dll`
- ❌ Generated files: `node_modules/`, `dist/`, `build/`, `target/`, `__pycache__/`
- ❌ IDE files: `.idea/`, `.vscode/` (unless project-standard), `.DS_Store`
- ❌ Secrets: `.env`, `credentials.json`, API keys, passwords
- ❌ Large files: `>1MB` (use Git LFS if needed)

**Validation command**:
```bash
git status --porcelain | grep -E "\\.class$|\\.jar$|node_modules/|target/"  # Should return empty
```

### Documentation Sync
- [ ] If you modify code, update relevant README/docs
- [ ] If you add files, ensure they're documented in appropriate README
- [ ] If you remove files, remove references from docs

**Validation command**:
```bash
# Check for broken README references (manual review)
grep -r "backend/\|frontend/" docs/00-active/
```

---

## Gate 3: Pre-Commit Validation (BEFORE `git commit`)

### Build Verification
- [ ] Build succeeds (or document why skipped):
  ```bash
  # Backend (Java/Spring)
  mvn clean install

  # Frontend (TypeScript/React)
  npm run build
  ```

### Test Verification
- [ ] Tests pass (or document why skipped):
  ```bash
  # Backend (Java/Spring)
  mvn test

  # Frontend (TypeScript/React)
  npm test
  ```

### Code Quality (if applicable)
- [ ] Linting passes:
  ```bash
  # Backend (Java/Spring)
  mvn checkstyle:check

  # Frontend (TypeScript/React)
  npm run lint  # or: npx eslint src/
  ```

### Git Hygiene
- [ ] **NO binary/generated files in `git status`**
- [ ] **NO secrets in commit** (.env, credentials, API keys)
- [ ] Meaningful commit message following project convention
- [ ] Verify changes: `git diff --cached` (review what you're committing)

### Documentation Validation
- [ ] README references to your deliverables are accurate
- [ ] No broken links in documentation you modified
- [ ] Code comments added for complex logic

### Session Journal (MANDATORY)
- [ ] **Session journal entry created**: `docs/00-active/journal/YYYY-MM-DD.md` or `session-NN-description.md`
- [ ] Journal includes: What was accomplished, decisions made, blockers encountered
- [ ] See D014 protocol: `../cognitive-framework/cognitive-core/quality-collaboration/quick-reference/d014-communication-quick-ref.md`

**If ANY pre-commit check fails: Fix before committing**

---

## Gate 4: Pre-Merge Validation (BEFORE PR merge)

### Integration Testing
- [ ] All CI/CD checks passing
- [ ] No merge conflicts with base branch
- [ ] Branch rebased or merged with latest base
- [ ] Integration tests pass (if applicable):
  ```bash
  # Backend integration tests
  mvn verify

  # Frontend integration tests (if configured)
  npm run test:integration
  ```

### Cross-Reference Check
- [ ] Changes don't break other agents' work
- [ ] API contracts maintained (if backend changed endpoints, frontend updated)
- [ ] Database migrations applied (if schema changed)

### Documentation Handoff
- [ ] PR description explains what changed and why
- [ ] Links to relevant GitHub issues
- [ ] Session journal referenced in PR for context

### Quality Review
- [ ] Code review requested (if multi-developer project)
- [ ] Validation gates explicitly confirmed in PR comments
- [ ] Test coverage maintained or improved

**If ANY pre-merge check fails: Block PR merge until resolved**

---

## Gate 5: Post-Merge Verification (AFTER PR merged)

### Tracking Updates
- [ ] GitHub issue status updated (closed or commented)
- [ ] Relevant project docs updated (SPRINT.md, NEXT.md, etc.)
- [ ] Session journal confirms task completion

### Environment Validation
- [ ] Main branch builds successfully after merge
- [ ] Integration environment still functional (if applicable)
- [ ] Database migrations applied (if backend changes merged):
  ```bash
  # Database migrations (if using Liquibase/Flyway with Spring)
  mvn liquibase:update
  # Or if using JPA auto-update, verify application starts successfully
  ```

### Orchestrator Verification (D009)
- [ ] Verify commits exist: `git log feature/[agent]-[task] --oneline -5`
- [ ] Verify PR exists and closed: `gh pr view [PR_NUMBER]`
- [ ] Worktree clean: `cd ~/projects/tech-interview-system-worktrees/[agent]-agent && git status`

**If post-merge verification fails: Rollback or hotfix immediately**

---

## Agent-Specific Quality Gates

### Backend Agent (Java/Spring)
**Pre-commit additions**:
- [ ] API endpoints tested with manual curl/Postman
- [ ] Database migrations created (if schema changed)
- [ ] Backend validation: `mvn clean install && mvn test`

### Frontend Agent (TypeScript/React)
**Pre-commit additions**:
- [ ] UI renders without console errors
- [ ] Component tests pass
- [ ] Frontend validation: `npm run build && npm test`

### Testing Agent
**Pre-commit additions**:
- [ ] New tests execute successfully
- [ ] Coverage maintained or improved
- [ ] Testing validation: `mvn test && npm test` (both backend and frontend tests)

### Database Agent
**Pre-commit additions**:
- [ ] Migrations reversible (test downgrade/upgrade if using Liquibase/Flyway)
- [ ] Schema changes documented
- [ ] Database validation: `mvn liquibase:status` (or verify JPA schema generation)

### DevOps Agent
**Pre-commit additions**:
- [ ] Docker builds successfully
- [ ] CI/CD pipeline syntax valid
- [ ] DevOps validation: `docker-compose build && docker-compose config`

### Docs Agent
**Pre-commit additions**:
- [ ] Markdown linting passes (if configured)
- [ ] Links validated (no broken references)
- [ ] Docs validation: Manual review + `grep -r "http" docs/` to check links

---

## Definition of Done Checklist

**Work is NOT complete until ALL items checked**:
- [ ] ✅ Pre-flight check passed (Gate 1)
- [ ] ✅ File hygiene maintained during development (Gate 2)
- [ ] ✅ Build succeeds (Gate 3)
- [ ] ✅ Tests pass (Gate 3)
- [ ] ✅ No binary/generated files committed (Gate 3)
- [ ] ✅ README accurate (Gate 3)
- [ ] ✅ **Session journal created** (Gate 3 - MANDATORY)
- [ ] ✅ CI/CD passing (Gate 4)
- [ ] ✅ No merge conflicts (Gate 4)
- [ ] ✅ PR merged (Gate 4)
- [ ] ✅ GitHub issue updated (Gate 5)
- [ ] ✅ Post-merge verification passed (Gate 5)

**Only report work as complete after ALL checkboxes ticked**

---

## Enforcement

### Orchestrator Responsibility
- Verify ALL agents follow quality gates before merging PRs
- Run D009 verification on all "complete" reports
- Reject PRs that skip gates (binary files, missing session journals, failing tests)

### Sub-Agent Responsibility
- Review quality gates BEFORE starting work (Gate 1)
- Monitor file hygiene DURING work (Gate 2)
- Validate BEFORE committing (Gate 3)
- Confirm in PR description which gates passed

### Human Responsibility
- Review quality gates during project setup
- Enforce gates during code review
- Update gates as project evolves (add new validation commands as stack grows)

---

## Tech Stack Summary

**Backend**: Java 17+ / Spring / Maven
**Frontend**: TypeScript / React / Node 18+ / npm
**Database**: JPA/Hibernate (with Liquibase or Flyway for migrations)
**DevOps**: Docker / docker-compose

**All placeholders customized for interview system tech stack**

---

## Version

**Created**: 2025-11-14
**Last Updated**: 2025-11-14
**Version**: 1.0
**Tech Stack**: Java/Spring + TypeScript/React
