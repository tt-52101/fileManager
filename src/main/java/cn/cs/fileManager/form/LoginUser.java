package cn.cs.fileManager.form;
import lombok.Data;

@Data
public class LoginUser {
	/**
     * 用户手机号码
     */
    private String loginName;
   

    /**
     * 是否记住密码
     */
    private Boolean remember;
}
