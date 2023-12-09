package com.pn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import java.util.Date;

/**
 * user_info表的实体类:
 */
@Data
@ToString
@TableName("user_info")
public class User {

	@TableField("user_id")
	private int userId;//用户id

	@TableField("user_code")
	private String userCode;//账号

	@TableField("user_name")
	private String userName;//用户名

	@TableField("user_pwd")
	private String userPwd;//用户密码

	@TableField("user_type")
	private String userType;//用户类型

	@TableField("user_state")
	private String userState;//用户状态

	@TableField("is_delete")
	private String isDelete;//删除状态

	@TableField("create_by")
	private int createBy;//创建人

	//返回前端时,自动将Date转换成指定格式的json字符串
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@TableField("create_time")
	private Date createTime;//创建时间

	@TableField("update_by")
	private int updateBy;//修改人

	@TableField("update_time")
	private Date updateTime;//修改时间

	public User() {
	}

	public User(int userId, String userCode, String userName, String userPwd,
			String userType, String userState, String isDelete, int createBy,
			Date createTime, int updateBy, Date updateTime) {
		this.userId = userId;
		this.userCode = userCode;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userType = userType;
		this.userState = userState;
		this.isDelete = isDelete;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
	}
}
