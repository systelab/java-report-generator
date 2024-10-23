
# Report Generator library

This is a Java libraries with some utilities to generate two kind of reports: grid ones and form ones.

## Getting Started

To get you started you can simply clone the `java-report-generator` repository and install the dependencies.

### Prerequisites

You need [git][git] to clone the `java-report-generator` repository.

You will need [Java™ SE Development Kit 21](https://adoptium.net/temurin/releases/) [jdk-download](https://github.com/adoptium/temurin21-binaries/releases/tag/jdk-21.0.5%2B11) and [Maven][maven].

### Clone

Clone the `java-report-generator` repository using git:

```bash
git clone https://github.com/systelab/java-report-generator.git
cd java-report-generator
```

### Install Dependencies

In order to install the dependencies, you must run:

```bash
mvn clean install
```

## Release

In order to release a new version:

Step 1. Update the version in your pom.xml file. For example:

```
<version>1.0.2</version>
```

Step 2. Commit and push in the master branch, and generate a new Release by pressing the button "Draft new release".

In the example use v.1.0.2 as the Tag version, and 1.0.2 as the release title.

Step 3. Head to https://jitpack.io and look up "systelab/java-report-generator". Choose the new version and press the button "Get it". Check the log to verify that everything is fine.


## Using the library

In order to use the library, you should add the dependency to your project.

### Gradle

In order to use the library, you should:

Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```
dependencies {
    implementation 'com.github.systelab:java-report-generator:v1.0.2'
}
```

### Maven

Step 1. Add the JitPack repository to your build file

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Step 2. Add the dependency

```
<dependency>
    <groupId>com.github.systelab</groupId>
    <artifactId>java-report-generator</artifactId>
    <version>v1.0.2</version>
</dependency>
```

## API

### Grid Report

In order to generate a grid report use the method build in GridReportService.

You will need to specify the configuration, the data, the output format (pdf or excel) and for pdf the page size.

```java
GridReportService gridReportService = new GridReportService();
GeneralConfiguration.setDefaultNullString("-");
File file = gridReportService.build(configuration, dataSource, ReportFormat.PDF, PageFormat.A4);
```

Configuration allows to specify the name of the file to generate, the report header, the footer and the columns to be present.

- Header includes a title, a logo and a up to four additional information.
- Footer includes up to three additional information and let you specify if the page number should be present.
- For each column, the key, the title and the width must be specified. The width is based in a grid system based on 12 columns across the page.

Use the builder method in GridReportConfiguration to generate your Configuration.

Data is provided through a class implementing the interface GridPageDataSource, in order to get the data in bursts to minimize memory utilization.

```java
public interface GridPageDataSource 
{
  void moveFirst();
  boolean nextPage();
  List<GridReportRow> getCurrentPageRows();
}
```

### Form Report

In order to generate a form report use the method build in FormReportService.

You will need to specify the configuration, the data and the page size (pdf will be generated).

```java
FormReportService formReportService = new FormReportService();
File file = formReportService.build(configuration, data, PageFormat.A4);
```

Configuration allows to specify the name of the file to generate, the report header and the footer.

- Header includes a title, a logo and a up to four additional information.
- Footer includes up to three additional information and let you specify if the page number should be present.

Use the builder method in FormReportConfiguration to generate your Configuration.

Data is provided through a FormReportData, a class that represents a hierarchy of sections, titles, fields and subfields.

Use the builder method in FormReportData to generate your data structure.


[git]: https://git-scm.com/
[archunit]: https://www.archunit.org/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
[junit]: https://junit.org/junit5/


