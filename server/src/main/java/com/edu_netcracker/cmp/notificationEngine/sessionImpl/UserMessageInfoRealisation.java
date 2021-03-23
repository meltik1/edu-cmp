package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@NoArgsConstructor
public class UserMessageInfoRealisation implements IUserMessageInfo {
    private Map<String ,String> contacts;


    public UserMessageInfoRealisation(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void setMapOfContactId(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public Map<String, String> getMapOfContactId() {
        return contacts;
    }
}
