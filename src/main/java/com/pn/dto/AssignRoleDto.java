package com.pn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 接受分配角色信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleDto {

    private Integer userId;

    private List<String> roleCheckList;

}
