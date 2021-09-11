package com.databasecrud.databases;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.databasecrud.models.ItemAML;
import org.apache.log4j.Logger;


public class ItemAMLDatabase {
	private static final String INSERT_itemS_SQL="INSERT INTO itemAML"+"(id,manufacturerPart,manufacturer) VALUES"+"(?,?,?);";
	private static final String SELECT_item_BY_ID="SELECT * FROM itemAML WHERE id=?";
	private static final String DELETE_itemS_SQL="DELETE from itemAML WHERE registryId=?;";
	private static final String UPDATE_itemS_SQL="UPDATE itemAML set manufacturerPart=?,manufacturer=? WHERE registryId=?;";
	
	private static  Logger 	logger = Logger.getLogger(ItemAMLDatabase.class.getName());
	
	public ItemAMLDatabase() {
		
	}
	
	public long insertAML(ItemAML aml) throws SQLException, IOException {
		long id=0;
		try(Connection connection=HelperClass.getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_itemS_SQL,PreparedStatement.RETURN_GENERATED_KEYS)	) {
			preparedStatement.setInt(1, aml.getId());
			preparedStatement.setString(2, aml.getManufacturer());
			preparedStatement.setString(3, aml.getManufacturerPart());
			
			int updated = preparedStatement.executeUpdate();
			 ResultSet generatedKeysResultSet = preparedStatement.getGeneratedKeys();
			 generatedKeysResultSet.next();
			  id = generatedKeysResultSet.getLong(1);
			
			
			}
		catch(SQLException e){
			logger.error("Exception is: ", e);
		}
		 return id;
	}	
	
	public List<ItemAML> selectItemAML(int id) throws IOException {
		List<ItemAML> list=new ArrayList<ItemAML>();
		try(Connection connection=HelperClass.getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(SELECT_item_BY_ID);	) {
				preparedStatement.setInt(1, id);
				
				ResultSet rs=preparedStatement.executeQuery();
				while(rs.next()) {
					int itemId=rs.getInt("id");
					int registryId=rs.getInt("registryId");
					String manufacturerPart=rs.getString("manufacturerPart");
					String manufacturer=rs.getString("manufacturer");
					
					list.add(new ItemAML(registryId,manufacturerPart,manufacturer,itemId));
				}
				}
			catch(SQLException e){
				logger.error("Exception is: ", e);
			}
		return list;
	
	}
	
	public boolean updateItem(ItemAML item) throws SQLException, IOException {
		
		
		boolean rowUpdated=false;
		try(Connection connection=HelperClass.getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_itemS_SQL);	) {
				preparedStatement.setString(1,item.getManufacturerPart());
				preparedStatement.setString(2, item.getManufacturer());
				preparedStatement.setInt(3, item.getRegistryId());
				
				rowUpdated=preparedStatement.executeUpdate()>0;
				}
				
			catch(SQLException e){
				logger.error("Exception is: ", e);
			}
		return rowUpdated;
		
	}
	
	public boolean deleteItem(int id) throws SQLException, IOException{
		boolean rowDeleted=false;
		try(Connection connection=HelperClass.getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(DELETE_itemS_SQL);	) {
				
				preparedStatement.setInt(1, id);
				rowDeleted=preparedStatement.executeUpdate()>0;
				}
				
			catch(SQLException e){
				logger.error("Exception is: ", e);
			}
		return rowDeleted;
	}
	
	
}
