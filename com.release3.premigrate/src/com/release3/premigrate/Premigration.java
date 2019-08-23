package com.release3.premigrate;

import java.io.File;
import java.io.IOException;

import com.release3.premigrate.PreMirgate;

public class Premigration {

	public Premigration() {
	}

	public void definitionGenerator(String destDir) throws IOException {
		(new PreMirgate()).makeDefinitionFS(new File(destDir));
	}
}
