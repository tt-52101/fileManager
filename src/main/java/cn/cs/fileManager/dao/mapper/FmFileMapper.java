package cn.cs.fileManager.dao.mapper;

import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FmFileMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	long countByExample(FmFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int deleteByExample(FmFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int insert(FmFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int insertSelective(FmFile record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	List<FmFile> selectByExample(FmFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int updateByExampleSelective(@Param("record") FmFile record, @Param("example") FmFileExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table fm_file
	 * @mbg.generated  Fri May 24 14:57:35 CST 2019
	 */
	int updateByExample(@Param("record") FmFile record, @Param("example") FmFileExample example);
}