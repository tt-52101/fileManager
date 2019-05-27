package cn.cs.fileManager.vo;

import java.util.List;

import lombok.Data;

@Data
public class LoginSuccessVO {
	 /**
     * 用户编号
     */
    private long userId;
    
    private String password;
    
    private String mobileNumber;

	 /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户名
     */
    private String name;
}
