package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallLog;
import com.zjl.litemall.db.example.LitemallLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallLog selectOneByExample(LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallLog selectOneByExampleSelective(@Param("example") LitemallLogExample example, @Param("selective") LitemallLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallLog> selectByExampleSelective(@Param("example") LitemallLogExample example, @Param("selective") LitemallLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallLog> selectByExample(LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallLog record, @Param("example") LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallLog record, @Param("example") LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}