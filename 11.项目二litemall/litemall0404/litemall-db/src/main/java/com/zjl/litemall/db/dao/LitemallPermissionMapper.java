package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallPermission;
import com.zjl.litemall.db.example.LitemallPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallPermission selectOneByExample(LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallPermission selectOneByExampleSelective(@Param("example") LitemallPermissionExample example, @Param("selective") LitemallPermission.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallPermission> selectByExampleSelective(@Param("example") LitemallPermissionExample example, @Param("selective") LitemallPermission.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallPermission> selectByExample(LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallPermission selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallPermission.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallPermission selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallPermission selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallPermission record, @Param("example") LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallPermission record, @Param("example") LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_permission
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}