package com.leyou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.jboss.logging.Field;

import javax.validation.constraints.Pattern;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qp
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @Length(min = 4,max = 30,message = "用户名必须在4到30位之间")
    private String username;

    /**
     * 密码，加密存储
     */
    @JsonIgnore   //对象序列化为=json字符串时忽略该属性
    @Length(min = 4,max = 30,message = "密码必须在4到30位之间")
    private String password;

    /**
     * 注册手机号
     */
    @Pattern( regexp = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$")
    private String phone;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 密码加密的salt值
     */
    private String salt;

    @TableField(exist = false)
    private String code;
}
