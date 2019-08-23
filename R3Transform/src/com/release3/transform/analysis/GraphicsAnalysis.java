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
package com.release3.transform.analysis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.converter.toolkit.ui.FormsObject;
import com.oracle.xmlns.forms.Canvas;
import com.oracle.xmlns.forms.Graphics;
import com.release3.transform.model.CanvasModel;

/**
 * 
 * @author jayansh
 * 
 */
public class GraphicsAnalysis {

	private List<CanvasModel> canvasModelList;

	public GraphicsAnalysis() {
		canvasModelList = new ArrayList<CanvasModel>();
	}

	public List<CanvasModel> getCanvasModelList() {
		return canvasModelList;
	}

	public void setCanvasModelList(List<CanvasModel> canvasModelList) {
		if (canvasModelList != null) {
			this.canvasModelList = canvasModelList;
		} else {
			this.canvasModelList = new ArrayList<CanvasModel>();
		}
	}

	public void analysis(Canvas canvas) {

		CanvasModel canvasModel = new CanvasModel();
		canvasModel.setCanvas(canvas);
		if (canvas.getChildren().size() > 0) {
			List<FormsObject> objList = canvas.getChildren();

			for (FormsObject formsObject : objList) {
				if (formsObject instanceof Graphics) {

					Graphics graphics = (Graphics) formsObject;
					// System.out.println(graphics);
					if ((graphics.getGraphicsText() != null && graphics
							.getGraphicsText().length() > 0)
							|| graphics.getFrameTitle() == null) {
						canvasModel.addGraphics(graphics);

					}
				}
			}

		}
		if (canvas.getHeight() == null || canvas.getHeight().intValue() < 10) {
			canvas.setHeight(new BigInteger("10"));

		}
		if (canvas.getWidth() == null || canvas.getWidth().intValue() < 10) {
			canvas.setWidth(new BigInteger("10"));
		}
		canvasModelList.add(canvasModel);
	}
}
