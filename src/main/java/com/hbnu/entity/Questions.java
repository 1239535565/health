package com.hbnu.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data/**去除Getter,Setter,equals,hashCode,toString方法使得代码简洁，需要lombok*/
@Accessors(chain = true)/**通过该注解可以控制getter和setter方法的形式。使用chain属性，setter方法返回当前对象*/
@TableName("t_questions")
public class Questions {
    private Integer id;//主键Id
    private String title;//问题/回复
    private Integer parent_id;//问题id
    private Integer doctor_id;//医生Id
    private Integer patients_id;//问题所属患者id
    private Date create_time;//创建时间
    private Date modify_time;//修改时间
}
