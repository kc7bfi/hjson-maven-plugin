# hjson-maven-plugin
hjson Maven Plugin to Generate Code

This maven plugin will generated the needed source files as determined by a WebSocket RPC (hjson) specification file.
The specification file is a JSON file that defines the hjson interface that is to be generated. 

For more documentation on hjson, see the [main project](https://github.com/kc7bfi/hjson-core).

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
			<phase>generate-sources</phase>
			<goals>
				<goal>generate</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
All files ending in `.json` will be processed.
