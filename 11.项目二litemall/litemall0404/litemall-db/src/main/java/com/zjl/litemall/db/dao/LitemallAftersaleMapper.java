package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallAftersale;
import com.zjl.litemall.db.example.LitemallAftersaleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallAftersaleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallAftersale selectOneByExample(LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallAftersale selectOneByExampleSelective(@Param("example") LitemallAftersaleExample example, @Param("selective") LitemallAftersale.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallAftersale> selectByExampleSelective(@Param("example") LitemallAftersaleExample example, @Param("selective") LitemallAftersale.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallAftersale> selectByExample(LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallAftersale selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallAftersale.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallAftersale selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallAftersale selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallAftersale record, @Param("example") LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallAftersale record, @Param("example") LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallAftersale record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallAftersaleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_aftersale
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}