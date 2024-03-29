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
/*
 * This file is part of Zql.
 *
 * Zql is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zql is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zql.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gibello.zql;

import java.io.* ;
import java.util.* ;

/**
 * ZFromItem: an SQL FROM clause (example: the FROM part of a SELECT...FROM).
 */
public class ZFromItem extends ZAliasedName {

  /**
   * Create a new FROM clause.
   * See the ZAliasedName constructor for more information.
   */
  public ZFromItem() { super(); }

  /**
   * Create a new FROM clause on a given table.
   * See the ZAliasedName constructor for more information.
   * @param fullname the table name.
   */
  public ZFromItem(String fullname) {
    super(fullname, ZAliasedName.FORM_TABLE);
  }

};

