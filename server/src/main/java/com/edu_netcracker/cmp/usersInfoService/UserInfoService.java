package com.edu_netcracker.cmp.usersInfoService;

import com.edu_netcracker.cmp.restControllers.UserCreationDto;
import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.TemplateDto;

import java.text.ParseException;
import java.util.Map;

public interface UserInfoService {
    Map<String, Object> getUsersAttributes(String userId);

    void modifyAttributes(Map<String, Object> newAttributes, String userId);

    void createNewAttribute(String attributeName, Object value, String userId) throws ParseException;

    void deleteAttribute(Attributes attribute, String userID);

    void sendDirectlyToUser(TemplateDto templateDto, String userID);

    void createUser(UserCreationDto userCreationDto);
}
