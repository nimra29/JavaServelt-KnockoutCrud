package com.databasecrud.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item {

	private int itemNumber;
	private String itemClass;
	private String itemDescription;
	
	public Item() {
	}

	public Item(String itemClass, String itemDescription) {
		super();
		this.itemClass = itemClass;
		this.itemDescription = itemDescription;
	}

	public Item(int itemNumber, String itemClass, String itemDescription) {
		super();
		this.itemNumber = itemNumber;
		this.itemClass = itemClass;
		this.itemDescription = itemDescription;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemClass() {
		return itemClass;
	}

	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	

}
