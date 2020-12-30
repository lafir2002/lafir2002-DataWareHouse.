package testCode;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import com.datahub.mainhub.DsProcess;

class MainTest1_Direct {
//----------------------------------- DIRECT INPUT -------------------------------------------------
	@Test
	void test() {

			try
			{
		
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("EmpId", "222");
			jsonObj.put("EmpName", "Mohamed Lafir Ali");
			jsonObj.put("Qualification","M.sc Computer Science");
			jsonObj.put("Age", "21");

			
			
			System.out
					.println("_____________________________________________________________");
			System.out
					.println("**********************  CREATE  *****************************");
			System.out
					.println("_____________________________________________________________");
			
			//CREATE DATA
			DsProcess myDataStore = new DsProcess("C:\\Users\\91883\\OneDrive\\Documents\\DataStore");
			
			System.out.println(myDataStore.create("1", jsonObj, 10));// success TTT
			System.out.println(myDataStore.create("1", jsonObj));// same key so fail
			System.out.println(myDataStore.create("2", jsonObj, 10));// success unique TTT
			System.out.println(myDataStore.create("3", jsonObj));// success unique key
			System.out.println(myDataStore.create("Hello DataStore This Key exceeds the limit", new JSONObject()));// fail due to exceed 
			try
			{
			// wait for 05 seconds
			Thread.sleep(5000);
			}catch (InterruptedException e) {e.printStackTrace();}
			
			System.out.println("\n");
			System.out.println("-------------------- AFTER WAIT ---------------------------");
			jsonObj.put("Designation", "Software Developer");
			System.out.println(myDataStore.create("1", jsonObj, 10));// fail due to key already available
			System.out.println(myDataStore.create("1", jsonObj));// fail due to key already available
			System.out.println(myDataStore.create("4", jsonObj, 10));// unique success
			System.out.println(myDataStore.create("5", jsonObj));// unique success
	       System.out.println("\n\n\n");
			
			
	       
	       
	       
			// READ DATA
			System.out
					.println("_____________________________________________________________");
			System.out
					.println("************************  READ  *****************************");
			System.out
					.println("_____________________________________________________________");
			System.out.println(myDataStore.read("5"));//AW-C[AFTER WAIT-CREATION] success get all data 
			System.out.println(myDataStore.read("2"));//C[ONLY CREATION] success but get only first creation data (2)
			System.out.println(myDataStore.read("3"));//C  success get first creation data (3)
			System.out.println(myDataStore.read("1"));//C  success get first creation data (3)
			System.out.println(myDataStore.read("4"));//AW-C  success get all data
			System.out.println(myDataStore.read("4"));//success key repeat allowed but it should be in C & AW-C
			System.out.println(myDataStore.read("6"));//fail because key not available in both C & AW-C

			System.out.println(myDataStore.read("HI!DataStore Hope Not Exceed"));// success
			
			
			try 
			{
			// wait for 05 seconds
			Thread.sleep(5000);
			} catch (InterruptedException e) {e.printStackTrace();}
			
			System.out.println("\n");
			System.out.println("--------------------- AFTER WAIT --------------------------");
			System.out.println(myDataStore.read("1"));// failed due to key not available because of TimeOut IN CREATION
			System.out.println(myDataStore.read("2"));// failed due to key not available because of TimeOut IN CREATION
			System.out.println(myDataStore.read("3"));// C success but  get only first creation [DATA]
			System.out.println(myDataStore.read("4"));// AW-C  success get all [DATA]+[UPDATED DATA]
			System.out.println(myDataStore.read("4"));// key repeat is allowed in AW-R process[DATA]+[UPDATED DATA]
			System.out.println(myDataStore.read("5"));// AW-C  success get all data[DATA]+[UPDATED DATA]
			System.out.println(myDataStore.read("6"));// failed due to key not available in both AW-C & C

	       System.out.println("\n\n\n");
			
	       
	       
	       

	       System.out
					.println("_____________________________________________________________");
			System.out
					.println("**********************  DELETE  *****************************");
			System.out
					.println("_____________________________________________________________");
			
			System.out.println(myDataStore.delete("1"));// Operation failed due to key not available 
			System.out.println(myDataStore.delete("2"));// Operation failed due to key not available
			System.out.println(myDataStore.delete("3"));// Record deletion successful
			System.out.println(myDataStore.delete("4"));// Record deletion successful
			System.out.println(myDataStore.delete("5"));// Record deletion successful
			System.out.println(myDataStore.delete("5"));// Operation failed due to key not available because we already deleted 
			System.out.println(myDataStore.delete("6"));// Operation failed due to key not available

			
			System.out.println(myDataStore.delete("THIS WILL RUN WITHOUT AN ERROR ONLY IF THE GIVEN CHARACTER IS NOT EXCEEDED(32CHAR)..BUT"));// failure
		
		 }catch(Exception e) {}
		
	

		}
		}
