package com.converter.toolkit.sqlparsing;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputPanel extends JPanel {

	public OutputPanel(String label, JTextArea area) {
		super();
		setLayout(new BorderLayout());
		add(new JLabel(label, JLabel.LEFT), BorderLayout.NORTH);
		add(new JScrollPane(area), BorderLayout.CENTER);
	}

}
