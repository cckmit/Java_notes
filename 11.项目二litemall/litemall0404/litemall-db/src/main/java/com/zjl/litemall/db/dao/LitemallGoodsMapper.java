package com.zjl.litemall.db.dao;

import com.zjl.litemall.db.domain.LitemallGoods;
import com.zjl.litemall.db.example.LitemallGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LitemallGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    long countByExample(LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByExample(LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insert(LitemallGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int insertSelective(LitemallGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoods selectOneByExample(LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoods selectOneByExampleSelective(@Param("example") LitemallGoodsExample example, @Param("selective") LitemallGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoods selectOneByExampleWithBLOBs(LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGoods> selectByExampleSelective(@Param("example") LitemallGoodsExample example, @Param("selective") LitemallGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGoods> selectByExampleWithBLOBs(LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGoods> selectByExample(LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleSelective(@Param("record") LitemallGoods record, @Param("example") LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExampleWithBLOBs(@Param("record") LitemallGoods record, @Param("example") LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByExample(@Param("record") LitemallGoods record, @Param("example") LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeySelective(LitemallGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKeyWithBLOBs(LitemallGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int updateByPrimaryKey(LitemallGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}