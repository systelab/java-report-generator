---
name: upgrade-components
description: Interactively select Maven dependency updates, check compatibility, obtain implementation approval, verify all tests, and propose documentation updates individually. Use for dependency upgrades, JasperReports/POI/Lombok/JUnit upgrades, Java version upgrades, latest-version checks, and related build warnings or pom.xml reviews.
---

# Upgrade components

## Project facts
- Single-module Maven project (`com.systelab:java-report-generator`): one root `pom.xml`, no parent POM,
  no imported BOM, no internal modules.
- A "component" is any versioned coordinate in that `pom.xml`: a dependency (`jasperreports`,
  `jasperreports-fonts`, `jasperreports-pdf`, `poi`, `poi-ooxml`, `lombok`, `junit-jupiter-api`,
  `junit-jupiter-engine`, `java-report-test-utilities`), a build plugin (`maven-compiler-plugin`,
  `maven-surefire-plugin`), or the declared Java version/toolchain.

## Rules
- Plan first: selecting versions is not permission to implement; get final approval in step 3 before applying
  updates or migrations.
- Keep versions the user says to keep.
- Never unilaterally exclude an item with a newer stable version from selection. Mark Keep automatically only
  with verified evidence of no newer stable version; otherwise Keep needs the user's explicit decision.
- Never remove an existing version pin or dependency declaration.
- Keep `jasperreports`, `jasperreports-fonts`, `jasperreports-pdf` on one shared version property.
- Keep `junit-jupiter-api` and `junit-jupiter-engine` aligned.
- Name version properties `<artifact_id>.version`; families that move together may share one property.
  Renaming the `jasperrport.version` typo is part of any approved JasperReports upgrade, not a drive-by rename.
- Corroborate releases with publisher docs and Maven Central metadata
  (`https://repo1.maven.org/maven2/<group path>/<artifact>/maven-metadata.xml`). Exclude alpha/beta/RC/
  milestone/early-access/SNAPSHOT. Don't equate the last metadata entry with the latest stable version;
  Solr search results can be stale.
- `java-report-test-utilities` resolves via `jitpack.io` from `com.github.systelab:java-report-test-utilities`;
  check its GitHub tags/releases (and `jitpack.io/api/builds/...` if needed), not just Maven Central. If
  discovery is blocked, mark Unknown, explain, and ask — never silently mark Keep.
- The `github` profile's `distributionManagement` is a publishing target, not a dependency version source.
- If an MCP is requested, verify the server's existence, source, permissions, and install steps before
  claiming availability or installing it.
- Preserve unrelated worktree changes; never commit unless explicitly requested.

## Step 0: Offer a preliminary pom.xml review
- First action: `ask_user` whether to review `pom.xml` for issues before selecting updates (Review / Skip).
  Wait for the answer before any workspace inspection or update selection.
- If declined, record it and proceed to preparation.
- If accepted, follow `.agents/skills/pom-cleanup/SKILL.md` end to end (findings, individual fix approval,
  validation with `mvn -B clean install`).
- After applied fixes, refresh the component inventory. Keep this review separate from the later
  dependency-update approval and baseline build.

## Preparation: documentation and inventory
- Read `README.md`, `CONTRIBUTING.md`, `CODING_GUIDELINES.md`, `.github/workflows/build.yml`,
  `.github/workflows/deploy.yml`, and templates; exclude generated output (`target/`). Report inaccessible
  or contradictory guidance rather than guessing.
- Inventory `java.version`, `maven-compiler-plugin`, `maven-surefire-plugin`, and every dependency listed
  under Project facts.
- Verify the newest stable Java feature release and applicable JDK patch releases against publisher docs;
  don't limit choices to the installed JDK or CI's pinned version.

## Step 1: Select updates interactively
- Present every item with current version, latest stable, proposed target, and a selection column. Include
  unchanged entries; mark available upgrades Pending, not Keep, until decided.
- Use a real multi-select UI if available; markdown checkboxes are not interactive — never treat them as
  recorded decisions.
- Otherwise ask via `ask_user`, one family at a time, stating current/target versions. Keep the three
  JasperReports artifacts together. Ask about Java before dependent libraries. Always offer the newest
  stable version even when recommending Keep or another target.
- Reconcile the inventory with selections before step 2: every item needs an explicit decision or verified
  evidence of no newer stable version.

## Step 2: Check compatibility and feasibility
- Analyze the selected set together: Java/toolchain support, Lombok processor compatibility, POI and
  JasperReports compatibility with Java, Jupiter API/engine alignment, and `java-report-test-utilities`
  compatibility with the selected JUnit version. Consult release notes and migration guides.
- State feasibility based on evidence; distinguish documented compatibility from untested runtime behavior.
- For each conflict, propose a concrete alternative and get individual `ask_user` approval before changing
  the selection. Recheck the revised selection and report any remaining uncertainty.

## Step 3: Confirm and execute
- Immediately before requesting execution approval, display the final proposed-updates table in chat
  (decision, item, current, selected target, migration/reason). A saved file or generic confirmation is
  not sufficient.
- Ask for explicit approval to start (in plan mode, use `exit_plan_mode`; otherwise `ask_user`). If the
  proposal changes afterward, redisplay and reconfirm.
- Run baseline `mvn -B clean install`; capture exit code and full logs. Preserve baseline Surefire reports
  before any subsequent `clean`. If baseline fails, report and get approval before fixing or proceeding.
- Apply only approved updates and directly necessary migrations.

## Step 4: Run all tests and report
- Run the full post-update `mvn -B clean install`, not a partial/offline build; use the actual exit code.
- Verify resolved versions with `mvn dependency:tree` / `mvn help:effective-pom`.
- Parse fresh `target/surefire-reports` XML for discovered/passed/failed/errored/skipped counts; avoid
  double counting and investigate any drop in discovered tests.
- For each failure, report cause, evidence, impact, and proposed fix; get individual `ask_user` approval
  before applying. Rerun affected tests, then the full suite. Report unresolved/declined fixes plainly.
- Summarize Maven/compiler/JVM/runtime warnings with counts and status; keep remediation a separate
  approval, and don't suppress warnings indiscriminately.
- Save reproducible commands, report paths, and sanitized logs in the session workspace.

## Step 5: Propose documentation updates individually
- Compare actual resolved versions, migrations, test commands, and warnings against `README.md`,
  `CONTRIBUTING.md`, `CODING_GUIDELINES.md`, and build/deploy workflows.
- Present each affected document with outdated content, proposed replacement, and reason; state if none
  are affected.
- Get individual `ask_user` approval per document change — dependency approval does not cover docs.
- Apply only approved edits and run existing documentation checks where applicable.

## Compatibility notes (verify, don't assume)
- JasperReports major bumps can rename/split artifacts and change fonts/PDF packaging; verify the three
  artifacts share one release and recheck Java-version support before raising `java.version`.
- POI major bumps can require a matching `poi-ooxml-full`/`poi-ooxml-lite` split or drop deprecated APIs.
- Lombok must support the selected Java and compiler-plugin version (`annotationProcessorPaths`); mismatches
  surface as annotation-processing failures, not plain compile errors.
- Jupiter API/engine must share a version; confirm `java-report-test-utilities` still supports it.
- JDK bumps: Mockito/byte-buddy agent warnings and Lombok's `sun.misc.Unsafe` warning are known JDK noise,
  not necessarily new defects.

## Report
- pom.xml-review decision, findings, approved fixes, and post-fix results (or explicit skip).
- Confirmed selection and feasibility assessment.
- Final before/after version table and full baseline/after test report.
- Individually approved failure fixes and remaining blockers.
- Warning summary with separate remediation proposals.
- Individually approved documentation changes.
