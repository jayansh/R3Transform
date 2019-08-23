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
package com.release3.tf.zql;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ParseException;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZqlJJParser;

public class PlSqlConverter {

	public List<ZStatement> parse(String text) throws ParseException {

		List<ZStatement> zStmts = new ArrayList<ZStatement>();
		ZqlJJParser p = null;

		if (text.length() < 1) {
			return null;

		} else {

			p = new ZqlJJParser(new DataInputStream(new ByteArrayInputStream(
					text.getBytes())));

		} // else ends here

		if (text.length() > 0) {
			System.out.println(text);
		}
		ZStatement zStmt = null;
		while ((zStmt = p.SQLStatement()) != null) {
			System.out.println(zStmt.toString());
			zStmts.add(zStmt);
		}
		return zStmts;
	}

	public static void main(String args[]) {
		PlSqlConverter converter = new PlSqlConverter();
		String text = "SELECT * FROM TableName;"
				+ "SELECT FirstName, LastName, Address, City, State FROM EmployeeAddressTable;"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE  WHERE SALARY >= 50000;"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE  WHERE POSITION = 'Manager';"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE "
				+ " WHERE SALARY > 40000 AND POSITION = 'Staff';"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE "
				+ " WHERE SALARY < 40000 OR BENEFITS < 10000;"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE " 
				+ " WHERE POSITION = 'Manager' AND SALARY > 60000 OR BENEFITS > 12000;"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE "
				+ " WHERE POSITION = 'Manager' AND (SALARY > 50000 OR BENEFIT > 10000);"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE"
				+ "  WHERE POSITION IN ('Manager', 'Staff');"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE"
				+ " WHERE SALARY BETWEEN 30000 AND 50000;"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEESTATISTICSTABLE"
				+ " WHERE SALARY NOT BETWEEN 30000 AND 50000;"
				+ "SELECT EMPLOYEEIDNO FROM EMPLOYEEADDRESSTABLE  WHERE LASTNAME LIKE 'L%';"
				+ "SELECT OWNERLASTNAME, OWNERFIRSTNAME FROM ANTIQUEOWNERS, ANTIQUES"
				+ " WHERE BUYERID = OWNERID AND ITEM = 'Chair';"
				+ "SELECT ANTIQUEOWNERS.OWNERLASTNAME, ANTIQUEOWNERS.OWNERFIRSTNAME"
				+ " FROM ANTIQUEOWNERS, ANTIQUES"
				+ " WHERE ANTIQUES.BUYERID = ANTIQUEOWNERS.OWNERID AND ANTIQUES.ITEM = 'Chair';"
				+ "SELECT DISTINCT SELLERID, OWNERLASTNAME, OWNERFIRSTNAME"
				+ " FROM ANTIQUES, ANTIQUEOWNERS"
				+ " WHERE SELLERID = OWNERID"
				+ " ORDER BY OWNERLASTNAME, OWNERFIRSTNAME, OWNERID;"
				+ "SELECT OWN.OWNERLASTNAME Last Name, ORD.ITEMDESIRED Item Ordered"
				+ " FROM ORDERS ORD, ANTIQUEOWNERS OWN"
				+ " WHERE ORD.OWNERID = OWN.OWNERID "
				+ "AND ORD.ITEMDESIRED IN (SELECT ITEM FROM ANTIQUES);"
				+ "SELECT SUM(SALARY), AVG(SALARY) FROM EMPLOYEESTATISTICSTABLE;"
				+ "SELECT MIN(BENEFITS) FROM EMPLOYEESTATISTICSTABLE  WHERE POSITION = 'Manager';"
				+ "SELECT COUNT(*) FROM EMPLOYEESTATISTICSTABLE  WHERE POSITION = 'Staff';"
				+ "SELECT SELLERID FROM ANTIQUES, ANTVIEW  WHERE ITEMDESIRED = ITEM;"
				+ "INSERT INTO ANTIQUES VALUES (21, 01, 'Ottoman', 200.00);"
				+ "INSERT INTO ANTIQUES (BUYERID, SELLERID, ITEM) VALUES (01, 21, 'Ottoman');"
				+ "DELETE FROM ANTIQUES  WHERE ITEM = 'Ottoman';"
				+ "DELETE FROM ANTIQUES  WHERE ITEM = 'Ottoman' AND BUYERID = 01 AND SELLERID = 21;"
				+ "UPDATE ANTIQUES SET PRICE = 500.00  WHERE ITEM = 'Chair';"
				+ "SELECT BUYERID, MAX(PRICE) FROM ANTIQUES GROUP BY BUYERID;"
				+ "SELECT BUYERID, MAX(PRICE) FROM ANTIQUES GROUP BY BUYERID HAVING PRICE > 1000;"
				+ "SELECT OWNERID FROM ANTIQUES"
				+ " WHERE PRICE > (SELECT AVG(PRICE) + 100 FROM ANTIQUES);"
				+ "SELECT OWNERLASTNAME FROM ANTIQUEOWNERS"
				+ " WHERE OWNERID = (SELECT DISTINCT BUYERID FROM ANTIQUES);"
				+ "UPDATE ANTIQUEOWNERS SET OWNERFIRSTNAME = 'John'"
				+ " WHERE OWNERID = (SELECT BUYERID FROM ANTIQUES  WHERE ITEM = 'Bookcase');"
				+ "SELECT OWNERFIRSTNAME, OWNERLASTNAME FROM ANTIQUEOWNERS"
				+ " WHERE EXISTS (SELECT * FROM ANTIQUES  WHERE ITEM = 'Chair');"
				+ "SELECT BUYERID, ITEM FROM ANTIQUES"
				+ " WHERE PRICE >= ALL (SELECT PRICE FROM ANTIQUES);"
				+ "SELECT BUYERID FROM ANTIQUEOWNERS UNION SELECT OWNERID FROM ORDERS;"
				+ "SELECT OWNERID, 'is in both Orders & Antiques' FROM ORDERS, ANTIQUES"
				+ " WHERE OWNERID = BUYERID" + " UNION "
				+ "SELECT BUYERID, 'is in Antiques only' FROM ANTIQUES"
				+ " WHERE BUYERID NOT IN (SELECT OWNERID FROM ORDERS);"
				+ "SELECT * FROM GRADES  WHERE GRADE = 2+3-4+5;"
				+ "SELECT * FROM GRADES  WHERE GRADE = 2+3*4-5;"
				+ "select * from table1  WHERE col1 is null;"
				+ "select * from table1  WHERE avg(col1) is not null; " +
						"EXIT;";
		try {
			converter.parse(text);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
