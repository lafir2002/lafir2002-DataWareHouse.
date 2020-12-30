package com.datahub.mainhub;
import java.io.*;
import org.json.simple.JSONObject;







public class MainDsAccess {
//---------------------------------  DIRECT STATEMENT -----------------------------------//
	public static void main(String[] args) {
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
//_____________________________________________________________________________________________________________________________________

//--------------------------------------------------- OTHER METHOD --------------------------------------------------------------------
/**
 * @DoneBy 
 * MOHAMED LAFIR ALI
 */
 /*public class MainDsAccess 
 
{
	
//---------------------------  USER INPUT ----------------------------------------//
		public static void main(String[] args) {
		
		
		  try
		 
			{
					
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
	        while(true)
			{	
			
			JSONObject jsonObj = new JSONObject();
			System.out.println("ENTER YOUR FIRST NAME:");  
			String fname=br.readLine();  
			  
			jsonObj.put("firstName",fname );
			System.out.println("ENTER YOUR LAST NAME:");  
			String lname=br.readLine();  
			  
			jsonObj.put("lastName", lname);
			System.out.println("ENTER YOUR QUALIFICATION:");  
			String qual=br.readLine();  
			jsonObj.put("Qualification",qual);
			
			System.out.println("ENTER YOUR AGE:");  
			String age=br.readLine();  
			jsonObj.put("Age",age);
			
			
			
			// CREATE 
			
			
			DsProcess myDataStore = new DsProcess("C:\\Users\\91883\\OneDrive\\Documents\\DataStore");
			BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));  
           
			
			
			
			System.out
			.println("_____________________________________________________________");
	System.out
			.println("**********************  CREATE  *****************************");
	System.out
			.println("_____________________________________________________________");
	        System.out.println("CREATE--T-->Enter Head Key1:");//1--DELETE-->DUE TO TIME OUT SO WE AGAIN INSERT SAME KEY
	        String ke1=br.readLine();
			System.out.println(myDataStore.create(ke1, jsonObj, 10));
			
			System.out.println("CREATE--->Enter Key2:");//2--NORMAL
			String ke2=br.readLine();
			System.out.println(myDataStore.create(ke2, jsonObj));
			
			System.out.println("CREATE--T-->Enter Key3:");//3--DELETE-->DUE TO TIME OUT SO WE AGAIN INSERT SAME KEY
			String ke3=br.readLine();
			System.out.println(myDataStore.create(ke3, jsonObj, 10));
			
			System.out.println("CREATE--->Enter Key4:");//4--NORMAL
			String ke4=br.readLine();
			System.out.println(myDataStore.create(ke4, jsonObj));
			
			
			System.out.println("Enter the Comments Or Any Sentence--->Character Test1:");
			String cstr=br.readLine();
			System.out.println(myDataStore.create(cstr, new JSONObject()));
			
			try 
			{
		    // wait for 05 seconds
			Thread.sleep(5000);
			}catch (InterruptedException e) {e.printStackTrace();}
		    
			System.out.println("\n");
			System.out.println("-------------------- AFTER WAIT ---------------------------");
			
			//CREATE A NEW DATA
			System.out.println("ENTER YOUR YEARS OF EXPERIENCE:");  
			String exp=br.readLine();  
			jsonObj.put("Experience:",exp); 
	        System.out.println("\n");
			System.out.println();
			System.out.println("AFTER WAIT--T-->CREATE-->KEY 1:");//1-->DONE BECAUSE ALREADY DELETED IN 10 SEC  
			String ake1=br.readLine(); //AGAIN DELETE IN 10 SEC 
			System.out.println(myDataStore.create(ake1, jsonObj, 10));//SO UNABLE TO READ THIS-->1
			
			
			System.out.println("\nAFTER WAIT--> CREATE-->KEY 2:");//3-->DONE BECAUSE ALREADY DELETED IN 10 SEC
			String ake2=br.readLine();//STANDARD KEY
			System.out.println(myDataStore.create(ake2, jsonObj));//ALL DATA
			
			
			
			System.out.println("\nAFTER WAIT--T-->CREATE-->KEY 3:");//5-->NEW LAUNCHED KEY BUT SOON DELETE IN 10 SEC
			String ake3=br.readLine(); //AGAIN DELETE IN 10 SEC 
			System.out.println(myDataStore.create(ake3, jsonObj, 10));//SO UNABLE TO READ THIS-->5
			
			
			
			System.out.println("\nAFTER WAIT-->CREATE-->KEY 4:");//6-->NEW LAUNCHED KEY 
			String ake4=br.readLine();//STANDARD KEY 
			System.out.println(myDataStore.create(ake4, jsonObj));//ALL  DATA
	        
	        System.out.println("\n");
			
	        
	        
	        while(true)
	        {	
	        System.out.println("ENTER YOUR OPTION:\n1:READ\n2:DELETE");  
			String opt=br.readLine(); 
			switch(opt)
			{	
			case "1":
	        {	
			// READ
	        
			System.out
					.println("_____________________________________________________________");
			System.out
					.println("************************  READ  *****************************");
			System.out
					.println("_____________________________________________________________");
			//IF ENTERING KEY = STANDARD KEY=>GET ALL DATA
			System.out.println("READ--->key1:\n\t");//1	KEY NOT FOUND IN AW-C		
			String rke1=br.readLine();
			System.out.println(myDataStore.read(rke1));
			
			System.out.println("\nREAD--->key2:\n\t");//3 ALL DATA
			String rke2=br.readLine();
			System.out.println(myDataStore.read(rke2));
			
			System.out.println("\nREAD--->key3:\n\t");//5 KEY NOT FOUND IN AW-C
			String rke3=br.readLine();//BECAUSE IT WILL DELETE-->DUE TO TIME OUT
			System.out.println(myDataStore.read(rke3));

			System.out.println("\nREAD--->key4:\n\t");//6 ALL DATA
			String rke4=br.readLine();
			System.out.println(myDataStore.read(rke4));

			System.out.println("\nEnter The Comments--->Character Test2:");
			String rstr=br.readLine();
			System.out.println(myDataStore.create(rstr, new JSONObject()));


			try 
			{
			// wait for 05 seconds
			Thread.sleep(5000);
			} catch (InterruptedException e) {e.printStackTrace();}
			
			System.out.println("\n");
			System.out.println("--------------------- AFTER WAIT --------------------------");
			System.out.println("AFTER WAIT-->READ-->KEY 1:");//3-->ALL DATA
			String arke1=br.readLine();
			System.out.println(myDataStore.read(arke1));
			
			System.out.println("\nAFTER WAIT-->READ-->KEY 2:");//6-->ALL DATA
			String arke2=br.readLine();
			System.out.println(myDataStore.read(arke2));
			
			System.out.println("\nAFTER WAIT-->READ-->KEY 3:");//2-->1st CREATED DATA ONLY
			String arke3=br.readLine();// NOT UPDATED DATA
			System.out.println(myDataStore.read(arke3));//-->BECAUSE THIS KEY IS NOT PRESENT IN AW-C
            
            System.out.println("\nAFTER WAIT-->READ-->KEY 4:");//4-->1st CREATED DATA ONLY
			String arke4=br.readLine();//NOT UPDATED DATA
			System.out.println(myDataStore.read(arke4));//-->BECAUSE THIS KEY IS NOT PRESENT IN AW-C
	        System.out.println("\n");
	        break;
	
	        }
	        
	        
	        //THE DELETE KEY SHOULD BE EXISTED IN C AND AW-C-->AFTER-WAIT--CREATE
	        //IT SHOULD NOT COMES UNDER TTL-->TIME-TO-LIVE
	        //THE DELETE KEY SHOULD NOT REPEAT AGAIN, ONCE THE KEY DELETED SUCCESSFULLY
	        
	        case "2":
	        {	
	       	
	        System.out
					.println("_____________________________________________________________");
			System.out
					.println("**********************  DELETE  *****************************");
			System.out
					.println("_____________________________________________________________");
			System.out.println("\nDELETE--->Enter the valid key1:");//1-->KEY IS NOT FOUND
			String dke1=br.readLine();	
			System.out.println(myDataStore.delete(dke1));
			
			System.out.println("\nDELETE--->Enter the valid key2:");//2-->DELETED SUCCESSFULLY
			String dke2=br.readLine();
			System.out.println(myDataStore.delete(dke2));
			
			System.out.println("\nDELETE--->Enter the valid key3:");//3-->DELETED SUCCESSFULLY
			String dke3=br.readLine();
			System.out.println(myDataStore.delete(dke3));
			
			System.out.println("\nDELETE--->Enter the valid key4:");//4-->DELETED SUCCESSFULLY
			String dke4=br.readLine();
			System.out.println(myDataStore.delete(dke4));
			
			//System.out.println("Enter the Comments--->Character Test 3:");
			//String dstr=br.readLine();
			//System.out.println(myDataStore.create(dstr, new JSONObject()));
			
			
			break;
	        }
			}//switch
			
	      

			
	        
			System.out.println("Want to continue with option y/n");  
			String ans1=br.readLine();  
			if(ans1.equals("n")){break;}
	        }//while-sub
	        
	        
	        System.out.println("Want to add more records in a file y/n");  
			String ans2=br.readLine();  
			if(ans2.equals("n")){System.out.println("THANKS FOR VISITING..");break;}
			}//while-main
		  
		
		}catch(IOException e) {}
		
		
		   
		}//main method

		
	}//class
	
	

*/