package com.hbnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
/**去除Getter,Setter,equals,hashCode,toString方法使得代码简洁，需要lombok*/
@Accessors(chain = true)/**通过该注解可以控制getter和setter方法的形式。使用chain属性，setter方法返回当前对象*/
@TableName("t_department")
public class Department {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//主键Id
    private String dep_name;//科室名
    private String letter;//医院编号
    private Date create_time;//创建时间
    private Date modify_time;//修改时间
}
