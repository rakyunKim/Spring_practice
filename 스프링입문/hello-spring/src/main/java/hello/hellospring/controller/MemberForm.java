package hello.hellospring.controller;

import org.springframework.stereotype.Controller;

public class MemberForm {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
