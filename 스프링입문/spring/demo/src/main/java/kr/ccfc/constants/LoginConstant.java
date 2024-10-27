package kr.ccfc.constants;

/**
 * 登录的常量
 */
public class LoginConstant {
    /**
     * 使用用户名查询用户
     */
    public static final String QUERY_ADMIN_SQL =
            "SELECT `id` ,`username`, `password`, `status` FROM sys_user WHERE username = ? ";

    /**
     * 查询后台用户的角色Code
     */
    public static final String QUERY_ROLE_CODE_SQL =
            "SELECT `code` FROM sys_role LEFT JOIN sys_user_role ON sys_role.id = sys_user_role.role_id WHERE sys_user_role.user_id= ?";

    /**
     * 查询前台用户角色Code
     */
    public static final String QUERY_MEMBER_ROLE_CODE_SQL =
            "SELECT `code` FROM sys_role LEFT JOIN user_role on sys_role.id = user_role.role_id WHERE user_role.user_id = ?";
    /**
     * 查询所有的权限名称
     */
    public static final String QUERY_ALL_PERMISSIONS =
            "SELECT `name` FROM sys_privilege";

    /**
     * 对于非超级用户，需要先查询 role->permissionId->permission
     */
    public static final String QUERY_PERMISSION_SQL =
            "SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON sys_role_privilege.privilege_id = sys_privilege.id " +
                    "LEFT JOIN sys_user_role  ON sys_role_privilege.role_id = sys_user_role.role_id WHERE sys_user_role.user_id = ?";

    /**
     * 超级管理员的角色的Code
     */
    public static final String ADMIN_ROLE_CODE = "ROLE_ADMIN";

    public static final String REFRESH_TYPE = "REFRESH_TOKEN";


    /**
     * 使用后台用户的id 查询用户名称
     */
    public static final String QUERY_ADMIN_USER_WITH_ID = "SELECT `username` FROM sys_user where id = ?";

    /**
     * 使用用户的id 查询用户名称
     */
    public static final String QUERY_MEMBER_USER_WITH_ID = "SELECT `mobile` FROM user where id = ?";
}
