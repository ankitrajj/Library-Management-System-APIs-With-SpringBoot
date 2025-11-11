package in.ankit.validatorservice;

import in.ankit.exceptions.IdNotFoundException;
import in.ankit.exceptions.InvalidInputException;
import in.ankit.service.UserService;

public class UserValidator extends LibraryValidator {

    UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validId(Long id) {
        if(id == null || id < 1L)
            throw new InvalidInputException(id + " is an invalid id.", "/users/");
    }

    @Override
    public void idExists(Long id) {
        userService.findUserEntityById(id).orElseThrow(() -> new IdNotFoundException("user", id));
    }
}
