package com.pn.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 权限表
 * @TableName auth_info
 */
@Data
public class Auth implements Serializable {
    /**
     * 
     */
    private Integer authId;

    /**
     * 父id为空或为0，表示一级权限
     */
    private Integer parentId;

    /**
     * 
     */
    private String authName;

    /**
     * 
     */
    private String authDesc;

    /**
     * 
     */
    private Integer authGrade;

    /**
     * 1 模块 、2  列表、 3  按钮
     */
    private String authType;

    /**
     * 
     */
    private String authUrl;

    /**
     * 
     */
    private String authCode;

    /**
     * 
     */
    private Integer authOrder;

    /**
     * 1 启用 、0 禁用
     */
    private String authState;

    /**
     * 
     */
    private Integer createBy;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Integer updateBy;

    /**
     * 
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    //额外属性
    private List<Auth> childAuth;



    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Auth other = (Auth) that;
        return (this.getAuthId() == null ? other.getAuthId() == null : this.getAuthId().equals(other.getAuthId()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getAuthName() == null ? other.getAuthName() == null : this.getAuthName().equals(other.getAuthName()))
            && (this.getAuthDesc() == null ? other.getAuthDesc() == null : this.getAuthDesc().equals(other.getAuthDesc()))
            && (this.getAuthGrade() == null ? other.getAuthGrade() == null : this.getAuthGrade().equals(other.getAuthGrade()))
            && (this.getAuthType() == null ? other.getAuthType() == null : this.getAuthType().equals(other.getAuthType()))
            && (this.getAuthUrl() == null ? other.getAuthUrl() == null : this.getAuthUrl().equals(other.getAuthUrl()))
            && (this.getAuthCode() == null ? other.getAuthCode() == null : this.getAuthCode().equals(other.getAuthCode()))
            && (this.getAuthOrder() == null ? other.getAuthOrder() == null : this.getAuthOrder().equals(other.getAuthOrder()))
            && (this.getAuthState() == null ? other.getAuthState() == null : this.getAuthState().equals(other.getAuthState()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAuthId() == null) ? 0 : getAuthId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getAuthName() == null) ? 0 : getAuthName().hashCode());
        result = prime * result + ((getAuthDesc() == null) ? 0 : getAuthDesc().hashCode());
        result = prime * result + ((getAuthGrade() == null) ? 0 : getAuthGrade().hashCode());
        result = prime * result + ((getAuthType() == null) ? 0 : getAuthType().hashCode());
        result = prime * result + ((getAuthUrl() == null) ? 0 : getAuthUrl().hashCode());
        result = prime * result + ((getAuthCode() == null) ? 0 : getAuthCode().hashCode());
        result = prime * result + ((getAuthOrder() == null) ? 0 : getAuthOrder().hashCode());
        result = prime * result + ((getAuthState() == null) ? 0 : getAuthState().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", authId=").append(authId);
        sb.append(", parentId=").append(parentId);
        sb.append(", authName=").append(authName);
        sb.append(", authDesc=").append(authDesc);
        sb.append(", authGrade=").append(authGrade);
        sb.append(", authType=").append(authType);
        sb.append(", authUrl=").append(authUrl);
        sb.append(", authCode=").append(authCode);
        sb.append(", authOrder=").append(authOrder);
        sb.append(", authState=").append(authState);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}