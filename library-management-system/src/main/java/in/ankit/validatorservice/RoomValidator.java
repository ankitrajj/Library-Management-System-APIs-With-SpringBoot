package in.ankit.validatorservice;

import in.ankit.exceptions.IdNotFoundException;
import in.ankit.exceptions.InvalidInputException;
import in.ankit.exceptions.NameInvalidException;
import in.ankit.service.RoomService;

public class RoomValidator extends LibraryValidator {

    private final RoomService roomService;

    public RoomValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public void validId(Long id) {
        if (id == null || id < 1L)
            throw new InvalidInputException(id + " is an invalid id.", "/rooms/");
    }

    @Override
    public void idExists(Long id) {
        roomService.findById(id).orElseThrow(() -> new IdNotFoundException("room", id));
    }

    public void validName(String name) {
        if(name.isBlank())
            throw new NameInvalidException("room", name);
    }
}
