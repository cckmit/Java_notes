package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallGroupon;
import com.zjl.litemall.db.example.LitemallGrouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallGrouponMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGroupon selectOneByExample(LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGroupon selectOneByExampleSelective(@Param("example") LitemallGrouponExample example, @Param("selective") LitemallGroupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGroupon> selectByExampleSelective(@Param("example") LitemallGrouponExample example, @Param("selective") LitemallGroupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGroupon> selectByExample(LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGroupon selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGroupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGroupon selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGroupon selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallGroupon record, @Param("example") LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallGroupon record, @Param("example") LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_groupon
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}