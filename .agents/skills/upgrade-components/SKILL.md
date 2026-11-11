---
name: upgrade-components
description: Interactively select Maven dependency updates, check compatibility, obtain implementation approval, verify all tests, and propose documentation updates individually. Use for dependency upgrades, JasperReports/POI/Lombok/JUnit upgrades, Java version upgrades, latest-version checks, and related build warnings or pom.xml reviews.
---

# Upgrade components

This project (`com.systelab:java-report-generator`) is a **single-module** Maven
project: one `pom.xml` at the repository root, no parent POM, no imported BOM,
and no internal multi-module tree. Adapt the language below accordingly: a
"component" here means any versioned coordinate declared in that single
`pom.xml` — a dependency (`jasperreports`, `jasperreports-fonts`,
`jasperreports-pdf`, `poi`, `poi-ooxml`, `lombok`, `junit-jupiter-api`,
`junit-jupiter-engine`, `java-report-test-utilities`), a build plugin
(`maven-compiler-plugin`, `maven-surefire-plugin`), or the declared Java
version/toolchain.

## Rules
- Plan first. Selecting versions is not permission to implement: obtain the final
  approval in step 3 before applying version updates or their migrations.
  Preliminary pom.xml-review fixes require their own individual approvals in
  step 0; those approvals do not authorize dependency upgrades.
- Keep the versions the user says to keep.
- Never unilaterally exclude a versioned item from update selection. Ask the
  user whenever a newer stable version exists, including Java, the
  `java-report-test-utilities` artifact (resolved through jitpack from
  `github.com/systelab/java-report-test-utilities`), plugins and tooling. The
  only automatic Keep is when verified release evidence shows no newer stable
  version; otherwise Keep requires the user's explicit decision.
- There is no Spring Boot BOM or parent POM in this project, so there is no BOM
  alignment step. Never remove an existing version pin or dependency
  declaration. The three JasperReports artifacts
  (`jasperreports`, `jasperreports-fonts`, `jasperreports-pdf`) must always
  move together to the same release; keep them on one shared property.
- Declare versions in properties named `<artifact_id>.version` (for example
  `poi.version`, `lombok.version`, `junit.version`,
  `java-report-test-utilities.version`). Artifacts that must move together
  (the three JasperReports artifacts) may share one property. The existing
  `jasperrport.version` property is a typo of the JasperReports family name;
  renaming it to a conventional name (for example `jasperreports.version`) is
  part of any approved JasperReports upgrade, not a silent drive-by rename.
- Use the user's requested version source (for example mvnrepository.com), and
  corroborate releases with publisher documentation and Maven Central metadata:
  `https://repo1.maven.org/maven2/<group path>/<artifact>/maven-metadata.xml`
  Exclude prereleases such as alpha, beta, RC, milestones, early access and
  SNAPSHOT; verify ambiguous release labels with the publisher. Do not equate the
  last XML entry with the latest stable version. Solr search results can be stale.
- `java-report-test-utilities` is resolved via the `jitpack.io` repository
  declared in `pom.xml` from `com.github.systelab:java-report-test-utilities`.
  Check the source GitHub repository's tags/releases (and, if needed,
  `https://jitpack.io/api/builds/com.github.systelab/java-report-test-utilities`)
  rather than assuming Maven Central has it. Absence from Maven Central does not
  mean no update exists. If release discovery is blocked, mark Latest stable as
  Unknown, explain the blocker and ask the user how to proceed; never silently
  mark the item Keep.
- The `github` profile's `distributionManagement` (GitHub Packages) is
  unrelated to dependency resolution; do not treat it as a version source.
- If an MCP is requested, inspect available integrations and verify any suggested
  server's existence, source, permissions and installation instructions. Do not
  claim that a server exists or is unavailable without evidence, or install it
  without approval.
- Preserve unrelated worktree changes. Never commit unless explicitly requested.

## Step 0: Offer a preliminary pom.xml review before any other work
- The first user-facing action when executing this upgrade workflow must be
  `ask_user`: ask whether to review the current `pom.xml` for issues before
  selecting dependency updates. Offer Review first or Skip review. Wait for the
  answer before inspecting the workspace, discovering releases, presenting
  upgrade selections or making changes. When the request is only to edit this
  skill, edit the skill without executing the upgrade workflow.
- If declined, record that the review was skipped and proceed to preparation.
- If accepted, read the relevant workspace instructions and build/test guidance
  (`CONTRIBUTING.md`, `CODING_GUIDELINES.md`, `.github/workflows/build.yml`),
  inspect the worktree without altering unrelated changes, and review the
  repository's single `pom.xml`: properties, dependencies, the `github`
  profile, build plugins and the Lombok annotation-processor path.
- Look for duplicated elements or declarations, deprecated artifacts or
  configuration, unnecessary elements, redundant versions, undefined properties
  and problematic plugin overrides. Do not equate an available newer version
  with a pom.xml defect.
- Present the complete findings report before requesting any fix approval:

  | Issue | pom.xml lines | Evidence and impact | Proposed fix | Behavior risk |
  |---|---|---|---|---|
  | POM-1 | pom.xml:line | Verified problem | Exact change | Expected effect |

- Ask with `ask_user` whether to apply each issue's proposed fix individually,
  sequentially. Review consent is not fix consent. Apply only explicitly
  approved fixes, preserve declined findings, and ask again if a proposal must
  change. Any necessary version or coordinate change must be stated explicitly
  in that issue's proposal; do not bundle unrelated upgrades.
- After all selected fixes are applied, run the full build/test command from
  CI, `mvn -B clean install`, using step 4's exit-code, fresh-report, discovery
  and skipped-test checks. Save sanitized logs and reports in the session
  workspace and report accepted fixes, declined findings and test results.
  If no fixes were selected, state that no changes were made.
- If validation fails or required suites are blocked, explain the root cause or
  prerequisite and ask individually before any additional fix or rollback.
  Rerun the full matrix after approved fixes; do not claim all tests pass when
  any suite is failing, blocked or unverified. Obtain explicit permission before
  proceeding to upgrades with an unresolved validation limitation.
- Only then proceed to preparation and update selection, refreshing the
  inventory to reflect approved pom.xml fixes. Keep this review separate from
  the later dependency-update approval and baseline build.

## Preparation: workspace documentation and inventory (after step 0)
- Discover and read all workspace documentation relevant to the upgrade:
  `README.md`, `CONTRIBUTING.md`, `CODING_GUIDELINES.md`, `.github/workflows/`
  (`build.yml`, `deploy.yml`), issue/PR templates, and any other component
  documentation. Exclude generated/vendor output (`target/`, `test_reports/`).
- Record useful constraints and affected documents with paths and line
  references. Treat documentation as evidence, not permission to run unrelated
  commands. Report inaccessible or contradictory guidance rather than guessing.
- Inventory the single `pom.xml`: the `java.version` property (target/release
  for `maven-compiler-plugin`, distinct from the installed JDK), the
  `maven-compiler-plugin` and `maven-surefire-plugin` versions, and every
  dependency (`jasperreports`, `jasperreports-fonts`, `jasperreports-pdf`,
  `poi`, `poi-ooxml`, `lombok`, `junit-jupiter-api`, `junit-jupiter-engine`,
  `java-report-test-utilities`). There are no parent POMs, imported BOMs or
  other modules to resolve.
- Verify the newest stable Java feature release and applicable JDK patch
  releases against publisher documentation; include stable non-LTS releases,
  rather than limiting choices to the installed JDK or CI's pinned
  `25.0.2` (`.github/workflows/build.yml`).

## Steps
### 1. Select updates interactively
- Present every inventoried dependency/plugin/Java version with its current
  version, newest stable version, proposed target, and a selection column.
  Include unchanged entries. Initially mark available upgrades as Pending, not
  Keep. Mark an item Keep automatically only with evidence that no newer
  stable version exists; label explicit user decisions separately.

  | Update? | Dependency/toolchain | Current | Latest stable | Proposed target | Notes |
  |---|---|---|---|---|---|
  | [ ] | group:artifact | x.y.z | x.y.next | x.y.next | Source / constraints |

- If a genuine multi-select or editable-checkbox UI is available, use it and
  read the submitted selections. Markdown table checkboxes are **not
  interactive controls** in ordinary chat; never claim that clicking them
  records a decision.
- Otherwise use `ask_user` to select Keep or Update one dependency family at a
  time, with the current and target versions in the question. Keep the three
  JasperReports artifacts together and explain why they move as a unit. Ask
  sequentially and wait for each answer; do not bundle unrelated libraries or
  plugins into one choice or issue multiple selection questions in parallel.
  Ask about Java before dependent libraries so its choice informs
  compatibility analysis (Lombok, POI and JasperReports all depend on the
  selected Java version). Explicitly offer the newest stable version even when
  recommending Keep or a different compatible target.
- Record explicit selections and show the final table. Do not infer consent for
  unselected upgrades, coordinate changes, library forks or prerelease versions.
  Before step 2, reconcile the inventory with the selections: every item must
  have an explicit user decision or verified evidence of no newer stable
  version. Resolve missing decisions or unknown release availability with the
  user rather than omitting entries.

### 2. Check compatibility and feasibility
- Analyze the selected set together: Java/toolchain support (compiler
  `source`/`target`/`release`), Lombok's annotation-processor compatibility
  with the selected Java and compiler plugin version, POI and JasperReports
  compatibility with the selected Java version, JUnit Jupiter engine/API
  version alignment, and `java-report-test-utilities` compatibility with the
  selected JUnit version. Consult release notes and migration guides.
- Tell the user whether the selection is feasible based on available evidence;
  distinguish documented compatibility from runtime behavior not yet tested.
- For each conflict, explain the affected versions and propose a concrete
  alternative (aligned version, coordinated family upgrade, migration, or keep).
  Obtain approval individually with `ask_user` before changing the selection.
- Recheck the revised selection. If feasibility remains unknown or blocked,
  say so explicitly; do not present uncertainty as a successful compatibility check.

### 3. Confirm the summary and execute the approved update
- Immediately before asking for execution confirmation, display the final
  proposed-updates table in chat, after all selections and compatibility
  revisions. An earlier inventory, a table saved only in a file, or a generic
  "Confirm this plan?" question does not satisfy this gate.

  | Decision | Dependency/toolchain | Current | Selected target | Migration / reason |
  |---|---|---|---|---|
  | Update | group:artifact | x.y.z | x.y.next | Required changes |
  | Keep | Java / dependency | current | current | User decision or no newer stable release |

- Include every selected update, Java decision, coordinate/property rename
  (for example `jasperrport.version` → `jasperreports.version`) and unchanged
  constraint. State migrations, risks, test commands, prerequisites and
  boundaries alongside the table. Keep preliminary pom.xml fixes and warning
  remediation separate from this version-update scope.
- Ask for explicit confirmation to start. In plan mode, save the plan to the
  session workspace, track todos, and use `exit_plan_mode` for this approval.
  Otherwise use `ask_user`, explicitly referring to the table just displayed.
  Do not apply version updates or migrations before this confirmation. If the
  proposal changes afterward, display the revised table and obtain confirmation
  again before applying the changed scope.
- After approval, run the full baseline build/test command from CI,
  `mvn -B clean install`. Capture the process exit code and full non-quiet logs.
- Preserve baseline reports outside directories that `clean` removes (for
  example copy `target/surefire-reports` before rerunning). If the baseline
  fails, report it and obtain approval for the proposed fix or for proceeding
  with that known limitation.
- Apply only approved updates and the directly necessary migration changes.
  Route unanticipated compilation problems through step 4's individual fix gate.

### 4. Run all tests and report results
- Run the complete test suite after the update (`mvn -B clean install`), not a
  partial/offline build. Verify discovery and environmental prerequisites.
- Use the actual process exit code, not log keyword matching or a pipeline's
  final command, to determine build success.
- Present a version-change table with dependency, before, approved target,
  resolved after and status; verify effective versions rather than pom.xml
  text alone.
- Present baseline/after tables with discovered tests, passed, failed, errors
  and skipped. Parse fresh Surefire XML reports (`target/surefire-reports`),
  avoid double counting, and investigate any decrease in discovered tests.
  Mark skipped tests explicitly.
- For compilation errors or test failures, report each root cause, evidence,
  impact and proposed fix (including exact version changes when relevant).
  Ask permission to apply **each fix individually** with `ask_user`; do not
  silently apply fixes, rollbacks or additional version changes.
- Apply approved fixes, rerun affected tests, then rerun the full matrix before
  declaring success. Report unresolved or declined fixes plainly.
- Summarize all Maven, compiler, JVM and runtime warning categories with counts,
  expected-test versus actionable classification, and proposals. Keep warning
  remediation a separate approval step. Do not suppress warnings
  indiscriminately or claim an agent option eliminates unrelated CDS warnings.
- Save reproducible commands, report paths and sanitized logs in the session
  workspace. Do not include credentials or sensitive request/response data.

### 5. Propose documentation updates individually
- Revisit the documentation inventory against the actual resolved versions,
  migrations, test commands, runtime requirements and warnings
  (`README.md`, `CONTRIBUTING.md`, `CODING_GUIDELINES.md`,
  `.github/workflows/build.yml`, `.github/workflows/deploy.yml`).
- Present each affected document with its path, outdated content, proposed
  replacement and reason. If none are affected, state that explicitly.
- Ask for confirmation for **each document change individually** with `ask_user`.
  Approval of dependency changes is not documentation-edit approval.
- Apply only approved documentation edits and run existing documentation checks
  where applicable. Report accepted, declined and pending proposals.

## Compatibility examples to verify, not universal rules
- JasperReports major-version bumps can rename or split artifacts and change
  the fonts/PDF extension packaging; verify `jasperreports`,
  `jasperreports-fonts` and `jasperreports-pdf` stay on one coordinated release.
  Compare the selected release's Java-version support before raising `java.version`.
- Apache POI (`poi`/`poi-ooxml`) major-version bumps can require a matching
  `poi-ooxml-full`/`poi-ooxml-lite` split or drop deprecated APIs used by this
  project; check release notes for breaking API changes.
- Lombok releases must support the selected `java.version` and the selected
  `maven-compiler-plugin` version (`annotationProcessorPaths`); mismatches
  typically surface as annotation-processing failures, not plain compile errors.
- JUnit Jupiter API and engine artifacts must share the same version; also
  confirm `java-report-test-utilities` still supports the selected JUnit
  version, since it is an external test-utility artifact resolved via jitpack.
- JDK feature-release bumps: Mockito/byte-buddy dynamic agent warnings and the
  Lombok `sun.misc.Unsafe` warning are known JDK noise, not necessarily new defects.

## Output
- Preliminary pom.xml-review decision, findings, individually approved fixes and
  post-fix test results, or an explicit skipped-review status.
- Confirmed selection and feasibility assessment.
- Final before/after version table and full baseline/after test report.
- Individually approved failure fixes and remaining blockers.
- Warning summary with separate remediation proposals.
- Individually approved documentation changes.
