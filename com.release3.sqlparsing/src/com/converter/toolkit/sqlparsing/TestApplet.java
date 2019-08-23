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
package com.converter.toolkit.sqlparsing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class TestApplet extends JApplet {

    static {
	setLookAndFeel();
    }

    private static final String SAMPLE_CODE = "/bindvariableprocessor/SampleCode.txt";

    JTextArea inputArea = new JTextArea();

    JTextArea outputCodeArea = new JTextArea();

    JTextArea outputVariablesArea = new JTextArea();

    CodeProcessor processor = new CodeProcessor();

    public TestApplet() {

	inputArea.setMargin(new Insets(5, 5, 5, 5));
	outputCodeArea.setEditable(false);
	outputVariablesArea.setEditable(false);
	outputCodeArea.setEnabled(false);
	outputCodeArea.setDisabledTextColor(Color.BLUE);
	outputCodeArea.setMargin(new Insets(5, 5, 5, 5));
	outputVariablesArea.setEnabled(false);
	outputVariablesArea.setDisabledTextColor(Color.BLUE);
	outputVariablesArea.setMargin(new Insets(5, 5, 5, 5));

	ActionListener listener = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		process();
	    }
	};

	getContentPane().setLayout(new GridLayout(2, 2));
	getContentPane().add(createInputpanel(listener));
	getContentPane().add(new JScrollPane(new JLabel()));
	getContentPane().add(new OutputPanel("Processed code", outputCodeArea));
	getContentPane().add(new OutputPanel("Variables", outputVariablesArea));
    }

    protected void process() {

	ProcessedCode code = processor.process(inputArea.getText());
	outputCodeArea.setText(code.getCode());
	outputCodeArea.setCaretPosition(0);
	printVariables(code.getVariables());
    }

    void printVariables(Vector<Variable> vars) {
	outputVariablesArea.setText("");
	for (Variable var : vars) {
	    outputVariablesArea.append(var.getName() + " " + var.getType()
		    + " " + var.getReplacedName() + "\n");
	}
	outputVariablesArea.setCaretPosition(0);
    }

    @Override
    public void init() {

	setLookAndFeel();
	SwingUtilities.updateComponentTreeUI(this);

	loadSampleCode();
    }

    @Override
    public void start() {

    }

    private static void setLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private JPanel createInputpanel(ActionListener listener) {
	JPanel panel = new JPanel(new BorderLayout());

	panel.add(new JLabel("Input code", JLabel.LEFT), BorderLayout.NORTH);

	panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

	JPanel bPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	JButton button = new JButton("Process");

	button.addActionListener(listener);

	bPanel.add(button);

	panel.add(bPanel, BorderLayout.SOUTH);

	return panel;
    }

    private void loadSampleCode() {
	try {

	    InputStream inputStream = getClass().getResourceAsStream(
		    SAMPLE_CODE);

	    if (inputStream != null) {

		char[] buffer = new char[1024];

		StringWriter writer = new StringWriter();

		BufferedReader reader = new BufferedReader(
			new InputStreamReader(inputStream));

		int idx;

		while ((idx = reader.read(buffer)) != -1) {
		    writer.write(buffer, 0, idx);
		}

		inputArea.setText(writer.toString());

	    } else {
		System.err.println("Cannot find sample code at \""
			+ SAMPLE_CODE + "\".");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
