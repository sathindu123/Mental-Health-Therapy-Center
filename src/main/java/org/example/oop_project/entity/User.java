package org.example.oop_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Admin")

public class User implements SuperEntity{
    @Id
    @Column(name = "user_Id")
    private String id;
    private String email;
    private String userName;
    private String password;
}
