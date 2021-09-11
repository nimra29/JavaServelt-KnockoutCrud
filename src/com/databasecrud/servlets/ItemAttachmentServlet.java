package com.databasecrud.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.databasecrud.databases.ItemAttachmentDatabase;
import com.databasecrud.databases.ItemStructureDatabase;
import com.databasecrud.models.ItemAttachment;
import com.databasecrud.models.ItemStructure;
import com.google.gson.Gson;



@WebServlet("/itemAttachmentServlet")
public class ItemAttachmentServlet extends HttpServlet {

	ItemAttachmentDatabase itemAttachmentDatabase=new ItemAttachmentDatabase();
	private Logger logger = null;
	
	public void init(ServletConfig config) throws ServletException {
		logger = Logger.getLogger(ItemAttachmentServlet.class.getName());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	      sb.append(line + "\n");
	      line = reader.readLine();
	    }
	    reader.close();
	  
	    String params = sb.toString();
	   
	    JSONObject json = new JSONObject(params); 
	    int id=json.getInt("itemNumber");
	 
					
	}
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	      sb.append(line + "\n");
	      line = reader.readLine();
	    }
	    reader.close();
	  
	    String params = sb.toString();
	   
	    JSONObject json = new JSONObject(params);  
	    
	    String action=json.getString("action");
	    int id=json.getInt("itemNumber");
	   
	    
	    if(action.equals("get")) {
	    	List<ItemAttachment> myitemAttachment=itemAttachmentDatabase.selectItemAttachment(id); 
			Gson gson = new Gson();
			String itemAttachmentListJson=gson.toJson(myitemAttachment);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(itemAttachmentListJson);
			out.flush();
	    }else {
	    	
	    	 int attachmentSize=json.getInt("attachmentSize");
	    	 String attachmentName=json.getString("attachmentName");
	    	
	    	 try {
	    		 
	    		 long result=itemAttachmentDatabase.insertAttachment(new ItemAttachment(attachmentSize,attachmentName,id));
		    	

	    	 				PrintWriter out = response.getWriter();
	    	 				response.setContentType("application/json");
	    	 				response.setCharacterEncoding("UTF-8");
	    	 				out.print(result);
	    	 				out.flush();
	    	 			} catch (SQLException e) {
	    	 				logger.error("Exception is: ", e);
	    	 			}
	    	 
	    	
	    }
			
		
	
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	      sb.append(line + "\n");
	      line = reader.readLine();
	    }
	    reader.close();
	  
	    String params = sb.toString();
	    JSONObject json = new JSONObject(params); 
	    int attachmentId=json.getInt("attachmentId");
	    String attachmentName=json.getString("attachmentName");
	    int attachmentSize=json.getInt("attachmentSize");
	    int itemNumber=json.getInt("itemNumber");
	    try {
	    	ItemAttachment itemStructure=new ItemAttachment(attachmentId,attachmentSize,attachmentName,itemNumber);
	    	
	    	boolean result=itemAttachmentDatabase.updateItemAttachment(itemStructure);
	    

		}
	   catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
	    List<ItemAttachment> myitemattachment=itemAttachmentDatabase.selectItemAttachment(itemNumber); 
		Gson gson = new Gson();
		String itemAttachmentListJson=gson.toJson(myitemattachment);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(itemAttachmentListJson);
		out.flush();
		
		

		
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	      sb.append(line + "\n");
	      line = reader.readLine();
	    }
	    reader.close();
	  
	    String params = sb.toString();
	    
	    JSONObject json = new JSONObject(params);  
	    int attachmentId=json.getInt("attachmentId");
		try {
			boolean result=itemAttachmentDatabase.deleteItemAttachment(attachmentId);
		
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(result);
			out.flush();
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}

		

	}

}
