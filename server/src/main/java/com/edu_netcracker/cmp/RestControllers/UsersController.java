package com.edu_netcracker.cmp.RestControllers;

import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.DTO.TemplateDto;
import com.edu_netcracker.cmp.entities.DTO.UserDTO;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.jpa.AttributesJPA;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.UsersJpa;
import com.edu_netcracker.cmp.usersInfoService.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user")
@Slf4j
public class UsersController {


    private UserInfoService userInfoService;
    private UsersJpa usersJpa;
    @Autowired
    private AttributesJPA attributesJPA;

    @Autowired
    public UsersController(UserInfoService userInfoService, UsersJpa usersJpa) {
        this.userInfoService = userInfoService;
        this.usersJpa = usersJpa;
    }

    @GetMapping("get/{userId}")
    public Map<String, Object> testGet(@PathVariable  String userId) {
        Map<String, Object> usersAttributes = userInfoService.getUsersAttributes(userId);
        return usersAttributes;
    }

    @PostMapping("saveForUser/{userId}")
    public ResponseEntity<Void> testSave(@RequestBody Map<String, Object> attributes, @PathVariable String userId) {
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin")) || userDetails.getUsername().equals(userId)) {
            userInfoService.modifyAttributes(attributes, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteAttribute(@RequestBody String attributeName, @PathVariable String userId){
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin"))
                || userDetails.getUsername().equals(userId)) {
            Attributes attributes = attributesJPA.findAttributesByAttributeName(attributeName);
            userInfoService.deleteAttribute(attributes, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> usersByUserNameDTO = usersJpa.findUsersByUserNameDTO();
        return new ResponseEntity<>(usersByUserNameDTO, HttpStatus.OK);
    }

    @PostMapping("createUser")
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
