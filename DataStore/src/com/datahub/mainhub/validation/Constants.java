package com.datahub.mainhub.validation;



public class Constants {
	
		public static final String defaultDataStoreLoc = "C:\\Users\\Public\\Documents";
		public static final int MILLISECONDS = 1000;
		public static final int KEY_MAX_LENGTH = 32;

		// Messages
		public static final String FAILURE_KEY_LENGTH_EXCEEDED = "Oops!Unable to proceed due to key length exceeded the limit(32chars)";
		public static final String FAILURE_KEY_ALREADY_AVAILABLE = "Oops!Unable to proceed due to key already exist,Please try with unique key";
		public static final String FAILURE_KEY_NOT_AVAILABLE = "Oops!Unable to proceed due to key not found,Please enter valid key.";
		public static final String SUCCESS_CREATE = "Congrats:Record Created successfully";
		public static final String FAILURE_CREATE = "Oops!Failed to create a record.PLease try again";
		//public static final String SUCCESS_READ = " Congrats:You are allowed to read the record ";
		public static final String FAILURE_READ = "Oops!Failed to read a record.PLease try again";
		public static final String SUCCESS_DELETE = "Congrats:Record deleted successfully";
		public static final String FAILURE_DELETE = "Oops!Failed to delete a record.PLease try again";
	}



