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

import com.databasecrud.databases.ItemAMLDatabase;
import com.databasecrud.models.ItemAML;
import com.google.gson.Gson;



@WebServlet("/itemamlapi")
public class ItemAMLServlet extends HttpServlet {

	ItemAMLDatabase itemAMLDatabase=new ItemAMLDatabase();
	
	private Logger logger = null;
	public void init(ServletConfig config) throws ServletException {
		logger = Logger.getLogger(ItemAMLServlet.class.getName());
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				if(request.getParameter("itemNumber")!=null) {
			
			List<ItemAML> myitemaml=itemAMLDatabase.selectItemAML(Integer.parseInt(request.getParameter("itemNumber"))); 
			Gson gson = new Gson();
			String itemAMLListJson=gson.toJson(myitemaml);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(itemAMLListJson);
			out.flush();
				}
		
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
		    	List<ItemAML> myitemaml=itemAMLDatabase.selectItemAML(id); 
				Gson gson = new Gson();
				String itemAMLListJson=gson.toJson(myitemaml);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(itemAMLListJson);
				out.flush();
		    }else {
		    	
		    	 String manufacturPart=json.getString("manufacturPart");
		    	 String manufacturer=json.getString("manufacturer");
		    	
		    	 try {
		    		 long result=itemAMLDatabase.insertAML(new ItemAML(manufacturPart, manufacturer, id));
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
	    int registryId=json.getInt("registryId");
	       String manufacturerPart=json.getString("manufacturPart");
	    String manufacturer=json.getString("manufacturer");
	    int itemNumber=json.getInt("id");
	    try {
	    	ItemAML itemAML=new ItemAML(registryId,manufacturerPart, manufacturer, itemNumber);
	    	
	    	boolean result=itemAMLDatabase.updateItem(itemAML);

		}
	   catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
	    List<ItemAML> myitemaml=itemAMLDatabase.selectItemAML(itemNumber); 
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
	    int registryId=json.getInt("registryId");
		try {
			boolean result=itemAMLDatabase.deleteItem(registryId);
			

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
