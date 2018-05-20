package adnyre.maildemo.controller;

import adnyre.maildemo.dto.AddresseeDto;
import adnyre.maildemo.model.Addressee;
import adnyre.maildemo.service.AddresseeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/addressees")
public class AddresseeController {

    @Autowired
    private AddresseeService addresseeService;

    @GetMapping("/{id}")
    @ResponseBody
    public AddresseeDto getAddressee(@PathVariable long id) {
        Addressee addressee = addresseeService.get(id);
        log.debug("Found addressee: {}", addressee);
        return AddresseeDto.fromEntity(addressee);
    }

    @GetMapping
    @ResponseBody
    public AddresseeDto getAddresseeByEmail(@RequestParam String email) {
        Addressee addressee = addresseeService.getByEmail(email);
        log.debug("Found addressee: {}", addressee);
        return AddresseeDto.fromEntity(addressee);
    }

    @PostMapping
    @ResponseBody
    public AddresseeDto saveAddressee(@RequestBody AddresseeDto dto) {
        Addressee addressee = addresseeService.save(dto);
        log.debug("Saved addressee successfully");
        return AddresseeDto.fromEntity(addressee);
    }

    @PutMapping
    @ResponseBody
    public AddresseeDto updateAddressee(@RequestBody AddresseeDto dto) {
        Addressee addressee = addresseeService.update(dto);
        log.debug("Updated addressee successfully");
        return AddresseeDto.fromEntity(addressee);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressee(@PathVariable long id) {
        addresseeService.delete(id);
        log.debug("Deleted addressee with id: {}", id);
    }
}