package com.release3.tf.core;

import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * @author jayansh 
 * This class stores form information like name of form,
 *         name/values of containing blocks etc.
 */
public class OracleForm {
	protected String formName;
	//block name and values.
	private Hashtable<String,String> blockTable = new Hashtable<String,String>();
	//form size in byte
	private long formsize;
	public OracleForm(String formName,Map<? extends String, ? extends String> blockMap,long formsize){
		this.formName = formName;
		blockTable.putAll(blockMap);
		this.formsize = formsize;
		
	}
	public void addBlock(String blockName,String blockValue){
		blockTable.put(blockName, blockValue);
	}
	public void removeBlock(String blockName){
		blockTable.remove(blockName);
	}
	public void addAll(Map<? extends String, ? extends String> blockMap){
		blockTable.putAll(blockMap);
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public long getFormsize() {
		return formsize;
	}
	public void setFormsize(long formsize) {
		this.formsize = formsize;
	}
	
}
