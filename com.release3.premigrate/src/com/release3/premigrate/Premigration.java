/*******************************************************************************
 * Copyright (c) 2009, 2013 ObanSoft Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jayansh Shinde
 *******************************************************************************/
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
