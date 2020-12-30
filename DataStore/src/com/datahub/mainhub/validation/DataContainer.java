package com.datahub.mainhub.validation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;

import com.datahub.mainhub.capsule.DataPojo;
import com.laughir.Main.Bean.Data;
import com.laughir.Main.Utils.Constants;


public class DataContainer 
{
	/**
	 * 
	 * IT HELPS TO GET THE CURRENT-- processName
	 * @return 
	 *HERE processName as--String
	 */
	public static String getProcessName() 
	{
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		return processName;
	}
//_________________________________________________________________________________________________________

//-------------------------------------   VALIDATION  -----------------------------------------------------		
	/**
	 * 
	 * IT HELPS TO VALIDATE THE KEY IN ==DATASTORE==
	 * @param key
	 *            KEY OF AN ELEMENT
	 * @return 
     *    IT RETURNS THE DELETION STATUS EITHER TRUE
	 *                                      OR FALSE
	 */
	public static boolean isKeyNameValid(String key) 
	{
		if (key.length() > Constants.KEY_MAX_LENGTH) 
		{
			return false;
		}
		return true;
	}

	/**
	 * 
	 * IT HELPS TO CHECK WHEATHER THE ENTERED KEY IS EXIST OR NOT IN  ==DATASTORE==
	 * @param key
	 *            KEY OF AN ELEMENT
	 * @param filePath
	 *            THE DATASTORE LOCATION IN OUR LAPTOP
	 *            
	 * @return 
	 * IT RETURNS TRUE IF KEY IS EXIST 
	 *            ELSE FALSE
	 */
	public static boolean isKeyExists(String key, String filePath) 
	{
		
		boolean isKeyExists = false;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		ObjectInputStream objectInputStream = null;
		ObjectOutputStream objectOutputStream = null;
		HashMap<String, DataPojo> dataMap = new HashMap<String, DataPojo>();//Thread-Safe
		
		try 
		{
			File file = new File(filePath);
			// CHECK IF FILE EXIST
			if (file.exists()) 
			{
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, DataPojo>) objectInputStream.readObject();
				
				// CHECK IF KEY EXIST
				if (dataMap.containsKey(key)) 
				{
					isKeyExists = true;
				}

				fileInputStream.close();
				objectInputStream.close();
			}

			
			//IT VALIDATE TTL(TIME-TO-LIVE) &
			//IF THE GIVEN TIME EXPIRED-->IT DESTROYS THE OBJECT
			if (isKeyExists) 
			{
				DataPojo data = dataMap.get(key);
				long currentDateTimeMillis = new Date().getTime();
				if (data.getTimeToLive() > 0
						  && (currentDateTimeMillis - data.getCreationDateTimeMillis()) 
						  >= (data.getTimeToLive() * Constants.MILLISECONDS))
				{
					
					
					//OBJECT IS EXPIRED-->REMOVE OBJ FROM DATASTORE
					dataMap.remove(key);
					fileOutputStream = new FileOutputStream(file);
					objectOutputStream = new ObjectOutputStream(fileOutputStream);
					objectOutputStream.writeObject(dataMap);
					fileOutputStream.close();
					objectOutputStream.close();

					
					//SINCE OBJECT IS REMOVED KEY IS AVAILABLE FOR STORAGE
					isKeyExists = false;
				}
			}
		}catch (Exception exception) {exception.printStackTrace();}
		
		
		finally 
		{
			if (fileInputStream != null) 
			{
				try 
				{
					fileInputStream.close();
				} catch (IOException e) {e.printStackTrace();}
			
			}
			
			if (objectInputStream != null) 
			{
				try 
				{
					objectInputStream.close();
				} catch (IOException e) {e.printStackTrace();}
			}
		}
		return isKeyExists;
	}
//________________________________________________________________________________________________________

//------------------------------------- WRITE AN ELEMENT FROM DATASTORE ----------------------------------		
	/**
	 * WRITE A DATA IN THE ==DATASTORE==
	 * 
	 * @param data
	 *            IT HELPS TO WRITE AN ELEMENT IN DATASTORE
	 * @param filePath
	 *            THE DATASTORE LOCATION IN OUR LAPTOP
	 * @return 
	 *        IT RETURNS THE DELETION STATUS TRUE--ELEMENT IS DELETED
	 *                                OR   FALSE--ELEMENT IS NOT DELETED 
	 */
	public static boolean writeData(DataPojo data, String filePath) 
	{
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String,DataPojo> dataMap = null;
		try 
		{
			File file = new File(filePath);
			if (file.exists()) 
			{
				//READ THE DATA FROM THE EXISTING FILE
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, DataPojo>) objectInputStream.readObject();

				fileInputStream.close();
				objectInputStream.close();

				// ADD THE ELEMENT
				dataMap.put(data.getKey(), data);

				// WRITE THE DATA IN FILE
				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
			else 
			{
				dataMap = new HashMap<String, DataPojo>();
				dataMap.put(data.getKey(), data);

				// WRITE THE DATA IN FILE
				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
		} catch (Exception exception) {return false;} 
		
		
		finally
		{
			if (fileInputStream != null) 
			{
				try 
				{
					fileInputStream.close();
				} catch (IOException e) {e.printStackTrace();}
			}
			if (fileOutputStream != null)
			{
				try 
				{
					fileOutputStream.close();
				} catch (IOException e){e.printStackTrace();}
			
			}
			
			if (objectInputStream != null) 
			{
				try 
				{
					objectInputStream.close();
				} catch (IOException e) {e.printStackTrace();}
			}
			
			if (objectOutputStream != null) 
			{
				try 
				{
				    objectOutputStream.close();
				} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
//_______________________________________________________________________________________________________

//------------------------------------- READ AN ELEMENT FROM DATASTORE ----------------------------------		
	/**
	 * 
	 *           READ AN ELEMENT FROM DATASTORE
	 * 
	 * @param key
	 *            HELPS TO READ AN ELEMENT
	 * @param filePath
	 *            THE DATASTORE LOCATION IN OUR LAPTOP
	 * @return 
	 *         RETURNS THE ELEMENT IF IT'S AVAILABLE 
	 *         ELSE REURNS --NULL   
	 */
	public static DataPojo readData(String key, String filePath) 
	{
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, DataPojo> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) 
			{
				// READ A DATA FROM EXISTING FILE
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, DataPojo>) objectInputStream.readObject();

				fileInputStream.close();
				objectInputStream.close();
				return dataMap.get(key);
			
			} 
			else {return null;}
		}catch (Exception exception) {exception.printStackTrace();return null;}
		
		
		finally 
		{
			if (fileInputStream != null)
			{
				try 
				{
					fileInputStream.close();
				
				}catch (IOException e) {e.printStackTrace();}
			}
			if (objectInputStream != null) 
			{
				try 
				{
				objectInputStream.close();
				} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
//_________________________________________________________________________________________________________

//------------------------------------- DELETE AN ELEMENT FROM DATASTORE ----------------------------------	
	/**
	 * 
	 *            DELETE AN ELEMENT FROM THE DATASTORE
	 * @param key
	 *            
	 *            KEY--DELETE THE ELEMENT FROM DATASTORE
	 * @param filePath
	 *            
	 *            LOCATION OF THE DATASTORE IN OUR LAPTOP
	 * @return 
	 *      IT RETURNS THE DELETION STATUS TRUE--ELEMENT IS DELETED
	 *                                OR   FALSE--ELEMENT IS NOT DELETED 
	 * 
	 */
	public static boolean deleteData(String key, String filePath) {

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, DataPojo> dataMap = null;// THREAD-SAFE
		
		try 
		{
			File file = new File(filePath);
			if (file.exists()) 
			{
				// READ THE DATA FROM AN EXISTING FILE
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, DataPojo>) objectInputStream.readObject();

				
				fileInputStream.close();
				objectInputStream.close();

				// REOMOVES A KEY
				dataMap.remove(key);

				
				
				// WRITE THE DATA INSIDE THE FILE
				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
		       return false;
		} catch (Exception exception) {return false;}
		
		
		finally 
		{
				if (fileInputStream != null) 
				{
						try
						{
						fileInputStream.close();
						} catch (IOException e) {e.printStackTrace();}
				}
				if (fileOutputStream != null)
				{
						try 
						{
							fileOutputStream.close();
						} catch (IOException e) {e.printStackTrace();}
			    }
				
				if (objectInputStream != null) 
				{
						try 
						{
						objectInputStream.close();
						} catch (IOException e) {e.printStackTrace();}
				}
				
			    if (objectOutputStream != null) 
			    {
						try 
						{
							objectOutputStream.close();
						} catch (IOException e) {e.printStackTrace();}
				}
		}

	
	
	}
}
//___________________________________X----X-X-X-X----X_____________________________________________________________________		
