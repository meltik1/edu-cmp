package com.edu_netcracker.cmp.usersInfoService;

import com.edu_netcracker.cmp.entities.Attributes;

import javax.management.Attribute;
import java.text.ParseException;
import java.util.Map;

public interface UserInfoService {
    Map<String, Object> getUsersAttributes(String userId);

    void modifyAttributes(Map<String, Object> newAttributes, String userId);

    void createNewAttribute(String attributeName, Object value, String userId) throws ParseException;

    void deleteAttribute(Attributes attribute, String userID);

    void sendDirectlyToUser(String template, String userID);
}
