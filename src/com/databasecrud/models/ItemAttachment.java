package com.databasecrud.models;

public class ItemAttachment {
	
	private int attachmentId;
	private int attachmentSize;
	private String attachmentName;
	private int id;
	
	
	
	public ItemAttachment(int attachmentSize, String attachmentName, int id) {
		super();
		this.attachmentSize = attachmentSize;
		this.attachmentName = attachmentName;
		this.id = id;
	}

	public ItemAttachment(int attachmentId, int attachmentSize, String attachmentName, int id) {
		super();
		this.attachmentId = attachmentId;
		this.attachmentSize = attachmentSize;
		this.attachmentName = attachmentName;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAttachmentId() {
		return attachmentId;
	}
	
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	public int getAttachmentSize() {
		return attachmentSize;
	}
	
	public void setAttachmentSize(int attachmentSize) {
		this.attachmentSize = attachmentSize;
	}
	
	public String getAttachmentName() {
		return attachmentName;
	}
	
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

}
