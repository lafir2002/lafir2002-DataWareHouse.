package com.datahub.mainhub; 



import java.util.Date;
import org.json.simple.JSONObject;
import com.datahub.mainhub.capsule.DataPojo;
import com.datahub.mainhub.validation.DataContainer;
import com.datahub.mainhub.validation.Constants;



public class DsProcess 
{
	


		private String dataStoreLoc = "";
		private String dataStoreName = "";

		/**
		 * 
		 *CONSTRUCTOR ==>DsProcess()INTIALIZE DATASTORE WITH DEFAULT STORAGE LOCATION
		 *
		 */
		public DsProcess() 
		{
			try
			{
				dataStoreLoc = Constants.defaultDataStoreLoc;
				dataStoreName = "datastore-" + DataContainer.getProcessName();
			}
			catch (Exception e) 
			{
            e.printStackTrace(); 
			}
		}

		/**
		 * 
		 *CONSTRUCTOR ==>DsProcess(FILE PATH) INTIALIZE DATASTORE WITH THE GIVEN STORAGE LOCATION

		 * @param
		 *      INSIDE THE PAREMETER=>(STORAGE LOCATION PATH)
		 */
		public DsProcess(String filePath) 
		{
			try 
			{
				dataStoreLoc = filePath;
				dataStoreName = "datastore-" + DataContainer.getProcessName();
			} 
			catch (Exception e) 
			{
                e.printStackTrace();
			}

		}


//_________________________________________________________________________________________________________		

//-----------------------------------  CREATE AN ELEMENT IN THE DATASTORE  --------------------------------------------------		
				
		/**
		 * METHOD TO CREATE AN ELEMENT IN THE <==DATASTORE==>
		 * @param key
		 *            THE KEY OF AN ELEMENT
		 * @param value
		 *            THE VALUE OF AN ELEMENT
		 * @return 
		 *           IT RETURNS THE STATUS OF AN OPERATION
		 */
		public synchronized String create(String key, JSONObject value)
		{
			try
			{
				return create(key, value, -1);
				
			} 
			catch (Exception exception)
			{
				return Constants.FAILURE_CREATE;
			}
		}

//-----------------------------------  CREATE AN ELEMENT IN THE DATASTORE (TTL)  -----------------------------------------------	
				
		/**
		 * THIS METHOD HELPS TO CREATE AN ELEMENT IN THE <==DATASTORE==>
		 * @param key
		 *            THE KEY OF AN ELEMENT
		 * @param value
		 *            THE VALUE OF AN ELEMENT
		 * @param timeToLive
		 *            NUMBER OF SECONDS AFTER -->ELEMENT DESTROYED         
		 * @return 
		 *           IT RETURNS THE STATUS OF AN OPERATION
		 * HERE WE INCLUDED TTL -->(KEY,VALUE,TTL)          
		 */
		public synchronized String create(String key, JSONObject value,int timeToLive) 
		{
			try 
			{
				String filePath = dataStoreLoc + "/" + dataStoreName;
				
				//IT CHECKS WHEATHER THE KEY IS VALIDATE OR NOT
				if (!DataContainer.isKeyNameValid(key))
				{
					return Constants.FAILURE_KEY_LENGTH_EXCEEDED;
				}
				if (DataContainer.isKeyExists(key, filePath)) 
				{
					return Constants.FAILURE_KEY_ALREADY_AVAILABLE;
				}
			 	//IF VALIDATE IT COMES HERE ==> SUCCESS FLOW OF CODE 

				DataPojo data = new DataPojo();
				data.setKey(key);
				if (timeToLive > 0) 
				{
					data.setTimeToLive(timeToLive);
				} 
				else 
				{
					data.setTimeToLive(-1);
				}
				data.setValue(value);
				data.setCreationDateTimeMillis(new Date().getTime());

				if (DataContainer.writeData(data, filePath)) 
				{
					return Constants.SUCCESS_CREATE;
				}
				else 
				{
					return Constants.FAILURE_CREATE;
				}
			} 
			catch (Exception exception) 
			{
				return Constants.FAILURE_CREATE;
			}
		}
		
//_________________________________________________________________________________________________________		

//-----------------------------------  READ FROM DATASTORE  --------------------------------------------------		
		
		
		/**
		 * 
		 *THIS METHOD HELPS TO READ A DATA FROM THE <==DATASTORE==>
		 * 
		 * @param key
		 * KEY HELPS TO READ AN ELEMENT      
		 * @return 
		 * HERE VALUE TYPE IS JSONObject
		 */
		public synchronized Object read(String key)
		{
			try
			{
				String filePath = dataStoreLoc + "/" + dataStoreName;
				
				//IT CHECKS WHEATHER THE KEY IS VALIDATE OR NOT
				if (!DataContainer.isKeyNameValid(key)) 
				{
					return Constants.FAILURE_KEY_LENGTH_EXCEEDED;
				}
				if (!DataContainer.isKeyExists(key, filePath)) 
				{
					return Constants.FAILURE_KEY_NOT_AVAILABLE;
				}
				
				//IF VALIDATE IT COMES HERE ==> SUCCESS FLOW OF CODE ==>READ
				DataPojo data = DataContainer.readData(key, filePath);
				if (null != data) 
				{
					return data.getValue();
				}
				return Constants.FAILURE_READ;
			 } 
			 catch (Exception exception) 
			 {
				exception.printStackTrace();
				return Constants.FAILURE_READ;
			 }
		}
//_________________________________________________________________________________________________________		

//-----------------------------------  DELETE FROM DATASTORE  --------------------------------------------------		
		
		/**
		 * THIS METHOD HELPS TO DELETE FROM THE <==DATASTORE==>
		 * @param 
		 * KEY HELPS TO READ AN ELEMENT           
		 * @return 
		 * IT REUTRNS THE STATUS OF DELETE OPERATION
		 */
		public synchronized Object delete(String key) 
		{
			try
			{
				String filePath = dataStoreLoc + "/" + dataStoreName;
				//IT CHECKS WHEATHER THE KEY IS VALIDATE OR NOT
				if (!DataContainer.isKeyNameValid(key)) 
				{
					return Constants.FAILURE_KEY_LENGTH_EXCEEDED;
				}
				if (!DataContainer.isKeyExists(key, filePath))
				{
					return Constants.FAILURE_KEY_NOT_AVAILABLE;
				}
				
				
				//IF VALIDATE IT COMES HERE ==> SUCCESS FLOW OF CODE ==>DELETE
				if (DataContainer.deleteData(key, filePath)) 
				{
					return Constants.SUCCESS_DELETE;
				}
				return Constants.FAILURE_DELETE;
			} 
			catch (Exception exception) 
			{
				exception.printStackTrace();
				return Constants.FAILURE_DELETE;
			}
			
		}
		
	}
//___________________________________X----X-X-X-X----X_____________________________________________________________________		
