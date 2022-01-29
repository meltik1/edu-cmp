package com.edu_netcracker.cmp.restControllers;

import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.TemplateDto;
import com.edu_netcracker.cmp.entities.DTO.UserDTO;
import com.edu_netcracker.cmp.entities.repositories.AttributesRepository;
import com.edu_netcracker.cmp.entities.repositories.UsersRepository;
import com.edu_netcracker.cmp.entities.users.User;
import com.edu_netcracker.cmp.usersInfoService.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/user")
@Slf4j
public class UsersController {


    private UserInfoService userInfoService;
    private UsersRepository usersRepository;
    @Autowired
    private AttributesRepository attributesRepository;

    @Autowired
    public UsersController(UserInfoService userInfoService, UsersRepository usersRepository) {
        this.userInfoService = userInfoService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable  String userId) {
        User user = usersRepository.getOne(userId);
        return new ResponseEntity<UserDTO>(user.toUserDTO(), HttpStatus.OK);
    }

    @GetMapping("getAttributes/{userId}")
    public  ResponseEntity<Map<String, Object>> getUserAttributes(@PathVariable  String userId) {
        Map<String, Object> usersAttributes = userInfoService.getUsersAttributes(userId);
        return new ResponseEntity<>(usersAttributes, HttpStatus.OK);
    }

    @PostMapping("saveForUser/{userId}")
    public ResponseEntity<Void> saveUserAttribute(@RequestBody Map<String, Object> attributes, @PathVariable String userId) {
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin")) || userDetails.getUsername().equals(userId)) {
            userInfoService.modifyAttributes(attributes, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("deleteAttribute/{userId}")
    public ResponseEntity<Void> deleteAttribute(@RequestBody String attributeName, @PathVariable String userId){
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin"))
                || userDetails.getUsername().equals(userId)) {
            Attributes attributes = attributesRepository.findAttributesByAttributeName(attributeName);
            userInfoService.deleteAttribute(attributes, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = usersRepository.findAll();
        List<UserDTO> usersDTO = users.stream().map(x -> new UserDTO(x.getUserName(), x.getFIO())).collect(Collectors.toList());
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Void> createUser(@RequestBody UserCreationDto creationDto) {
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin")) ){
            userInfoService.createUser(creationDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("sendToUser/{userId}")
    public ResponseEntity<Void> sendToUser(@RequestBody TemplateDto tempalte, @PathVariable String userId) {
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin"))
                || userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("hr"))
                ) {
            userInfoService.sendDirectlyToUser(tempalte, userId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}
