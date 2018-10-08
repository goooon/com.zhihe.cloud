package com.zhihe.template.domain.dataJpa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class User implements Serializable{

	/**
	 * 用户登录类
	 */
	private static final long serialVersionUID = 5239854751047695809L;


	@ApiModelProperty(value = "人员名称")
	private String name;
	@ApiModelProperty(value = "用户ID")
	private Long id;
	@ApiModelProperty(value = "用户访问token，7天有效，设备访问Token只要不退出不失效")
	private String token;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "头像")
	private String icon;
	@ApiModelProperty(value = "性别 0男 1女")
	private String gender;

}
