package com.databasecrud.models;

public class ItemAML {

	private int registryId;
	private String manufacturerPart;
	private String manufacturer;
	private int id;

	public ItemAML(String manufacturerPart, String manufacturer, int id) {
		super();
		this.manufacturerPart = manufacturerPart;
		this.manufacturer = manufacturer;
		this.id = id;
	}

	public ItemAML(int registryId, String manufacturerPart, String manufacturer, int id) {

		this.registryId = registryId;
		this.manufacturerPart = manufacturerPart;
		this.manufacturer = manufacturer;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegistryId() {
		return registryId;
	}

	public void setRegistryId(int registryId) {
		this.registryId = registryId;
	}

	public String getManufacturerPart() {
		return manufacturerPart;
	}

	public void setManufacturerPart(String manufacturerPart) {
		this.manufacturerPart = manufacturerPart;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}
