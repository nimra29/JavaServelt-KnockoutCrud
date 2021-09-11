package com.databasecrud.models;

import java.sql.Date;

public class ItemStructure {

	private int structureId;
	private int lifecyclePhases;
	private String createdBy;
	private Date creationDate;
	private int id;

	public ItemStructure(int structureId, int lifecyclePhases, String createdBy, int id) {
		super();
		this.structureId = structureId;
		this.lifecyclePhases = lifecyclePhases;
		this.createdBy = createdBy;
		this.id = id;
	}

	public ItemStructure(int lifecyclePhases, String createdBy, int id) {
		super();
		this.lifecyclePhases = lifecyclePhases;
		this.createdBy = createdBy;
		this.id = id;
	}

	public ItemStructure(int lifecyclePhases, String createdBy, Date creationDate, int id) {
		super();
		this.lifecyclePhases = lifecyclePhases;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.id = id;
	}

	public ItemStructure(int structureId, int lifecyclePhases, String createdBy, Date creationDate, int id) {
		super();
		this.structureId = structureId;
		this.lifecyclePhases = lifecyclePhases;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getStructureId() {
		return structureId;
	}
	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}
	public int getLifecyclePhases() {
		return lifecyclePhases;
	}
	public void setLifecyclePhases(int lifecyclePhases) {
		this.lifecyclePhases = lifecyclePhases;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
