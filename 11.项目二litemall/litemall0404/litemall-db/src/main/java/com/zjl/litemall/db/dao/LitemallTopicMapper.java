package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallTopic;
import com.zjl.litemall.db.example.LitemallTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallTopicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallTopic selectOneByExample(LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallTopic selectOneByExampleSelective(@Param("example") LitemallTopicExample example, @Param("selective") LitemallTopic.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallTopic selectOneByExampleWithBLOBs(LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallTopic> selectByExampleSelective(@Param("example") LitemallTopicExample example, @Param("selective") LitemallTopic.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallTopic> selectByExampleWithBLOBs(LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallTopic> selectByExample(LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallTopic selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallTopic.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallTopic selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallTopic selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallTopic record, @Param("example") LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleWithBLOBs(@Param("record") LitemallTopic record, @Param("example") LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallTopic record, @Param("example") LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeyWithBLOBs(LitemallTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}