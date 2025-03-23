package org.example.oop_project.bo.custom;

import org.example.oop_project.bo.SuperBO;

public interface UserManageBO extends SuperBO {
    boolean cheack(String userName, String password);

}
