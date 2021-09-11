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

public class ItemDatabase {

	public static final String FILENAME = "/home/dev/Downloads/TemplateProject/src/com/databasecrud/databases/database.properties";

	private static final String INSERT_itemS_SQL = "INSERT INTO items" + "(itemClass,itemDescription) VALUES"
			+ "(?,?);";
	private static final String SELECT_item_BY_ID = "SELECT itemNumber,itemClass,itemDescription FROM items WHERE itemNumber=?";
	private static final String SELECT_ALL_itemS = "SELECT * FROM items";
	private static final String DELETE_itemS_SQL = "DELETE from items WHERE itemNumber=?;";
	private static final String UPDATE_itemS_SQL = "UPDATE items set itemClass=?, itemDescription=? WHERE itemNumber=?;";
	private Logger 	logger = Logger.getLogger(ItemDatabase.class.getName());
	
	public ItemDatabase() {

	}

	
	public long insertItem(Item item) throws SQLException, IOException {
		
		long id = 0;
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_itemS_SQL,
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, item.getItemClass());
			preparedStatement.setString(2, item.getItemDescription());

			int updated = preparedStatement.executeUpdate();
			ResultSet generatedKeysResultSet = preparedStatement.getGeneratedKeys();
			generatedKeysResultSet.next();
			id = generatedKeysResultSet.getLong(1);

		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return id;
	}
	public Item selectItem(int id) throws IOException {
		Item item = null;

		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_item_BY_ID);) {
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int itemNumber = rs.getInt("itemNumber");
				String itemClass = rs.getString("itemClass");
				String itemDescription = rs.getString("itemDescription");

				item = new Item(itemNumber, itemClass, itemDescription);
			}
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return item;

	}

	public List<Item> selectAllItems() throws IOException {
		List<Item> items = new ArrayList<>();

		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_itemS);) {

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int itemNumber = rs.getInt("itemNumber");
				String itemClass = rs.getString("itemClass");
				String itemDescription = rs.getString("itemDescription");

				items.add(new Item(itemNumber, itemClass, itemDescription));
			}
		} catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return items;

	}

	public boolean updateItem(Item item) throws SQLException, IOException {
		boolean rowUpdated = false;
		try (Connection connection = HelperClass.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_itemS_SQL);) {

			preparedStatement.setString(1, item.getItemClass());
			preparedStatement.setString(2, item.getItemDescription());
			preparedStatement.setInt(3, item.getItemNumber());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}

		catch (SQLException e) {
			logger.error("Exception is: ", e);
		}
		return rowUpdated;

	}

	public boolean deleteItem(int id) throws SQLException, IOException {
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
