
# Report Generator library

This is a Java libraries with some utilities ....

## Getting Started

To get you started you can simply clone the `java-report-generator` repository and install the dependencies.

### Prerequisites

You need [git][git] to clone the `java-report-generator` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

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


[git]: https://git-scm.com/
[archunit]: https://www.archunit.org/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
[junit]: https://junit.org/junit5/


