package in.ankit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.ankit.dto.Role;
import in.ankit.dto.UserWithRole;
import in.ankit.service.UserService;
import in.ankit.validatorservice.RoleValidator;
import in.ankit.validatorservice.UserValidator;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRoleController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleValidator roleValidator;

    public UserRoleController(UserService userService, UserValidator userValidator, RoleValidator roleValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleValidator = roleValidator;
    }

    @GetMapping("{id}/role")
    public ResponseEntity<List<Role>> getUserRole(@PathVariable Long id) {
        List<Role> roles = userService.getUserRoles(id);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PatchMapping("{userId}/role/{roleId}")
    public ResponseEntity<UserWithRole> updateUserRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userValidator.validId(userId);
        roleValidator.validId(roleId);
        UserWithRole user = userService.addRoleToUser(userId, roleId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
