# hjson-maven-plugin
hjson Maven Plugin

This maven plugin can be used to validate hjson files. 

## Usage
To use this plugin, add it to your maven project descriptor pom.xml.
The following example shows how to generate a interfaces defined in the "hjson" subdirectory.

```
<plugin>
	<groupId>net.psgglobal.hjson</groupId>
	<artifactId>hjson-maven-plugin</artifactId>
	<version>1.0-SNAPSHOT</version>
	<configuration>
		<inputDir>${project.sourceDirectory}/main/resources/hjson</inputDir>
	</configuration>
	<executions>
		<execution>
			<goals>
				<goal>validate</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
All files ending in `.hjson` will be processed.
