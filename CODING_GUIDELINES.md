# Coding Guidelines

<!-- TOC -->

- [1. Introduction](#1-introduction)
    - [1.1. Terminology notes](#11-terminology-notes)
    - [1.2. Guide notes](#12-guide-notes)
- [2. Source file basics](#2-source-file-basics)
    - [2.1. File name](#21-file-name)
    - [2.2. File encoding: UTF-8](#22-file-encoding-utf-8)
    - [2.3. Special characters](#23-special-characters)
        - [2.3.1. Whitespace characters](#231-whitespace-characters)
        - [2.3.2. Special escape sequences](#232-special-escape-sequences)
        - [2.3.3. Non-ASCII characters](#233-non-ascii-characters)
- [3. Source file structure](#3-source-file-structure)
    - [3.1. License or copyright information, if present](#31-license-or-copyright-information-if-present)
    - [3.2. Package statement](#32-package-statement)
    - [3.3. Import statements](#33-import-statements)
        - [3.3.1. No wildcard imports](#331-no-wildcard-imports)
        - [3.3.2. No line-wrapping](#332-no-line-wrapping)
        - [3.3.3. Ordering and spacing](#333-ordering-and-spacing)
    - [3.4. Class declaration](#34-class-declaration)
        - [3.4.1. Exactly one top-level class declaration](#341-exactly-one-top-level-class-declaration)
        - [3.4.2. Class member ordering](#342-class-member-ordering)
            - [3.4.2.1. Overloads: never split](#3421-overloads-never-split)
- [4. Formatting](#4-formatting)
    - [4.1. Braces](#41-braces)
        - [4.1.1. Braces are used where optional](#411-braces-are-used-where-optional)
        - [4.1.2. Nonempty blocks: K &amp; R style](#412-nonempty-blocks-k-amp-r-style)
        - [4.1.3. Empty blocks: may be concise](#413-empty-blocks-may-be-concise)
    - [4.2. Block indentation: +4 spaces](#42-block-indentation-4-spaces)
    - [4.3. One statement per line](#43-one-statement-per-line)
    - [4.4. Column limit: 120](#44-column-limit-120)
    - [4.5. Line-wrapping](#45-line-wrapping)
        - [4.5.1. Where to break](#451-where-to-break)
        - [4.5.2. Indent continuation lines at least +4 spaces](#452-indent-continuation-lines-at-least-4-spaces)
    - [4.6. Whitespace](#46-whitespace)
        - [4.6.1. Vertical Whitespace](#461-vertical-whitespace)
        - [4.6.2. Horizontal whitespace](#462-horizontal-whitespace)
        - [4.6.3. Horizontal alignment: never required](#463-horizontal-alignment-never-required)    
    - [4.7. Grouping parentheses: recommended](#47-grouping-parentheses-recommended)
    - [4.8 Specific constructs](#48-specific-constructs)    
        - [4.8.1 Enum classes](#481-enum-classes)
        - [4.8.2. Variable declarations](#482-variable-declarations)
            - [4.8.2.1. One variable per declaration](#4821-one-variable-per-declaration)
            - [4.8.2.2. Declared when needed, initialized as soon as possible](#4822-declared-when-needed-initialized-as-soon-as-possible)
        - [4.8.3. Arrays](#483-arrays)
            - [4.8.3.1. Array initializers: can be "block-like"](#4831-array-initializers-can-be-block-like)
            - [4.8.3.2. No C-style array declarations](#4832-no-c-style-array-declarations)
        - [4.8.4. Switch statements](#484-switch-statements)
            - [4.8.4.1. Indentation](#4841-indentation)
            - [4.8.4.2. Fall-through: commented](#4842-fall-through-commented)
            - [4.8.4.3. The default case is present](#4843-the-default-case-is-present)
        - [4.8.5. Annotations](#485-annotations)
        - [4.8.6. Comments](#486-comments)
            - [4.8.6.1. Block comment style](#4861-block-comment-style)
        - [4.8.7. Modifiers](#487-modifiers)
        - [4.8.8. Numeric Literals](#488-numeric-literals)
- [5. Statements](#5-statements)
    - [5.1 Simple Statements](#51-simple-statements)
    - [5.2. Compound Statements](#52-compound-statements)    
        - [5.2.1. return Statements](#521-return-statements)
        - [5.2.2. if-else Statements](#522-if-else-statements)
        - [5.2.3. for Statements](#523-for-statements)
        - [5.2.4. while Statements](#524-while-statements)
        - [5.2.5. do-while Statements](#525-do-while-statements)
        - [5.2.6. switch Statements](#526-switch-statements)
        - [5.2.7. try-catch Statements](#527-try-catch-statements)
- [6. Naming](#6-naming)
    - [6.1 Rules common to all identifiers](#61-rules-common-to-all-identifiers)
    - [6.2. Rules by identifier type](#62-rules-by-identifier-type)
        - [6.2.1. Package names](#621-package-names)
        - [6.2.2. Class names](#622-class-names)
        - [6.2.3. Method names](#623-method-names)
        - [6.2.4. Constant names](#624-constant-names)
        - [6.2.5. Non-constant field names](#625-non-constant-field-names)
        - [6.2.6 Parameter names](#626-parameter-names)
        - [6.2.7. Local variable names](#627-local-variable-names)
        - [6.2.8. Type variable names](#628-type-variable-names)
    - [6.3. Camel case: defined](#63-camel-case-defined)
- [7. Unit-Tests](#7-unit-tests)
    - [7.1. Keeping Tests Clean](#71-keeping-tests-clean)
    - [7.2. F.I.R.S.T](#72-first)
    - [7.3. Unit-Test Smells](#73-unit-test-smells)
- [8. Design & Best Practices](#8-design--best-practices)
    - [8.1. Encapsulations](#81-encapsulations)
    - [8.2. Interfaces and Inheritances](#82-interfaces-and-inheritances)
    - [8.3. Structure over Convention](#83-structure-over-convention)
    - [8.4. Exceptions and Error Handlings](#84-exceptions-and-error-handlings)
    - [8.5. Constant interface pattern](#85-constant-interface-pattern)
    - [8.6. Concurrency](#86-concurrency)
- [9. Programming Practices](#9-programming-practices)
    - [9.1. Avoid multiple *return* statements](#91-avoid-multiple-return-statements)
    - [9.2. Boolean comparisons](#92-boolean-comparisons)
    - [9.3. *for* loops Vs *for-each* loops](#93-for-loops-vs-for-each-loops)
    - [9.4. *String* concatenation](#94-string-concatenation)
    - [9.5. Collections](#95-collections)
    - [9.6. Raw types](#96-raw-types)
    - [9.7. @Override: always used](#97-override-always-used)
    - [9.8. Caught exceptions: not ignored](#98-caught-exceptions-not-ignored)
    - [9.9. Static members: qualified using class](#99-static-members-qualified-using-class)
    - [9.10. Finalizers: not used](#910-finalizers-not-used)
    - [9.11. Final private fields, parameters, and local variables](#911-final-private-fields-parameters-and-local-variables)
- [10 Javadoc](#10-javadoc)
    - [10.1 Formatting](#101-formatting)
        - [10.1.1 General form](#1011-general-form)
        - [10.1.2 Paragraphs](#1012-paragraphs)
        - [10.1.3. At-clauses](#1013-at-clauses)
    - [10.2. The summary fragment](#102-the-summary-fragment)
    - [10.3. Where Javadoc is used](#103-where-javadoc-is-used)
        - [10.3.1. Exception: self-explanatory methods](#1031-exception-self-explanatory-methods)
        - [10.3.2. Exception: overrides](#1032-exception-overrides)

<!-- /TOC -->

 
## 1. Introduction

If you plan on contributing to java-report-generator, please start reading this document.

This document serves as a **complete** definition of  coding standards for
source code in the Java™ Programming Language. A Java source file is described as being _in
Google Style_ if and only if it adheres to the rules herein.

Like other programming style guides, the issues covered span not only aesthetic issues of
formatting, but other types of conventions or coding standards as well. However, this document
focuses primarily on the **hard-and-fast rules** that we follow universally, and
avoids giving _advice_ that isn't clearly enforceable (whether by human or tool).

We will check each of the coding guidelines during the review process, but knowing about them ahead of time will reduce the number of iterations.

### 1.1. Terminology notes

In this document, unless otherwise clarified:

1.  The term _class_ is used inclusively to mean an "ordinary" class, enum class,
    interface or annotation type (`@interface`).
2.  The term _comment_ always refers to _implementation_ comments. We do not
    use the phrase "documentation comments", instead using the common term "Javadoc."

Other "terminology notes" will appear occasionally throughout the document.


### 1.2. Guide notes

Example code in this document is **non-normative**. That is,  they may not illustrate the _only_ stylish way to represent the
code. Optional formatting choices made in examples should not be enforced as rules.


## 2. Source file basics


### 2.1. File name

The source file name consists of the case-sensitive name of the top-level class it contains,
plus the `.java` extension.


### 2.2. File encoding: UTF-8

Source files are encoded in **UTF-8**.


### 2.3. Special characters


#### 2.3.1. Whitespace characters

Aside from the line terminator sequence, the **ASCII horizontal space
character** (**0x20**) is the only whitespace character that appears
anywhere in a source file. This implies that:

1.  All other whitespace characters in string and character literals are escaped.
2.  Tab characters are **not** used for indentation.

#### 2.3.2. Special escape sequences

For any character that has a special escape sequence
(`\b`,
`\t`,
`\n`,
`\f`,
`\r`,
`\"`,
`\'` and
`\\`), that sequence
is used rather than the corresponding octal
(e.g. `\012`) or Unicode
(e.g. `\u000a`) escape.


#### 2.3.3. Non-ASCII characters

For the remaining non-ASCII characters, either the actual Unicode character
(e.g. `∞`) or the equivalent Unicode escape
(e.g. `\u221e`) is used, depending only on which
makes the code **easier to read and understand**.

**Tip:** In the Unicode escape case, and occasionally even when actual
Unicode characters are used, an explanatory comment can be very helpful.

Examples:
<table><tr><th>Example</th><th>Discussion</th></tr><tr><td>`String unitAbbrev = "μs";`</td><td>Best: perfectly clear even without a comment.</td></tr><tr><td>`String unitAbbrev = "\u03bcs"; // "μs"`</td><td>Allowed, but there's no reason to do this.</td></tr><tr><td>`String unitAbbrev = "\u03bcs";
      // Greek letter mu, "s"`</td><td>Allowed, but awkward and prone to mistakes.</td></tr><tr><td>`String unitAbbrev = "\u03bcs";`</td><td>Poor: the reader has no idea what this is.</td></tr><tr><td>`return '\ufeff' + content;
       // byte order mark`</td><td>Good: use escapes for non-printable characters, and comment if necessary.</td></tr></table>

**Tip:** Never make your code less readable simply out of fear that
some programs might not handle non-ASCII characters properly. If that should happen, those
programs are **broken** and they must be **fixed**.


## 3. Source file structure


A source file consists of, **in order**:

1.  License or copyright information, if present
2.  Package statement
3.  Import statements
4.  Exactly one top-level class

**Exactly one blank line** separates each section that is present.


### 3.1. License or copyright information, if present

If license or copyright information belongs in a file, it belongs here.


### 3.2. Package statement

The package statement is **not line-wrapped**. The column limit (Section 4.4,
[Column limit: 120](#s4.4-column-limit)) does not apply to package statements.


### 3.3. Import statements



#### 3.3.1. No wildcard imports

**Wildcard imports**, static or otherwise, **are not used**.


#### 3.3.2. No line-wrapping

Import statements are **not line-wrapped**. The column limit (Section 4.4,
[Column limit: 120](#s4.4-column-limit)) does not apply to import
statements.


#### 3.3.3. Ordering and spacing

Import statements are divided into the following groups, in this order, with each group
separated by a single blank line:

1. All otherwise-unmatched static imports in a single group
1.  `com.datalogics` imports
1. `com.adobe` imports
1.  Third-party imports, one group per top-level package, in ASCII sort order
    * for example: `com`, `junit`, `org`, `sun`
4.  `java` imports
5.  `javax` imports

Within a group there are no blank lines, and the imported names appear in ASCII sort
order. (**Note:** this is not the same as the import _statements_ being in
ASCII sort order; the presence of semicolons warps the result.)

The Eclipse project is set up to automatically organize imports according to these requirements.


### 3.4. Class declaration


#### 3.4.1. Exactly one top-level class declaration

Each top-level class resides in a source file of its own.


#### 3.4.2. Class member ordering

The ordering of the members of a class can have a great effect on learnability, but there is
no single correct recipe for how to do it. Different classes may order their members
differently.

What is important is that each class order its members in **_some_ logical
order**, which its maintainer could explain if asked. For example, new methods are not
just habitually added to the end of the class, as that would yield "chronological by date
added" ordering, which is not a logical ordering.


##### 3.4.2.1. Overloads: never split

When a class has multiple constructors, or multiple methods with the same name, these appear
sequentially, with no intervening members.


## 4. Formatting

**Terminology Note:** _block-like construct_ refers to
the body of a class, method or constructor. Note that, by Section 4.8.3.1 on
[array initializers](#s4.8.3.1-array-initializers), any array initializer
_may_ optionally be treated as if it were a block-like construct.


### 4.1. Braces


#### 4.1.1. Braces are used where optional

Braces are used with
`if`,
`else`,
`for`,
`do` and
`while` statements, even when the
body is empty or contains only a single statement.


#### 4.1.2. Nonempty blocks: K &amp; R style

Braces follow the Kernighan and Ritchie style
("[Egyptian brackets](http://www.codinghorror.com/blog/2012/07/new-programming-jargon.html)")
for _nonempty_ blocks and block-like constructs:

*   Line break before the opening brace.
*   Line break after the opening brace.
*   Line break before the closing brace.
*   Line break after the closing brace _if_ that brace terminates a statement or the body
    of a method, constructor or _named_ class. For example, there is _no_ line break
    after the brace if it is followed by  a
    comma.

Example:
````
return new MyClass() {
  @Override public void method() 
  {
    if (condition()) 
    {
      try 
      {
        something();
      } 
      catch (ProblemException e) 
      {
        recover();
      }
    }
  }
};
````

A few exceptions for enum classes are given in Section 4.8.1,
[Enum classes](#s4.8.1-enum-classes).


#### 4.1.3. Empty blocks: may be concise

An empty block or block-like construct _may_ be closed immediately after it is
opened, with no characters or line break in between
(`{}`), **unless** it is part of a
_multi-block statement_ (one that directly contains multiple blocks:
`if/else-if/else` or
`try/catch/finally`).

Example:
````
  void doNothing() {}
````

### 4.2. Block indentation: +4 spaces

Each time a new block or block-like construct is opened, the indent increases by four
spaces. When the block ends, the indent returns to the previous indent level. The indent level
applies to both code and comments throughout the block. (See the example in Section 4.1.2,
[Nonempty blocks: K &amp; R Style](#s4.1.2-blocks-k-r-style).)


### 4.3. One statement per line

Each statement is followed by a line-break.


### 4.4. Column limit: 120

Projects should use a column limit of 120 characters.

Except as noted below, any line that would exceed this limit must be line-wrapped, as explained in
Section 4.5, [Line-wrapping](#s4.5-line-wrapping).

**Exceptions:**

1.  Lines where obeying the column limit is not possible (for example, a long URL in Javadoc,
    or a long JSNI method reference).
2.  `package` and
    `import` statements (see Sections
    3.2 [Package statement](#s3.2-package-statement) and
    3.3 [Import statements](#s3.3-import-statements)).
3.  Command lines in a comment that may be cut-and-pasted into a shell.

### 4.5. Line-wrapping

**Terminology Note:** When code that might otherwise legally
occupy a single line is divided into multiple lines, typically to avoid overflowing the column
limit, this activity is called
_line-wrapping_.

There is no comprehensive, deterministic formula showing _exactly_ how to line-wrap in
every situation. Very often there are several valid ways to line-wrap the same piece of code.

**Tip:** Extracting a method or local variable may solve the problem without the need to line-wrap.


#### 4.5.1. Where to break

The prime directive of line-wrapping is: prefer to break at a
**higher syntactic level**. Also:

1.  When a line is broken at a _non-assignment_ operator the break comes _before_
    the symbol.
*   This also applies to the following "operator-like" symbols: the dot separator
    (`.`), the ampersand in type bounds
    (`<T extends Foo & Bar>`), and the pipe in
    catch blocks
    (`catch (FooException | BarException e)`).
2.  When a line is broken at an _assignment_ operator the break typically comes
    _after_ the symbol, but either way is acceptable.
*   This also applies to the "assignment-operator-like" colon in an enhanced
    `for` ("foreach") statement.
3.  A method or constructor name stays attached to the open parenthesis
    (`(`) that follows it.
4.  A comma (`,`) stays attached to the token that
    precedes it.

#### 4.5.2. Indent continuation lines at least +4 spaces

When line-wrapping, each line after the first (each _continuation line_) is indented
at least +4 from the original line.

When there are multiple continuation lines, indentation may be varied beyond +4 as
desired. In general, two continuation lines use the same indentation level if and only if they
begin with syntactically parallel elements.

Section 4.6.3 on [Horizontal alignment](#s4.6.3-horizontal-alignment) addresses
the discouraged practice of using a variable number of spaces to align certain tokens with
previous lines.


### 4.6. Whitespace


#### 4.6.1. Vertical Whitespace

A single blank line appears:

1.  _Between_ consecutive members (or initializers) of a class: fields, constructors,
    methods, nested classes, static initializers, instance initializers.
*   <span class="exception">**Exception:** A blank line between two consecutive
    fields (having no other code between them) is optional. Such blank lines are used as needed to
    create _logical groupings_ of fields.</span>
2.  Within method bodies, as needed to create _logical groupings_ of statements.
3.  _Optionally_ before the first member or after the last member of the class (neither
    encouraged nor discouraged).
4.  As required by other sections of this document (such as Section 3.3,
    [Import statements](#s3.3-import-statements)).

_Multiple_ consecutive blank lines are permitted, but never required (or encouraged).


#### 4.6.2. Horizontal whitespace

Beyond where required by the language or other style rules, and apart from literals, comments and
Javadoc, a single ASCII space also appears in the following places **only**.

1.  Separating any reserved word, such as
    `if`,
    `for` or
    `catch`, from an open parenthesis
    (`(`)
    that follows it on that line
2.  Separating any reserved word, such as
    `else` or
    `catch`, from a closing curly brace
    (`}`) that precedes it on that line
3.  Before any open curly brace
    (`{`), with two exceptions:
*   `@SomeAnnotation({a, b})` (no space is used)
*   `String[][] x = {{"foo"}};` (no space is required
    between `{{`, by item 8 below)
4.  On both sides of any binary or ternary operator. This also applies to the following
    "operator-like" symbols:
*   the ampersand in a conjunctive type bound:
    `<T extends Foo & Bar>`
    *   the pipe for a catch block that handles multiple exceptions:
        `catch (FooException | BarException e)`
    *   the colon (`:`) in an enhanced
        `for` ("foreach") statement
5.  After `,:;` or the closing parenthesis
    (`)`) of a cast
6.  On both sides of the double slash (`//`) that
    begins an end-of-line comment. Here, multiple spaces are allowed, but not required.
7.  Between the type and variable of a declaration:
    `List<String> list`
8.  _Optional_ just inside both braces of an array initializer
*   `new int[] {5, 6}` and
    `new int[] { 5, 6 }` are both valid

**Note:** This rule never requires or forbids additional space at the
start or end of a line, only _interior_ space.


#### 4.6.3. Horizontal alignment: never required

**Terminology Note:** _Horizontal alignment_ is the
practice of adding a variable number of additional spaces in your code with the goal of making
certain tokens appear directly below certain other tokens on previous lines.


Here is an example without alignment, then using alignment:
````
private int x; // this is fine
private Color color; // this too

private int   x;      // permitted, but future edits
private Color color;  // may leave it unaligned
````

**Tip:** Alignment can aid readability, but it creates problems for
future maintenance.  Consider a future change that needs to touch just one line. This change may
leave the formerly-pleasing formatting mangled, and that is **allowed**. More often
it prompts the coder (perhaps you) to adjust whitespace on nearby lines as well, possibly
triggering a cascading series of reformattings. That one-line change now has a "blast radius."
This can at worst result in pointless busywork, but at best it still corrupts version history
information, slows down reviewers and exacerbates merge conflicts.


### 4.7. Grouping parentheses: recommended

Optional grouping parentheses are omitted only when author and reviewer agree that there is no
reasonable chance the code will be misinterpreted without them, nor would they have made the code
easier to read. It is _not_ reasonable to assume that every reader has the entire Java
operator precedence table memorized.

### 4.8. Specific constructs


#### 4.8.1. Enum classes

After each comma that follows an enum constant, a line-break is optional.

An enum class with no methods and no documentation on its constants may optionally be formatted
as if it were an array initializer (see Section 4.8.3.1 on
[array initializers](#s4.8.3.1-array-initializers)).
````
private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
````

Since enum classes _are classes_, all other rules for formatting classes apply.


#### 4.8.2. Variable declarations


##### 4.8.2.1. One variable per declaration

Every variable declaration (field or local) declares only one variable: declarations such as
`int a, b;` are not used.


##### 4.8.2.2. Declared when needed, initialized as soon as possible

Local variables are **not** habitually declared at the start of their containing
block or block-like construct. Instead, local variables are declared close to the point they are
first used (within reason), to minimize their scope. Local variable declarations typically have
initializers, or are initialized immediately after declaration.


#### 4.8.3. Arrays


##### 4.8.3.1. Array initializers: can be "block-like"

Any array initializer may _optionally_ be formatted as if it were a "block-like
construct." For example, the following are all valid (**not** an exhaustive
list):
````
new int[] {           new int[] {
  0, 1, 2, 3            0,
}                       1,
                        2,
new int[] {             3,
  0, 1,               }
  2, 3
}                     new int[]
                          {0, 1, 2, 3}
````

##### 4.8.3.2. No C-style array declarations

The square brackets form a part of the _type_, not the variable:
`String[] args`, not
`String args[]`.


#### 4.8.4. Switch statements

**Terminology Note:** Inside the braces of a
_switch block_ are one or more _statement groups_. Each statement group consists of
one or more _switch labels_ (either `case FOO:` or
`default:`), followed by one or more statements.

##### 4.8.4.1. Indentation

As with any other block, the contents of a switch block are indented +4.

After a switch label, a newline appears,  exactly as
if a block were being opened. The following switch label returns to the previous indentation
level, as if a block had been closed.


##### 4.8.4.2. Fall-through: commented

Within a switch block, each statement group either terminates abruptly (with a
`break`,
`continue`,
`return` or thrown exception), or is marked with a comment
to indicate that execution will or _might_ continue into the next statement group. Any
comment that communicates the idea of fall-through is sufficient (typically
`// fall through`). This special comment is not required in
the last statement group of the switch block. Example:
````
switch (input) 
{
  case 1:
  case 2:
    prepareOneOrTwo();
    // fall through
  case 3:
    handleOneTwoOrThree();
    break;
  default:
    handleLargeNumber(input);
}
````

##### 4.8.4.3. The default case is present

Each switch statement includes a `default` statement
group, even if it contains no code.


#### 4.8.5. Annotations

Annotations applying to a class, method or constructor appear immediately after the
documentation block, and each annotation is listed on a line of its own (that is, one annotation
per line). These line breaks do not constitute line-wrapping (Section
4.5, [Line-wrapping](#s4.5-line-wrapping)), so the indentation level is not
increased. Example:
````
@Override
@Nullable
public String getNameIfPresent() { ... }
````

**Exception:** A _single_ parameterless annotation
_may_ instead appear together with the first line of the signature, for example:
````
@Override public int hashCode() { ... }
````

Annotations applying to a field also appear immediately after the documentation block, but in
this case, _multiple_ annotations (possibly parameterized) may be listed on the same line;
for example:
````
@Partial @Mock DataLoader loader;
````

There are no specific rules for formatting parameter and local variable annotations.


#### 4.8.6. Comments


##### 4.8.6.1. Block comment style

Block comments are indented at the same level as the surrounding code. They may be in
`/* ... */` style or
`// ...` style. For multi-line
`/* ... */` comments, subsequent lines must start with
`*` aligned with the `*` on the previous line.
````
/*
 * This is          // And so           /* Or you can
 * okay.            // is this.          * even do this. */
 */
````

Comments are not enclosed in boxes drawn with asterisks or other characters.

**Tip:** When writing multi-line comments, use the
`/* ... */` style if you want automatic code formatters to
re-wrap the lines when necessary (paragraph-style). Most formatters don't re-wrap lines in
`// ...` style comment blocks.


#### 4.8.7. Modifiers

Class and member modifiers, when present, appear in the order
recommended by the Java Language Specification:

````
public protected private abstract static final transient volatile synchronized native strictfp
````

#### 4.8.8. Numeric Literals

`long`-valued integer literals use an uppercase `L` suffix, never
lowercase (to avoid confusion with the digit `1`). For example, `3000000000L`
rather than `3000000000l`.

## 5. Statements

### 5.1. Simple Statements

Each line should contain at most one statement. Example:

````
// DO
count++;
count--;

// DON'T
count++; count--;
````

### 5.2. Compound Statements

Compound statements are statements that contain lists of statements enclosed in braces:

````
{
  <statement>
}
````

* The enclosed statements should be indented one more level than the compound statement.
* Braces are used around all statements, even single code-line statements, when they are part of a control structure, such as a if-else or for statement. This makes it easier to add statements without accidentally introducing bugs due to forgetting to add braces.

### 5.2.1. return Statements

A return statement with a value should not use parentheses unless they make the return value more obvious in some way. Example:

````
return 1;
return products.size();
return (size < 10 ? size : DEFAULT_SIZE);
````

### 5.2.2. if-else Statements

The if-else class of statements should have the following form:

````
if (condition1)
{
  <statements>
} 
else if (condition2)
{
  <statements>
} 
else 
{
  <statements>
}

return 1;
````

**if** statements always use braces {}.
Positive conditionals are easier to read than negative conditionals.

### 5.2.3. for Statements

A for statement should have the following form:

````
for (<initialization>; <condition>; <update>)
{
  <statements>
}
````

An empty for statement (one in which all the work is done in the initialization, condition, and update clauses) should have the following form:

````
for (<initialization>; <condition>; <update>);
````

When using the comma operator in the initialization or update clause of a **for** statement, avoid the complexity of using more than three variables. If needed, use separate statements before the **for** loop (for the initialization clause) or at the end of the loop (for the update clause).

### 5.2.4. while Statements

A **while** statement should have the following form:

````
while (<condition>) 
{
  <statements>
}
````

An empty while statement should have the following form:

````
while (<condition>);
````

### 5.2.5. do-while Statements

A **do-while** statement should have the following form:

````
do 
{
  <statements>
} 
while (<condition>);
````

### 5.2.6. switch Statements

A **switch** statement should have the following form:

````
switch (status)
{
  case ABC:
    <statements>
    /* falls through */
  case DEF:
    <statements>
    break;
  case XYZ:
    <statements>
    break;
  default:
    <statements>
    break;
}
````

Every time a case falls through (doesn’t include a **break** statement), add a comment where the **break** statement would normally be. This is shown in the preceding code example with the /\* falls through \*/ comment. Every **switch** statement should include a **default** case. The **break** in the **default** case is redundant, but it prevents a fall-through error if later another case is added.

### 5.2.7. try-catch Statements

A **try-catch** statement should have the following format:

````
try
{
  <statements>
}
catch (Exception e)
{
  <statements>
}
````

A **try-catch** statement may also be followed by **finally**, which executes regardless of whether or not the try block has completed successfully:

````
try 
{
  <statements>
}
catch (Exception e) 
{
  <statements>
} 
finally 
{
  <statements>
}
````


## 6. Naming


### 6.1. Rules common to all identifiers

Identifiers use only ASCII letters and digits, and in two cases noted below, underscores. Thus
each valid identifier name is matched by the regular expression `\w+` .

Special prefixes or
suffixes, like those seen in the examples `name_`,
`mName`, `s_name` and
`kName`, are **not** used.


### 6.2. Rules by identifier type


#### 6.2.1. Package names

Package names are all lowercase, with consecutive words simply concatenated together (no
underscores). For example, `com.example.deepspace`, not
`com.example.deepSpace` or
`com.example.deep_space`.


#### 6.2.2. Class names

Class names are written in [UpperCamelCase](#s5.3-camel-case).

Class names are typically nouns or noun phrases. For example,
`Character` or
`ImmutableList`. Interface names may also be nouns or
noun phrases (for example, `List`), but may sometimes be
adjectives or adjective phrases instead (for example,
`Readable`).

There are no specific rules or even well-established conventions for naming annotation types.

_Test_ classes are named starting with the name of the class they are testing, and ending
with `Test`. For example,
`HashTest` or
`HashIntegrationTest`.


#### 6.2.3. Method names

Method names are written in [lowerCamelCase](#s5.3-camel-case).

Method names are typically verbs or verb phrases. For example,
`sendMessage` or
`stop`.

Underscores may appear in JUnit _test_ method names to separate logical components of the
name. One typical pattern is `test_<MethodUnderTest>___<state>_`,
for example `testPop_emptyStack`. There is no One Correct
Way to name test methods.


#### 6.2.4. Constant names

Constant names use `CONSTANT_CASE`: all uppercase
letters, with words separated by underscores. But what _is_ a constant, exactly?

Every constant is a static final field, but not all static final fields are constants. Before
choosing constant case, consider whether the field really _feels like_ a constant. For
example, if any of that instance's observable state can change, it is almost certainly not a
constant. Merely _intending_ to never mutate the object is generally not
enough. Examples:
````
// Constants
static final int NUMBER = 5;
static final ImmutableList&lt;String&gt; NAMES = ImmutableList.of("Ed", "Ann");
static final Joiner COMMA_JOINER = Joiner.on(',');  // because Joiner is immutable
static final SomeMutableType[] EMPTY_ARRAY = {};
enum SomeEnum { ENUM_CONSTANT }

// Not constants
static String nonFinal = "non-final";
final String nonStatic = "non-static";
static final Set&lt;String&gt; mutableCollection = new HashSet&lt;String&gt;();
static final ImmutableSet&lt;SomeMutableType&gt; mutableElements = ImmutableSet.of(mutable);
static final Logger logger = Logger.getLogger(MyClass.getName());
static final String[] nonEmptyArray = {"these", "can", "change"};
````

These names are typically nouns or noun phrases.


#### 6.2.5. Non-constant field names

Non-constant field names (static or otherwise) are written
in [lowerCamelCase](#s5.3-camel-case).

These names are typically nouns or noun phrases.  For example,
`computedValues` or
`index`.


#### 6.2.6 Parameter names

Parameter names are written in [lowerCamelCase](#s5.3-camel-case).

One-character parameter names should be avoided.


#### 6.2.7. Local variable names

Local variable names are written in [lowerCamelCase](#s5.3-camel-case), and can be
abbreviated more liberally than other types of names.

However, one-character names should be avoided, except for temporary and looping variables.

Even when final and immutable, local variables are not considered to be constants, and should not
be styled as constants.


#### 6.2.8. Type variable names

Each type variable is named in one of two styles:

*   A single capital letter, optionally followed by a single numeral (such as
    `E`, `T`,
    `X`, `T2`)
*   A name in the form used for classes (see Section 5.2.2,
    [Class names](#s5.2.2-class-names)), followed by the capital letter
    `T` (examples:
    `RequestT`,
    `FooBarT`).

### 6.3. Camel case: defined

Sometimes there is more than one reasonable way to convert an English phrase into camel case,
such as when acronyms or unusual constructs like "IPv6" or "iOS" are present. To improve
predictability, Google Style specifies the following (nearly) deterministic scheme.

Beginning with the prose form of the name:

1.  Convert the phrase to plain ASCII and remove any apostrophes. For example, "Müller's
    algorithm" might become "Muellers algorithm".
2.  Divide this result into words, splitting on spaces and any remaining punctuation (typically
    hyphens).

*   _Recommended:_ if any word already has a conventional camel-case appearance in common
    usage, split this into its constituent parts (e.g., "AdWords" becomes "ad words"). Note
    that a word such as "iOS" is not really in camel case _per se_; it defies _any_
    convention, so this recommendation does not apply.
3.  Now lowercase _everything_ (including acronyms), then uppercase only the first
    character of:
*   ... each word, to yield _upper camel case_, or
*   ... each word except the first, to yield _lower camel case_
4.  Finally, join all the words into a single identifier.

Note that the casing of the original words is almost entirely disregarded. Examples:
<table><tr><th>Prose form</th><th>Correct</th><th>Incorrect</th></tr><tr><td>"XML HTTP request"</td><td>`XmlHttpRequest`</td><td>`XMLHTTPRequest`</td></tr><tr><td>"new customer ID"</td><td>`newCustomerId`</td><td>`newCustomerID`</td></tr><tr><td>"inner stopwatch"</td><td>`innerStopwatch`</td><td>`innerStopWatch`</td></tr><tr><td>"supports IPv6 on iOS?"</td><td>`supportsIpv6OnIos`</td><td>`supportsIPv6OnIOS`</td></tr><tr><td>"YouTube importer"</td><td>`YouTubeImporter`
`YoutubeImporter`*</td></tr></table>

*Acceptable, but not recommended.

**Note:** Some words are ambiguously hyphenated in the English
language: for example "nonempty" and "non-empty" are both correct, so the method names
`checkNonempty` and
`checkNonEmpty` are likewise both correct.

## 7. Unit-Tests

Tests are as important to the health of a project as the production code is. Perhaps they are even more important, because tests preserve and enhance the ﬂexibility, maintainability, and reusability of the production code. So keep your tests constantly clean.

### 7.1. Keeping Tests Clean

* Unit-Test should not be maintained to the same standards of quality as their production code.
* Having dirty tests is better than having no tests.
* Test code is just as important as production code
* Use the SetUp / TearDown methods only for infrastructure that your unit-test needs. Do not use it for anything that is under test.
* Clean Test: Builds up the test data (Given). The second part operates on that test data (When). Checks that the operation yielded the expected results. (Then)
* Single Scenario per Test

````
public void testGetPageHierarchyAsXml() throws Exception 
{
  givenPages("PageOne", "PageOne.ChildOne", "PageTwo");
  whenRequestIsIssued("root", "type:pages");
  thenResponseShouldBeXML();
}

public void testGetPageHierarchyHasRightTags() throws Exception 
{
  givenPages("PageOne", "PageOne.ChildOne", "PageTwo");
  whenRequestIsIssued("root", "type:pages");
  thenResponseShouldContain("PageOne", "PageTwo", "ChildOne");
}
````

### 7.2. F.I.R.S.T

**F**ast: Unit-tests have to be fast in order to be executed often. Fast means much smaller than seconds.

**I**ndependent: Clear when the failure happened. No dependency between tests (random order).

**R**epeatable: No assumed initial state, nothing left behind, no dependency on external services that might be unavailable (databases, file system …)

**S**elf-validating: No manual test interpretation or intervention. Red or green!

**T**imely: Tests are written at the right time. TDD (Test Driven Development): Red – green – refactor, test a little – code a little. DDT (Defect Driven Testing): Write a unit test that reproduces the defect – Fix code – Test will succeed – Defect will never return. POUTing (Plain Old Unit Testing, aka. Test After): Write unit tests to check existing code, you cannot and probably do not want to test drive everything.

### 7.3. Unit-Test Smells

**Test Not Testing Anything**: Passing test that at first sight appears valid but does not test the testee.

**Checking Internals**: A test that accesses internals (private/protected members) of the testee directly (Reflection). This is a refactoring killer.

**Test Only Running on Developer’s Machine**: A test that is dependent on the development environment and fails elsewhere. Use continuous integration to catch them as soon as possible.

**Test Checking More than Necessary**: A test that checks more than it is dedicated to. The test fails whenever something changes that it checks unnecessarily. Especially probable when fakes are involved or checking for item order in unordered collections.

**Chatty Test**: A test that fills the console with text – probably used once to manually check for something.

**Test Swallowing Exceptions**: A test that catches exceptions and lets the test pass.

**Test Not Belonging in Host Test Fixture**: A test that tests a completely different testee than all other tests in the fixture.

**Hidden Test Functionality**: Test functionality hidden in either the SetUp method, base class or helper class. The test should be clear by looking at the test method only – no initialisation or asserts somewhere else.

**Conditional Test Logic**: Tests should not have any conditional test logic because it’s hard to read.

**Test Logic in Production Code**: Tests depend on special logic in production code.

**Erratic Test**: Sometimes passes, sometimes fails due to left overs or environment.

## 8. Design & Best Practices

For some good Object-oriented code design guidelines, see the Gang of Four book or the ObjectMentor website: http://www.objectmentor.com/

### 8.1. Encapsulations

It’s important to keep data and behavior as encapsulated as possible to reduce dependencies and therefore increase the maintainability of the system. These following guidelines should be considered in your design to increase encapsulation:

* Aim to make all of your instance variables private and provide accessor methods only where necessary.
* Make accessor methods for instance variables “final”.
* Only use protected instance variables or protected constructors in well-defined packages
* Use packages to manage complexity
* If a class is only used within a package, make it package local (default visibility) to reduce system-level coupling.
* Avoid using inner classes, an inner class only makes sense, and should only be used, if it is going to associate and be visible only to the class that contains it.

### 8.2. Interfaces and Inheritances

A well-designed system is set with assignment well-defined responsibilities and interfaces. The interface defines the “contract” that a class should meet and it’s essential to the development of large systems. The interface concept can be implemented in different ways, like abstract classes or actual Java interfaces. Most people tend to abuse of the use of inheritance when what they really needed was interfaces. Creating deep class hierarchies generate large dependencies and therefore less maintainability. Below some guidelines:

* Keep inheritance hierarchies small
* Prefer delegation or using utility classes over inheritance for reuse
* Program to an interface, not to an implementation
* If a class is designed to be inherited, but it does not make sense to have an instance of the class, it should be defined as abstract

### 8.3. Structure over Convention

Enforce design decisions with structure over convention. Naming conventions are good, but there are inferior to structures that force compliance.

### 8.4. Exceptions and Error Handlings

The general philosophy is to use exceptions only for errors: logic and programming errors, configuration errors, corrupted data, resource exhaustion, etc. The general rule is that the systems in normal condition and in the absence of overload or hardware failure should not raise any exceptions.  Keep in mind that throwing an exception is a very expensive operation in Java. Unwinding the stack and forming String objects requires many CPU cycles. Never program by exceptions. If you can anticipate a situation, include it in your code rather than using an exception to signal it.

Avoid putting more than one **try/catch** block in a method. In general, all methods should follow this form:

````
public void doSomething() throws SomeException 
{
  <initializations>
  try 
  {
    <code that throws exceptions>
  } 
  catch (SomeException e)
  {
    throw e;
  } 
  catch (SomeOtherException e)
  {
    <create a new SomeException se from e>
    throw se;
  }
  catch (Exception e) 
  {
    <create a new SomeExcention se from e>
    throw se;
  }
  finally
  {
    <cleanup>
  }
}
````

If you find that you need to nest try/catch blocks, consider encapsulating the nested block in a new method that handles the special exception.

**Exception Factory**

Every Application should have an exception handling framework. You should use a message ID to create exceptions within the code. Never use the new operator to create an exception.

Here is the procedure you should follow when creating an exception

* Decide on an appropriate message and exception class. You may subclass Exception or use it directly.
* Check the existing exception hierarchy and message catalog to see if an appropriate exception/message pair already exists.
* If an appropriate exception doesn’t already exist, check out the message catalog and add a new message/exception class pair. You may choose an appropriate string identifier for your message that is descriptive. If you need to pass additional static parameters to your exception constructor, put them in the message catalog as well.
* If necessary, write the new exception class.
* In your code, use an ExceptionFactory to generate the new exception. You may include an exception to be nested and any special purpose properties when creating this exception.

**Additional Exception Guidelines**

* Never, ever, consume an exception without taking an action. This form is unacceptable:

````
try 
{
  <code that throws exceptions>
} catch (SomeException e) 
{
  // do nothing
}
````

* Use exceptions to handle logic and programming errors, configuration errors, corrupted data, and resource exhaustion.

````
// DON'T 
if (doSomething()) 
{ 
  if (doSomethingElse()) 
  { 
    if (doSomethingElseAgain()) 
    { 
      // ... 
    } 
    else 
    {
      // handle doSomethingElseAgain failed 
    }
  } 
  else 
  { 
    // handle doSomethingElse failed 
  } 
} 
else 
{ 
  // handle doSomething failed
}
````

````
// D0
try 
{
  doSomething();
  doSomethingElse();
  doSomethingElseAgain();
} 
catch (SomethingException e) 
{
  // handle doSomething failed
} 
catch (SomethingElseException e) 
{
  // handle doSomethingElse failed
} 
catch (SomethingElseAgainException e) 
{
  // handle doSomethingElseAgain failed
}
````

* Report exceptions by the appropriate logging mechanism as early as possible, including at the point of raise.
* Minimize the number of exceptions exported from a given abstraction by categorizing them and using a constant (typesafe) to represent the condition
* Do not use exceptions for frequent, anticipated events.
* Do not use exceptions to implement control structures.
* Make sure status codes have an appropriate value.
* Perform safety checks locally; do not expect your client to do so.
* Catch as many exceptions as possible explicitly – avoid catch(Exception) as the only exception handler
* Separate fatal and non-fatal exception hierarchies
* Never let exceptions propagate out a finally block
* Never declare throws Exception, always use a subclass of the base Application Exception.

### 8.5. Constant interface pattern

Avoid declaring constant variables in an interface

````
public interface UserService 
{
  String SYSTEM_USERNAME = "system";
  int AWAITING_QUEUE_SIZE = 20;

  int AWAITING_APPROVAL_STATUS = 1;
  int ACTIVE_SIATUS = 2;
  int LOCKED_SIATUS = 3;

  void approveUser(String username);

  int getUserStatus();
}
````

An **enum** may be a better approach. Or you could simply put the constants as public static fields in a class that cannot be instantiated

````
public final class UserConstants 
{
  public static final String SYSTEM_USERNAME = "system";
  public static final int AWAITING_QUEUE_SIZE = 20;

  private UserConstants() 
  {
  }
}

public enum UserStatus 
{
  AWAITING_APPROVAL,
  ACTIVE,
  LOCKED
}
````

### 8.6. Concurrency

What follows is a series of principles and techniques for defending your systems from the problems of concurrent code:

* Keep your concurrency-related code separate from other code.
* Limit the scope of data. Take data encapsulation to heart; severely limit the access of any data that may be shared.
* Use Copies of Data. A good way to avoid shared data is to avoid sharing the data in the ﬁrst place. In some situations it is possible to copy objects and treat them as read-only. In other cases it might be possible to copy objects, collect results from multiple threads in these copies and then merge the results in a single thread.
* Threads should be as independent as possible. Attempt to partition data into independent subsets than can be operated on by independent threads, possibly in different processors.
* Review the classes available to you. In the case of Java 5, become familiar with **java.util.concurrent**, **java.util.concurrent.atomic**, **java.util.concurrent.locks**.
* Keep your synchronized sections as small as possible.
* Write tests that have the potential to expose problems and then run them frequently, with different programmatic conﬁgurations and system conﬁgurations and load. If tests ever fail, track down the failure. Don’t ignore a failure just because the tests pass on a subsequent run.


## 9. Programming Practices

Pay special attention to the most common style errors...

* classes too long
* methods too long
* little or no javadoc comments
* swallow exceptions
* multiple *return* statements
* Overuse of arrays in place of collections
* too much or no whitespace


### 9.1. Avoid multiple *return* statements

Multiple *return* statements are hard and time consuming to debug.

**Correct:**

````
 public class StringUtils 
 {
   public static boolean isEmpty(String string) 
   {
     return string == null || "".equals(string.trim());
   }
 }

or

public class StringUtils 
{
  public static boolean isEmpty(String string) 
  {
    boolean empty = false;
    if (string == null) 
    {
      empty = true;
    } 
      else if ("".equals(string.trim())) 
    {
      empty = true;
    }
      return empty;
    }
 }
````

**Incorrect:**

````
 public class StringUtils 
 {
    public static boolean isEmpty(String string) 
    {
    if (string == null) 
    {
      return true;
    } 
    else if ("".equals(string.trim())) 
    {
      return true;
    }
    return false;
    }
 }
````

### 9.2. Boolean comparisons

Mirroring the natural language "if the current state is not active" rather than "if active is not the current state"

**Correct:**

````
	!active
````

**Incorrect:**

````
	active == false
````

### 9.3. *for* loops Vs *for-each* loops

When iterating over iterable elements where the current index in the iteration is not important for-each loops are preferred. **Not compatible for JDK 1.4 **

**Correct:**

````
for (String name: names) 
{
  doStuff(name);
}
````

**Incorrect:**

````
for (int i = 0; i < names.length; i++) 
{
  doStuff(names[i]);
}	
````

### 9.4. *String* concatenation

Avoid the use of + or += to concatenate strings. Use java standards designed for that purposes such as String.format, StringBuilder, etc.  If you are doing thread safe operation then use StringBuilder instead of StringBuffer.

**Correct:**

````
log.debug(String.format("found %s items", amount));
````

**Incorrect:**

````
log.debug("found " + amount + " items");	
````

### 9.5. Collections

Use the right collections for the right task.

**Duplicates**

* Allows duplicates: [List](http://docs.oracle.com/javase/8/docs/api/java/util/List.html)
* Does Not Allow Duplicates: [Set](http://docs.oracle.com/javase/8/docs/api/java/util/Set.html), [Map](http://docs.oracle.com/javase/8/docs/api/java/util/Map.html)

**Implementations Iteration Order**

* [HashSet](http://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html) - undefined
* [ConcurrentHashMap](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ConcurrentHashMap.html) - undefined
* [HashMap](http://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) - undefined
* [LinkedHashSet](http://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html) - insertion order
* [LinkedHashMap](http://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html) - insertion order of keys
* [ArrayList](http://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html) - insertion order
* [LinkedList](http://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html) - insertion order
* [TreeSet](http://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html) - ascending order (Comparable / Comparator)

### 9.6. Raw types

Avoid using raw types when using classes that support generics.

**Correct:**

````
List<String> people = Arrays.asList("you", "me");
````

**Incorrect:**

````
List people = Arrays.asList("you", "me");
````

### 9.7. @Override: always used

A method is marked with the `@Override` annotation
whenever it is legal.  This includes a class method overriding a superclass method, a class method
implementing an interface method, and an interface method respecifying a superinterface
method.

**Exception:**`@Override` may be omitted when the parent method is
`@Deprecated`.


### 9.8. Caught exceptions: not ignored

Except as noted below, it is very rarely correct to do nothing in response to a caught
exception. (Typical responses are to log it, or if it is considered "impossible", rethrow it as an
`AssertionError`.)

When it truly is appropriate to take no action whatsoever in a catch block, the reason this is
justified is explained in a comment.
````
try 
{
  int i = Integer.parseInt(response);
  return handleNumericResponse(i);
} 
catch (NumberFormatException ok) 
{
  // it's not numeric; that's fine, just continue
}
return handleTextResponse(response);
````

**Exception:** In tests, a caught exception may be ignored
without comment _if_ it is named `expected`. The
following is a very common idiom for ensuring that the method under test _does_ throw an
exception of the expected type, so a comment is unnecessary here.
````
try 
{
  emptyStack.pop();
  fail();
} 
catch (NoSuchElementException expected) 
{
}
````

### 9.9. Static members: qualified using class

When a reference to a static class member must be qualified, it is qualified with that class's
name, not with a reference or expression of that class's type.
````
Foo aFoo = ...;
Foo.aStaticMethod(); // good

aFoo.aStaticMethod(); // bad

somethingThatYieldsAFoo().aStaticMethod(); // very bad
````

### 9.10. Finalizers: not used

It is **extremely rare** to override `Object.finalize`.

**Tip:** Don't do it. If you absolutely must, first read and understand
[_Effective Java_](http://books.google.com/books?isbn=8131726592)
Item 7, "Avoid Finalizers," very carefully, and _then_ don't do it.

### 9.11. Final private fields, parameters, and local variables

Private fields, parameters, and local variables that are initialized only once,
and not changed after initialization, should be marked final.

Parameters should be marked final whenever possible. It is better to make a
copy of a parameter before modifying it.

## 10. Javadoc


### 10.1. Formatting


#### 10.1.1. General form

The _basic_ formatting of Javadoc blocks is as seen in this example:
````
/**
 * Multiple lines of Javadoc text are written here,
 * wrapped normally...
 */
public int method(String p1) { ... }
````

... or in this single-line example:
````
/** An especially short bit of Javadoc. */
````

The basic form is always acceptable. The single-line form may be substituted when there are no
at-clauses present, and the entirety of the Javadoc block (including comment markers) can fit on a
single line.


#### 10.1.2. Paragraphs

One blank line—that is, a line containing only the aligned leading asterisk
(`*`)—appears between paragraphs, and before the group of "at-clauses" if
present. Each paragraph but the first has `<p>` immediately before the first word,
with no space after.


#### 10.1.3. At-clauses

Any of the standard "at-clauses" that are used appear in the order `@param`,
`@return`, `@throws`, `@deprecated`, and these four types never
appear with an empty description. When an at-clause doesn't fit on a single line, continuation lines
are indented four (or more) spaces from the position of the `@`.



### 10.2. The summary fragment

The Javadoc for each class and member begins with a brief **summary fragment**. This
fragment is very important: it is the only part of the text that appears in certain contexts such as
class and method indexes.

This is a fragment—a noun phrase or verb phrase, not a complete sentence. It does
**not** begin with `A {@code Foo} is a...`, or
`This method returns...`, nor does it form a complete imperative sentence
like `Save the record.`. However, the fragment is capitalized and
punctuated as if it were a complete sentence.

**Tip:** A common mistake is to write simple Javadoc in the form
`/** @return the customer ID */`. This is incorrect, and should be
changed to `/** Returns the customer ID. */`.


### 10.3. Where Javadoc is used

At the _minimum_, Javadoc is present for every
`public` class, and every
`public` or
`protected` member of such a class, with a few exceptions
noted below.

Other classes and members still have Javadoc _as needed_.  Whenever an implementation
comment would be used to define the overall purpose or behavior of a class, method or field, that
comment is written as Javadoc instead. (It's more uniform, and more tool-friendly.)


#### 10.3.1. Exception: self-explanatory methods

Javadoc is optional for "simple, obvious" methods like
`getFoo`, in cases where there _really and truly_ is
nothing else worthwhile to say but "Returns the foo".

**Important:** it is not appropriate to cite this exception to justify
omitting relevant information that a typical reader might need to know. For example, for a method
named `getCanonicalName`, don't omit its documentation
(with the rationale that it would say only
`/** Returns the canonical name. */`) if a typical reader may have no idea
what the term "canonical name" means!


#### 10.3.2. Exception: overrides

Javadoc is not always present on a method that overrides a supertype method.
