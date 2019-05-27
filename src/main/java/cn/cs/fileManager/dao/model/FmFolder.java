package cn.cs.fileManager.dao.model;

import java.util.Date;

public class FmFolder {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.folder_name
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private String folderName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.p_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Long pId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.base_dir
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private String baseDir;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.reg_date
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Date regDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.reg_account
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private Long regAccount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.description
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private String description;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column fm_folder.valid
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	private String valid;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.id
	 * @return  the value of fm_folder.id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.id
	 * @param id  the value for fm_folder.id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.folder_name
	 * @return  the value of fm_folder.folder_name
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.folder_name
	 * @param folderName  the value for fm_folder.folder_name
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.p_id
	 * @return  the value of fm_folder.p_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.p_id
	 * @param pId  the value for fm_folder.p_id
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.base_dir
	 * @return  the value of fm_folder.base_dir
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public String getBaseDir() {
		return baseDir;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.base_dir
	 * @param baseDir  the value for fm_folder.base_dir
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.reg_date
	 * @return  the value of fm_folder.reg_date
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.reg_date
	 * @param regDate  the value for fm_folder.reg_date
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.reg_account
	 * @return  the value of fm_folder.reg_account
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public Long getRegAccount() {
		return regAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.reg_account
	 * @param regAccount  the value for fm_folder.reg_account
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setRegAccount(Long regAccount) {
		this.regAccount = regAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.description
	 * @return  the value of fm_folder.description
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.description
	 * @param description  the value for fm_folder.description
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column fm_folder.valid
	 * @return  the value of fm_folder.valid
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public String getValid() {
		return valid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column fm_folder.valid
	 * @param valid  the value for fm_folder.valid
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
}