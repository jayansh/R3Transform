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
package com.release3.transform.model;

import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.forms.Canvas;
import com.oracle.xmlns.forms.Graphics;

/**
 * 
 * @author jayansh
 *
 */
public class CanvasModel {

	private Canvas canvas;
	private List<Graphics> graphicsList;

	public CanvasModel() {
		graphicsList = new ArrayList<Graphics>();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void addGraphics(Graphics graphics) {
		graphicsList.add(graphics);
	}

	public List<Graphics> getGraphicsList() {
		return graphicsList;
	}

	public void setGraphicsList(List<Graphics> graphicsList) {
		this.graphicsList = graphicsList;
	}

}
