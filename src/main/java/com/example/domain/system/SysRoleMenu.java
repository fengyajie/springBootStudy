package com.example.domain.system;

public class SysRoleMenu {
    /**
     * 关联表ID
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 按钮ID
     */
    private Long menuId;

    /**
     * 获取关联表ID
     *
     * @return id - 关联表ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置关联表ID
     *
     * @param id 关联表ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取按钮ID
     *
     * @return menu_id - 按钮ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置按钮ID
     *
     * @param menuId 按钮ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}