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
import com.databasecrud.databases.ItemDatabase;
import com.databasecrud.models.Item;
import com.google.gson.Gson;

@WebServlet("/itemServlet")
public class ItemServlet extends HttpServlet {
	
	ItemDatabase itemDatabase=new ItemDatabase();
	private Logger logger = null;
	public void init(ServletConfig config) throws ServletException {
		logger = Logger.getLogger(ItemServlet.class.getName());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("here we go!");
		logger.info("here we go!");
		logger.info("here we go!");
		

		if (request.getParameter("itemNumber") != null) {
			Item myitem = itemDatabase.selectItem(Integer.parseInt(request.getParameter("itemNumber")));
			Gson gson = new Gson();
			String itemListJson = gson.toJson(myitem);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(itemListJson);
			out.flush();
		} else {
			List<Item> itms = itemDatabase.selectAllItems();
			Gson gson = new Gson();
			String itemListJson = gson.toJson(itms);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(itemListJson);
			out.flush();
		}

		logger.info("logger name information");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		String itemClass = json.getString("itemClass");
		String itemDescription = json.getString("itemDescription");
		try {
			long result = itemDatabase.insertItem(new Item(itemClass, itemDescription));

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(result);
			out.flush();
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		int itemNumber = json.getInt("itemNumber");
		String itemClass = json.getString("itemClass");
		String itemDescription = json.getString("itemDescription");
		try {
			Item myitem = new Item(itemNumber, itemClass, itemDescription);
			boolean result = itemDatabase.updateItem(myitem);
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		List<Item> itms = itemDatabase.selectAllItems();
		Gson gson = new Gson();
		String itemListJson = gson.toJson(itms);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(itemListJson);
		out.flush();

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		int itemNumber = json.getInt("itemNumber");
		try {
			boolean result = itemDatabase.deleteItem(itemNumber);
			
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
