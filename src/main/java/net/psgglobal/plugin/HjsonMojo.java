package net.psgglobal.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.hjson.JsonValue;

/*
This file is part of wsrpc.

wsrpc is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

wsrpc is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with wsrpc.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * The plugin class
 */
@Mojo(name = "validate")
@Execute(phase = LifecyclePhase.GENERATE_SOURCES)
public class HjsonMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.basedir}/src/main/resources/wsrpc", property = "inputDir", required = true)
	private File inputDir;

	@Parameter(defaultValue = "${project}")
	private MavenProject project;

	/**
	 * Execute the plugin
	 * @throws MojoExecutionException any errors
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {

		// process each specification file
		if (inputDir == null) throw new MojoExecutionException("Cannot find inputDir");
		if (!inputDir.exists()) throw new MojoExecutionException("inputDir does not exist");

		getLog().info("inputDir = " + inputDir.getAbsolutePath());

		for (File dirFile : inputDir.listFiles()) {
			if (!dirFile.getAbsolutePath().endsWith(".hson")) continue;

			// read the specification file
			BufferedReader reader = null;
			String hjsonSource = null;
			try {
				reader = new BufferedReader(new FileReader(dirFile));
				hjsonSource = readAsString(reader);
			} catch (IOException e) {
				getLog().warn("Error reading input sorce files: " + e.getMessage());
				throw new MojoExecutionException("Error reading input sorce files", e);
			} finally {
				if (reader != null) try { reader.close(); } catch (Exception e) { getLog().warn("Could not close reader " + e.getMessage()); }
			}

			// verify we can parse
			try {
				JsonValue.readHjson(hjsonSource);
			} catch (Exception e) {
				throw new MojoExecutionException("Cannot parse hjson file " + dirFile.getAbsolutePath(), e);
			}
		}
	}

	/**
	 * Read an entire resource as a string
	 * @param reader the buffered reader
	 * @return the string
	 * @throws IOException any errors
	 */
	private String readAsString(BufferedReader reader) throws IOException {
		StringBuilder str = new StringBuilder();
		for (String ln = reader.readLine(); ln != null; ln = reader.readLine()) str.append(ln + "\n");
		return str.toString();
	}
}
