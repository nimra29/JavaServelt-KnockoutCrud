package com.databasecrud.databases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.databasecrud.models.Item;
import com.databasecrud.models.ItemAML;
import com.databasecrud.models.ItemStructure;

public class ItemStructureDatabase {
	public static final String FILENAME = "/home/dev/Downloads/TemplateProject/src/com/databasecrud/databases/database.properties";
	private static final String INSERT_itemS_SQL = "INSERT INTO itemstructure" + "(id,lifecyclePhases,createdBy) VALUES" + "(?,?,?);";
	private static final String SELECT_item_BY_ID = "SELECT * FROM itemstructure WHERE id=?";
	private static final String SELECT_item = "SELECT * FROM itemstructure WHERE structureId=?";
	private static final String DELETE_itemS_SQL = "DELETE from itemstructure WHERE structureId=?;";
	private static final String UPDATE_itemS_SQL = "UPDATE itemstructure set lifecyclePhases=?,createdBy=? WHERE structureId=?;";
	private Logger 	logger = Logger.getLogger(ItemStructureDatabase.class.getName());

	public ItemStructureDatabase() {

	}

	public int insertItemStructure(ItemStructure amlStructure) throws SQLException, IOException {
		
		int id = 0;
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_itemS_SQL,
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setInt(1, amlStructure.getId());
			preparedStatement.setInt(2, amlStructure.getLifecyclePhases());
			preparedStatement.setString(3, amlStructure.getCreatedBy());

			int updated = preparedStatement.executeUpdate();
			ResultSet generatedKeysResultSet = preparedStatement.getGeneratedKeys();
			generatedKeysResultSet.next();
			id = generatedKeysResultSet.getInt(1);
			

		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return id;
	}

	public ItemStructure selectSpecificStructure(int id) throws IOException {
		ItemStructure itemStructure =null;
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_item);) {
			preparedStatement.setInt(1, id);
		

			ResultSet rs = preparedStatement.executeQuery();
			
	         while (rs.next()) {
				int itemId = rs.getInt("id");
				int structureId = rs.getInt("structureId");
				int lifecyclePhases = rs.getInt("lifecyclePhases");
				Date creationDate = rs.getDate("creationDate");
				String createdBy = rs.getString("createdBy");
	         
				itemStructure=new ItemStructure(structureId, lifecyclePhases, createdBy, creationDate, itemId);
	         }
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return itemStructure;

	}
	public List<ItemStructure> selectItemStructure(int id) throws IOException {
		List<ItemStructure> list = new ArrayList<ItemStructure>();
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_item_BY_ID);) {
			preparedStatement.setInt(1, id);
		

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int itemId = rs.getInt("id");
				int structureId = rs.getInt("structureId");
				int lifecyclePhases = rs.getInt("lifecyclePhases");
				Date creationDate = rs.getDate("creationDate");
				String createdBy = rs.getString("createdBy");

				list.add(new ItemStructure(structureId, lifecyclePhases, createdBy, creationDate, itemId));
			}
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return list;

	}

	public boolean updateItemStructure(ItemStructure item) throws SQLException, IOException {

	
		boolean rowUpdated = false;
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_itemS_SQL);) {

			preparedStatement.setInt(1, item.getLifecyclePhases());
			preparedStatement.setString(2, item.getCreatedBy());
			preparedStatement.setInt(3, item.getStructureId());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}

		catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return rowUpdated;

	}

	public boolean deleteItemStructure(int id) throws SQLException, IOException {
		boolean rowDeleted = false;
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_itemS_SQL);) {
			
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}

		catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return rowDeleted;
	}

}
