package com.databasecrud.databases;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.databasecrud.models.ItemAttachment;
import org.apache.log4j.Logger;


public class ItemAttachmentDatabase {
	public static final String FILENAME = "/home/dev/Downloads/TemplateProject/src/com/databasecrud/databases/database.properties";	 
	private static final String INSERT_itemS_SQL="INSERT INTO itemattachment"+"(id,attachmentName,attachmentSize) VALUES"+"(?,?,?);";
	private static final String SELECT_item_BY_ID="SELECT * FROM itemattachment WHERE id=?";
	private static final String DELETE_itemS_SQL="DELETE from itemattachment WHERE attachmentId=?;";
	private static final String UPDATE_itemS_SQL="UPDATE itemattachment set attachmentName=?,attachmentSize=? WHERE attachmentId=?;";
	
	private static  Logger 	logger = Logger.getLogger(ItemAMLDatabase.class.getName());
	
	public ItemAttachmentDatabase() {
		
	}
	
	public long insertAttachment(ItemAttachment item) throws SQLException, IOException {
		long id=0;
		try(Connection connection=HelperClass.getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_itemS_SQL,PreparedStatement.RETURN_GENERATED_KEYS)	) {
			preparedStatement.setInt(1, item.getId());
			preparedStatement.setString(2, item.getAttachmentName());
			preparedStatement.setInt(3,item.getAttachmentSize());
			
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
	
	public List<ItemAttachment> selectItemAttachment(int id) throws IOException {
		List<ItemAttachment> list=new ArrayList<ItemAttachment>();
		try(Connection connection=HelperClass.getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(SELECT_item_BY_ID);	) {
				preparedStatement.setInt(1, id);
				
				ResultSet rs=preparedStatement.executeQuery();
				while(rs.next()) {
					int itemId=rs.getInt("id");
					int attachmentId=rs.getInt("attachmentId");
					String attachmentName=rs.getString("attachmentName");
					int attachmentSize=rs.getInt("attachmentSize");
					
					list.add(new ItemAttachment(attachmentId,attachmentSize,attachmentName,itemId));
				}
				}
			catch(SQLException e){
				logger.error("Exception is: ", e);
			}
		return list;
	
	}
	
	public boolean updateItemAttachment(ItemAttachment item) throws SQLException, IOException {
		
		
		boolean rowUpdated=false;
		try(Connection connection=HelperClass.getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_itemS_SQL);	) {
		
			preparedStatement.setString(1, item.getAttachmentName());
			preparedStatement.setInt(2,item.getAttachmentSize());
			preparedStatement.setInt(3, item.getAttachmentId());
				
				rowUpdated=preparedStatement.executeUpdate()>0;
				}
				
			catch(SQLException e){
				logger.error("Exception is: ", e);
			}
		return rowUpdated;
		
	}
	
	public boolean deleteItemAttachment(int id) throws SQLException, IOException{
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
