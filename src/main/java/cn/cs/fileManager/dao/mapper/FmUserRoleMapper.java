package cn.cs.fileManager.dao.mapper;

import cn.cs.fileManager.dao.model.FmUserRole;
import cn.cs.fileManager.dao.model.FmUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FmUserRoleMapper {
	
	/**
	 * find roles by userid
	 * select role from role join role_user
	 * */
	List<String> findRolesBYId(long id);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	long countByExample(FmUserRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int deleteByExample(FmUserRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int insert(FmUserRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int insertSelective(FmUserRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	List<FmUserRole> selectByExample(FmUserRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int updateByExampleSelective(@Param("record") FmUserRole record, @Param("example") FmUserRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_user_role
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int updateByExample(@Param("record") FmUserRole record, @Param("example") FmUserRoleExample example);
}