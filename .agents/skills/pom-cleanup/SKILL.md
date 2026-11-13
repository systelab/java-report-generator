---
name: pom-cleanup
description: >
  Review the java-report-generator pom.xml for structural defects, duplicated tags, version conflicts,
  and unused or redundant dependencies/configuration; propose fixes for individual approval.
---

# POM cleanup

## Project facts
- Single-module Maven project (`com.systelab:java-report-generator`): one root `pom.xml`, no parent POM,
  no imported BOM, no internal modules.
- Dependencies: `jasperreports`, `jasperreports-fonts`, `jasperreports-pdf` (one shared version property),
  `poi`, `poi-ooxml`, `lombok`, `junit-jupiter-api`, `junit-jupiter-engine`,
  `java-report-test-utilities` (resolved via the `jitpack.io` repository).
- The `github` profile's `distributionManagement` is a publishing target, not a dependency version source.

## Rules
- Use this skill to find defects, not to perform version upgrades; a newer available version is not a defect.
- Preserve unrelated worktree changes; never commit unless explicitly requested.
- Do not edit or remove anything until the user approves that specific proposed fix.
- Report evidence and uncertainty; never change a POM based only on suspicion.
- Keep approved fixes separate from unrelated changes.

## Review checklist
- Duplicate or conflicting declarations: repeated XML elements, repeated properties, repeated
  `groupId:artifactId:type:classifier` coordinates.
- Version conflicts: inconsistent versions/scopes/exclusions/optional flags for the same dependency;
  check the resolved dependency tree (`mvn dependency:tree`) for direct/transitive conflicts and mediation.
- Unused dependencies: flag any with no demonstrated use in source, tests, or build plugins.
  Use `mvn dependency:analyze` as supporting evidence only — reflection, service loading, annotations,
  generated code, and test-only usage can cause false positives.
- Misaligned families: `jasperreports`/`jasperreports-fonts`/`jasperreports-pdf` must share one version;
  `junit-jupiter-api`/`junit-jupiter-engine` must share one version.
- Properties: undefined, misspelled, duplicated, or unused properties and property references
  (for example the `jasperrport.version` typo).
- Scopes: test-only dependencies declared with compile scope; do not change scope without approval.
- Deprecated or obsolete dependency/plugin coordinates and configuration.
- Unnecessary dependencies, plugins, profiles, repositories, or configuration with no demonstrated use.
- Compiler settings: conflicting or redundant `source`/`target`/`release`; Lombok annotation-processor
  path and version alignment with the compiler plugin.

## Findings and approval
- Report all findings before asking to apply any fix, as a table:

  | Issue | pom.xml lines | Evidence and impact | Proposed fix | Behavior risk |
  |---|---|---|---|---|

- Separate verified issues from suspicious items needing confirmation; state missing evidence explicitly.
- Ask about each proposed fix individually and sequentially with `ask_user`.
- Apply only explicitly approved fixes; preserve declined findings.
- Re-ask for approval if evidence changes a proposal.

## Validation and result
- Run the full build `mvn -B clean install` after approved fixes; use the actual exit code, not log matching.
- Verify effective/resolved versions with `mvn help:effective-pom` / `mvn dependency:tree` when relevant.
- If validation fails, report evidence and ask before any additional fix or rollback.
- Save reproducible commands and sanitized logs in the session workspace; omit credentials and secrets.
- Summarize accepted fixes, declined findings, unresolved uncertainty, and validation status.
