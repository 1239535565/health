package com.hbnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
/**去除Getter,Setter,equals,hashCode,toString方法使得代码简洁，需要lombok*/
@Accessors(chain = true)/**通过该注解可以控制getter和setter方法的形式。使用chain属性，setter方法返回当前对象*/
@TableName("t_user")
public class User {

    private static final long serialVersionUID = 1L;
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer user_id;//用户id
    private String username;//用户名
    private String nick_name;//用户昵称/真实姓名
    private String password;//密码
    private String email;//邮箱
    private String mobile;//联系电话
    private Integer last_login_ip;//上次登录Ip
    private Integer ssex;//性别 0男 1女 2保密
    private String description;//描述
    private String avatar;//用户头像
    private String school;//医生毕业院校
    private Integer dep_id;//医生科室Id
    private Integer status;//状态 0锁定 1有效,默认为1
    private Integer role_id;//角色，0微超级管理员，1为医生，2为患者
    private Date create_time;//创建时间
    private Date modify_time;//修改时间
    private Date last_login_time;//最近访问时间

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;//生日

    @TableField(exist = false)
    private String dep_name;

    @TableField(exist = false)
    private Integer age;
}
