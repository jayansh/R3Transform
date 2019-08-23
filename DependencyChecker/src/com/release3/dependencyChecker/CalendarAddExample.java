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
package com.release3.dependencyChecker;

import java.util.Calendar;

public class CalendarAddExample {

	public static void main(String[] args)

	{

		Calendar cal = Calendar.getInstance();

		System.out.println("Today : " + cal.getTime());

		// Substract 30 days from the calendar

		cal.add(Calendar.DATE, 45);

		System.out.println("After 45 days : " + cal.getTime());
		cal.add(Calendar.DATE, -75);
		System.out.println("30 days ago: " + cal.getTime());

		// Add 10</SPAN> months to the calendar

		cal.add(Calendar.MONTH, 10);

		System.out.println("10 months later: " + cal.getTime());

		// Substract 1 year from the calendar

		cal.add(Calendar.YEAR, -1);

		System.out.println("1 year ago: " + cal.getTime());

	}
}
