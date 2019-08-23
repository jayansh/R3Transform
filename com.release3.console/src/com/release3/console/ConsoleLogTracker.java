package com.release3.console;

import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleLineTracker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;

public class ConsoleLogTracker implements IConsoleLineTracker {
	private IConsole m_console;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IConsole console) {
		m_console = console;

	}

	@Override
	public void lineAppended(IRegion region) {
		try {

			String line = m_console.getDocument().get(region.getOffset(),
					region.getLength());

			// DO SOMETHING WITH THAT LINE

		} catch (BadLocationException e) {

			// WrCheck.logError(e);

		}

	}

}