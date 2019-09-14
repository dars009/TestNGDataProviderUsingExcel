package com.dataproviderDemo;

import org.testng.annotations.DataProvider;

public class Repo {
	
	@DataProvider
	//return type of this method is object 2D array*******
	public Object[][] logindata() {
	  
		return new Object[][] {
	    
			new Object[] { "TC01", "BlankUser","","","Please enter email." }, //whenever u provide data to method then data is always consistent.Both will
	    
			new Object[] { "TC02", "SpecialUser" ,"&^&^","fgfhg","Please enter email as kiran@gmail.com"},
	    
			new Object[] { "TC03", "ValidUser" ,"kiran@gmail.com","123456","AdminLTE 2 | Dashboard"},
	  };
	  }
	
}
