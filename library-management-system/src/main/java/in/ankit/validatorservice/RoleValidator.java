package in.ankit.validatorservice;

import in.ankit.exceptions.IdNotFoundException;
import in.ankit.exceptions.InvalidInputException;
import in.ankit.service.RoleService;

public class RoleValidator extends LibraryValidator {

    RoleService roleService;

    public RoleValidator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void validId(Long id) {
        if(id == null || id < 1L)
            throw new InvalidInputException(id + " is an invalid id.", "/roles/");
    }

    @Override
    public void idExists(Long id) {
        roleService.findById(id).orElseThrow(() -> new IdNotFoundException("role", id));
    }
}
