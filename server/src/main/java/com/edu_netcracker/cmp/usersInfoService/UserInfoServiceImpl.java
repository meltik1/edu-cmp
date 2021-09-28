package com.edu_netcracker.cmp.usersInfoService;

import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.DTO.TemplateDto;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.jpa.AttributesJPA;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.UsersJpa;
import com.edu_netcracker.cmp.entities.users.User;
import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private UsersJpa usersJpa;

    private AttributesJPA attributesJPA;

    private STAJPA stajpa;

    private List<NotificationService> notificationServices;
    private ITemplate iTemplate;
    private IUserMessageInfo iUserMessageInfo;

    public UserInfoServiceImpl(UsersJpa usersJpa, AttributesJPA attributesJPA, STAJPA stajpa, List<NotificationService> notificationServices, ITemplate iTemplate,
                               IUserMessageInfo iUserMessageInfo) {
        this.usersJpa = usersJpa;
        this.attributesJPA = attributesJPA;
        this.stajpa = stajpa;
        this.notificationServices = notificationServices;
        this.iTemplate = iTemplate;
        this.iUserMessageInfo = iUserMessageInfo;
    }

    private void addFilledAttribute(Map<String, Object> attributesValues, StudentsAttributesDTO studentsAttributesDTO) {
        if (studentsAttributesDTO.getCharValue() != null) {
            attributesValues.put(studentsAttributesDTO.getAttributeName(), studentsAttributesDTO.getCharValue());
        }
        else if (studentsAttributesDTO.getIntValue() != null) {
            attributesValues.put(studentsAttributesDTO.getAttributeName(), studentsAttributesDTO.getIntValue());
        }
        else if (studentsAttributesDTO.getDateValue() != null) {
            attributesValues.put(studentsAttributesDTO.getAttributeName(), studentsAttributesDTO.getCharValue());
        }
    }

    @Override
    public Map<String, Object> getUsersAttributes(String userId) {
        List<StudentsAttributesDTO> studentAttributeValue = stajpa.findStudentAttributeValue(userId);
        Map<String, Object> attributesValues = new HashMap<>();
        for (StudentsAttributesDTO studentsAttributesDTO: studentAttributeValue) {
            addFilledAttribute(attributesValues, studentsAttributesDTO);
        }
        return attributesValues;
    }

    public Map<String, String> getUsersDataSource(String userId) {
        List<StudentsAttributesDTO> dataSources = stajpa.findDataSources(userId);
        Map<String,String> datasources = new HashMap<>();
        for (StudentsAttributesDTO studentsAttributesDTO: dataSources) {
            datasources.put(studentsAttributesDTO.getAttributeName(), studentsAttributesDTO.getCharValue());
        }
        return datasources;
    }

    @Override
    public void modifyAttributes(Map<String, Object> newAttributes, String userId) {
        for (Map.Entry<String, Object> attr: newAttributes.entrySet()) {
            try {
                addNewSTAOrModifyExited(attr.getKey(), attr.getValue(), userId);
            }
             catch (ParseException e) {
                log.error("Can not add attribute with {}:{}", attr.getKey(), attr.getValue());
            }
        }
    }

    @Override
    public void sendDirectlyToUser(TemplateDto templateDto, String userID) {
        Map<String, Object> usersAttributeObject = getUsersAttributes(userID);

        Map<String, String> usersAttributes = new HashMap<>();
        for (Map.Entry<String, Object> it :usersAttributeObject.entrySet()) {
            usersAttributes.put(it.getKey(), (String) it.getValue());
        }

        iTemplate.setTemplate(templateDto.getTemplate());
        iTemplate.setTheme(templateDto.getTheme());
        iTemplate.applyParams(usersAttributes);
        iUserMessageInfo.setMapOfContactId(getUsersDataSource(userID));
        for (NotificationService notificationService: notificationServices) {
            if (iUserMessageInfo.getMapOfContactId().get(notificationService.getName()) != null) {
                notificationService.send(iUserMessageInfo, iTemplate);
            }
        }
    }

    private void addNewSTAOrModifyExited(String key, Object value, String userId) throws ParseException {
        User user = usersJpa.findUsersByUserName(userId);
        Attributes attributes = addAttributeOrGetIfExisted(key);
        StudentsToAttributes studentsToAttributesByAttributesAndAndStudent = stajpa.findStudentsToAttributesByAttributesAndAndStudent(attributes, user);
        if (studentsToAttributesByAttributesAndAndStudent == null) {
            studentsToAttributesByAttributesAndAndStudent = new StudentsToAttributes();
            studentsToAttributesByAttributesAndAndStudent.setAttributes(attributes);
            studentsToAttributesByAttributesAndAndStudent.setStudent(user);
            addValue(studentsToAttributesByAttributesAndAndStudent, value);
        }
        else {
            addValue(studentsToAttributesByAttributesAndAndStudent, value);;
        }
        stajpa.save(studentsToAttributesByAttributesAndAndStudent);
    }

    private StudentsToAttributes addValue(StudentsToAttributes studentsToAttributes, Object value) throws ParseException {
        String strValue = String.valueOf(value);
        Integer intValue = null;
        Date dateValue = null;
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();

        try {
            intValue = Integer.parseInt(strValue);
        }
        catch (NumberFormatException exception) {
            log.info("Can't cast {} to Integer", strValue);
        }

        try {
            dateValue = dateFormat.parse(strValue);
        } catch (ParseException e) {
            log.info("Can't cast {} to Date", strValue);
        }

        if (intValue != null) {
            studentsToAttributes.setIntValue(intValue);
        }
        else if (dateValue != null) {
            studentsToAttributes.setDateValue(dateValue);
        }
        else if (strValue != null) {
            studentsToAttributes.setCharValue(strValue);
        }
        else {
            log.error("Can't add attribute");
            throw new ParseException("Can not parse the value", 0);
        }
        return studentsToAttributes;
    }

    private Attributes addAttributeOrGetIfExisted(String attributeName) {
        Attributes attributesByAttributeName = attributesJPA.findAttributesByAttributeName(attributeName);
        if (attributesByAttributeName == null) {
            attributesByAttributeName = new Attributes();
            attributesByAttributeName.setAttributeName(attributeName);
            attributesJPA.save(attributesByAttributeName);
        }
        return attributesByAttributeName;
    }

    @Override
    public void createNewAttribute(String attributeName, Object value, String userID) throws ParseException {
        addNewSTAOrModifyExited(attributeName, value, userID);
    }

    @Override
    public void deleteAttribute(Attributes attribute, String userID) {
        User user = usersJpa.findUsersByUserName(userID);
        if (user == null) {
            throw new IllegalArgumentException("Can't find user");
        }
        StudentsToAttributes studentsToAttributesByAttributesAndAndStudent = stajpa.findStudentsToAttributesByAttributesAndAndStudent(attribute, user);
        if (studentsToAttributesByAttributesAndAndStudent == null) {
            stajpa.delete(studentsToAttributesByAttributesAndAndStudent);
        }

    }
}
