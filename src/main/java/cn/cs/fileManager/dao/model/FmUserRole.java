package cn.cs.fileManager.dao.model;

public class FmUserRole {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_user_role.user_role_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Long userRoleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_user_role.user_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Long userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_user_role.role_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Long roleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_user_role.user_role_status
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private String userRoleStatus;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_user_role.user_role_id
	 * @return  the value of fm_user_role.user_role_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Long getUserRoleId() {
		return userRoleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_user_role.user_role_id
	 * @param userRoleId  the value for fm_user_role.user_role_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_user_role.user_id
	 * @return  the value of fm_user_role.user_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_user_role.user_id
	 * @param userId  the value for fm_user_role.user_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_user_role.role_id
	 * @return  the value of fm_user_role.role_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_user_role.role_id
	 * @param roleId  the value for fm_user_role.role_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_user_role.user_role_status
	 * @return  the value of fm_user_role.user_role_status
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public String getUserRoleStatus() {
		return userRoleStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_user_role.user_role_status
	 * @param userRoleStatus  the value for fm_user_role.user_role_status
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setUserRoleStatus(String userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}
}