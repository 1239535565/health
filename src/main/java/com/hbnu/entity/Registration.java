package com.hbnu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
/**去除Getter,Setter,equals,hashCode,toString方法使得代码简洁，需要lombok*/
@Accessors(chain = true)/**通过该注解可以控制getter和setter方法的形式。使用chain属性，setter方法返回当前对象*/
@TableName("t_registration")
public class Registration {
    private Integer id;//主键id
    private Integer number;//唯一编号
    private Date time;//预约时间，默认为当前时间
    private Date create_time;//挂号时间
    private Integer doctor_id;//挂号医生
    private Integer patients_id;//挂号患者Id
    private Integer status;//0:未处理，1已处理
    private String evaluate;//满意，一般，不满意
    private Integer accept;//状态，1为采纳，2为不采纳
    private Integer is_cases;//是否需要病历本
    private Double amount_payable;//金额
    private String type;//结算类型
    private String level;//挂号级别
    private Integer is_invoice;//是否开票
    private Integer invoice_code;//发票代码
    private Integer invoice_number;//发票号
    private Date invoice_date;//开票日期
}
