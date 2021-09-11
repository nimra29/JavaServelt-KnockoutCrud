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

import com.databasecrud.databases.ItemStructureDatabase;
import com.databasecrud.models.ItemAML;
import com.databasecrud.models.ItemStructure;
import com.google.gson.Gson;



@WebServlet("/itemstructureapi")
public class ItemStructureServlet extends HttpServlet {

	ItemStructureDatabase itemStructureDatabase=new ItemStructureDatabase();
	private Logger logger = null;
	
	public void init(ServletConfig config) throws ServletException {
		logger = Logger.getLogger(ItemStructureServlet.class.getName());
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
	    	List<ItemStructure> myitemstructure=itemStructureDatabase.selectItemStructure(id); 
			Gson gson = new Gson();
			String itemAMLListJson=gson.toJson(myitemstructure);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(itemAMLListJson);
			out.flush();
	    }else {
	    	
	    	 int lifecyclePhases=json.getInt("lifecyclePhases");
	    	 String createdBy=json.getString("createdBy");
	    	
	    	 try {
	    		 int result=itemStructureDatabase.insertItemStructure(new ItemStructure(lifecyclePhases, createdBy, id));
	    		 ItemStructure myitemstructure=itemStructureDatabase.selectSpecificStructure(result);
	 			Gson gson = new Gson();
	 			String itemAMLListJson=gson.toJson(myitemstructure);
	 			PrintWriter out = response.getWriter();
	 			response.setContentType("application/json");
	 			response.setCharacterEncoding("UTF-8");
	 			out.print(itemAMLListJson);
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
	    int structureId=json.getInt("structureId");
	    String createdBy=json.getString("createdBy");
	    int lifecyclePhases=json.getInt("lifecyclePhases");
	    int itemNumber=json.getInt("id");
	    try {
	    	ItemStructure itemStructure=new ItemStructure(structureId,lifecyclePhases,createdBy,itemNumber);
	    	
	    	boolean result=itemStructureDatabase.updateItemStructure(itemStructure);
	    

		}
	   catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
	    List<ItemStructure> myitemaml=itemStructureDatabase.selectItemStructure(itemNumber); 
		Gson gson = new Gson();
		String itemAMLListJson=gson.toJson(myitemaml);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(itemAMLListJson);
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
	    int structureId=json.getInt("structureId");
		try {
			boolean result=itemStructureDatabase.deleteItemStructure(structureId);
		
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
