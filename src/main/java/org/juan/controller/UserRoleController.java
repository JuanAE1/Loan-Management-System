package org.juan.controller;

import io.javalin.http.Context;
import org.juan.model.UserRole;
import org.juan.service.UserRoleService;
import java.util.List;

public class UserRoleController {

    UserRoleService userRoleService = new UserRoleService();

    public void getAllRoles(Context ctx) {
        List<UserRole> roles = userRoleService.getAllRoles();
        ctx.json(roles);
    }
}
