//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.12 at 11:40:08 AM EST 
//


package com.oracle.xmlns.forms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import com.converter.toolkit.ui.FormsObject;


/**
 * <p>Java class for ObjectLibrary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectLibrary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{http://xmlns.oracle.com/Forms}ObjectLibraryTab" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;group ref="{http://xmlns.oracle.com/Forms}JdapiElements" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DirtyInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="Name" use="required" type="{http://xmlns.oracle.com/Forms}nameType" />
 *       &lt;attribute name="ObjectCount" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="PersistentClientInfoLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectLibrary", namespace = "http://xmlns.oracle.com/Forms", propOrder = {
    "children"
})
public class ObjectLibrary
    extends FormsObject
{

    @XmlElements({
        @XmlElement(name = "RecordGroupColumn", namespace = "http://xmlns.oracle.com/Forms", type = RecordGroupColumn.class),
        @XmlElement(name = "ProgramUnit", namespace = "http://xmlns.oracle.com/Forms", type = ProgramUnit.class),
        @XmlElement(name = "LOV", namespace = "http://xmlns.oracle.com/Forms", type = LOV.class),
        @XmlElement(name = "Graphics", namespace = "http://xmlns.oracle.com/Forms", type = Graphics.class),
        @XmlElement(name = "AttachedLibrary", namespace = "http://xmlns.oracle.com/Forms", type = AttachedLibrary.class),
        @XmlElement(name = "MenuItem", namespace = "http://xmlns.oracle.com/Forms", type = MenuItem.class),
        @XmlElement(name = "VisualState", namespace = "http://xmlns.oracle.com/Forms", type = VisualState.class),
        @XmlElement(name = "Relation", namespace = "http://xmlns.oracle.com/Forms", type = Relation.class),
        @XmlElement(name = "Coordinate", namespace = "http://xmlns.oracle.com/Forms", type = Coordinate.class),
        @XmlElement(name = "Item", namespace = "http://xmlns.oracle.com/Forms", type = Item.class),
        @XmlElement(name = "PropertyClass", namespace = "http://xmlns.oracle.com/Forms", type = PropertyClass.class),
        @XmlElement(name = "RadioButton", namespace = "http://xmlns.oracle.com/Forms", type = RadioButton.class),
        @XmlElement(name = "Block", namespace = "http://xmlns.oracle.com/Forms", type = Block.class),
        @XmlElement(name = "RecordGroup", namespace = "http://xmlns.oracle.com/Forms", type = RecordGroup.class),
        @XmlElement(name = "Trigger", namespace = "http://xmlns.oracle.com/Forms", type = Trigger.class),
        @XmlElement(name = "ObjectGroup", namespace = "http://xmlns.oracle.com/Forms", type = ObjectGroup.class),
        @XmlElement(name = "Point", namespace = "http://xmlns.oracle.com/Forms", type = Point.class),
        @XmlElement(name = "TabPage", namespace = "http://xmlns.oracle.com/Forms", type = TabPage.class),
        @XmlElement(name = "TextSegment", namespace = "http://xmlns.oracle.com/Forms", type = TextSegment.class),
        @XmlElement(name = "VisualAttribute", namespace = "http://xmlns.oracle.com/Forms", type = VisualAttribute.class),
        @XmlElement(name = "DataSourceColumn", namespace = "http://xmlns.oracle.com/Forms", type = DataSourceColumn.class),
        @XmlElement(name = "Font", namespace = "http://xmlns.oracle.com/Forms", type = Font.class),
        @XmlElement(name = "ModuleParameter", namespace = "http://xmlns.oracle.com/Forms", type = ModuleParameter.class),
        @XmlElement(name = "LOVColumnMapping", namespace = "http://xmlns.oracle.com/Forms", type = LOVColumnMapping.class),
        @XmlElement(name = "Window", namespace = "http://xmlns.oracle.com/Forms", type = Window.class),
        @XmlElement(name = "Alert", namespace = "http://xmlns.oracle.com/Forms", type = Alert.class),
        @XmlElement(name = "CompoundText", namespace = "http://xmlns.oracle.com/Forms", type = CompoundText.class),
        @XmlElement(name = "ObjectLibraryTab", namespace = "http://xmlns.oracle.com/Forms", type = ObjectLibraryTab.class),
        @XmlElement(name = "Canvas", namespace = "http://xmlns.oracle.com/Forms", type = Canvas.class),
        @XmlElement(name = "Report", namespace = "http://xmlns.oracle.com/Forms", type = Report.class),
        @XmlElement(name = "Editor", namespace = "http://xmlns.oracle.com/Forms", type = Editor.class),
        @XmlElement(name = "DataSourceArgument", namespace = "http://xmlns.oracle.com/Forms", type = DataSourceArgument.class),
        @XmlElement(name = "Menu", namespace = "http://xmlns.oracle.com/Forms", type = Menu.class)
    })
    protected List<FormsObject> children;
    @XmlAttribute(name = "Comment")
    protected String comment;
    @XmlAttribute(name = "DirtyInfo")
    protected Boolean dirtyInfo;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "ObjectCount")
    protected BigInteger objectCount;
    @XmlAttribute(name = "PersistentClientInfoLength")
    protected BigInteger persistentClientInfoLength;

    /**
     * Gets the value of the children property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the children property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildren().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecordGroupColumn }
     * {@link ProgramUnit }
     * {@link LOV }
     * {@link Graphics }
     * {@link AttachedLibrary }
     * {@link MenuItem }
     * {@link VisualState }
     * {@link Relation }
     * {@link Coordinate }
     * {@link Item }
     * {@link PropertyClass }
     * {@link RadioButton }
     * {@link Block }
     * {@link RecordGroup }
     * {@link Trigger }
     * {@link ObjectGroup }
     * {@link Point }
     * {@link TabPage }
     * {@link TextSegment }
     * {@link VisualAttribute }
     * {@link DataSourceColumn }
     * {@link Font }
     * {@link ModuleParameter }
     * {@link LOVColumnMapping }
     * {@link Window }
     * {@link Alert }
     * {@link CompoundText }
     * {@link ObjectLibraryTab }
     * {@link Canvas }
     * {@link Report }
     * {@link Editor }
     * {@link DataSourceArgument }
     * {@link Menu }
     * 
     * 
     */
    public List<FormsObject> getChildren() {
        if (children == null) {
            children = new ArrayList<FormsObject>();
        }
        return this.children;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the dirtyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDirtyInfo() {
        return dirtyInfo;
    }

    /**
     * Sets the value of the dirtyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDirtyInfo(Boolean value) {
        this.dirtyInfo = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the objectCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getObjectCount() {
        return objectCount;
    }

    /**
     * Sets the value of the objectCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setObjectCount(BigInteger value) {
        this.objectCount = value;
    }

    /**
     * Gets the value of the persistentClientInfoLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPersistentClientInfoLength() {
        return persistentClientInfoLength;
    }

    /**
     * Sets the value of the persistentClientInfoLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPersistentClientInfoLength(BigInteger value) {
        this.persistentClientInfoLength = value;
    }

}
