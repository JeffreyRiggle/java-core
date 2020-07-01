# Installation
You can install this using maven with the following maven configuration

In your .m2
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
  		  <id>github</id>
  		  <name>Jeffrey Riggle Apache Maven Packages</name>
  		  <url>https://maven.pkg.github.com/JeffreyRiggle/java-core</url>
  	    </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <username>USERNAME</username>
      <password>TOKEN</password>
    </server>
  </servers>
</settings>
```

In your pom file
```xml
<dependency>
  <groupId>com.ilusr.core.Core</groupId>
  <artifactId>core</artifactId>
  <version>2.0.25</version>
</dependency>
```

For more information on github packages see the [documentation](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages#installing-a-package).