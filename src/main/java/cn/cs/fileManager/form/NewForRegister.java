package cn.cs.fileManager.form;

import java.util.List;

import lombok.Data;

@Data
public class NewForRegister {
	 	
	private String loginName;
	
	private String password;
	
	private String mobileNumber;
		
	private String name;

   //role id valid 都是自动插入的
}
