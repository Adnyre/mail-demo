package adnyre.maildemo.controller;

import adnyre.maildemo.dto.UserDto;
import adnyre.maildemo.dto.UserStatsView;
import adnyre.maildemo.model.User;
import adnyre.maildemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public UserDto getAddressee(@PathVariable long id) {
        User addressee = userService.get(id);
        log.debug("Found user: {}", addressee);
        return UserDto.fromEntity(addressee);
    }

    @PostMapping
    @ResponseBody
    public UserDto saveAddressee(@RequestBody UserDto dto) {
        User addressee = userService.save(dto);
        log.debug("Saved user successfully");
        return UserDto.fromEntity(addressee);
    }

    @PutMapping
    @ResponseBody
    public UserDto updateAddressee(@RequestBody UserDto dto) {
        User addressee = userService.update(dto);
        log.debug("Updated user successfully");
        return UserDto.fromEntity(addressee);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressee(@PathVariable long id) {
        userService.delete(id);
        log.debug("Deleted user with id: {}", id);
    }

    @GetMapping("/stats")
    @ResponseBody
    public List<UserStatsView> getAllUserStats() {
        List<UserStatsView> userStats;
        userStats = userService.getAllUserStats();
        log.debug("Retrieved user stats for all users");
        return userStats;
    }
}