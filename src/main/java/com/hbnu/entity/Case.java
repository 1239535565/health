package com.hbnu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
/**去除Getter,Setter,equals,hashCode,toString方法使得代码简洁，需要lombok*/
@Accessors(chain = true)/**通过该注解可以控制getter和setter方法的形式。使用chain属性，setter方法返回当前对象*/
@TableName("t_case")
public class Case {
    private Integer id;//主键Id
    private Integer doctor_id;//医生id
    private Integer patients_id;//患者id
    private String remake;//病例详情
    private String recipe;//处方信息
    private Date create_time;//创建时间
    private Date modify_time;//修改时间
}
