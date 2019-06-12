package cn.cs.fileManager.vo;


import java.util.List;
import lombok.Data;

@Data
public class FmUserVO {

	private Long id;
	
	private List<String> roles;
}
