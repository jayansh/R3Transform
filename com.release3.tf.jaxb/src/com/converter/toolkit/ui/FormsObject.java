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
package com.converter.toolkit.ui;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.converter.toolkit.ui.FormsObject;

@XmlAccessorType(XmlAccessType.NONE)
public class FormsObject {
	
	@XmlTransient
    private String name = "na";

    public List<FormsObject> getChildren() {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return this.getClass().getName();
    }

    public String getPage() {
    
    
        if (getType().equals("com.oracle.xmlns.forms.RecordGroup"))
            return "RecordGroupCustomization";
        else if (getType().equals("com.oracle.xmlns.forms.Block"))
            return "BlockCustomization";
        else if (getType().equals("com.converter.toolkit.ui.Operation"))
            return "OperationCustomization";
        else if (getType().equals("com.oracle.xmlns.forms.Trigger"))
            return "TriggerCustomization";
        else if (getType().equals("com.converter.toolkit.ui.ItemTrigger"))
            return "AddItemTrigger";
        else if (getType().equals("com.converter.toolkit.ui.BlockTrigger"))
            return "AddBlockTrigger";
        else 
            return "empty";
    }

    public void setName(String name) {
        this.name = name;
    }
}
