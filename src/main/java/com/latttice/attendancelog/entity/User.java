package com.latttice.attendancelog.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = StringConstants.USER_MASTER_TABLE)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = StringConstants.USER_ID, unique = true)
    private String userId;

    @Column(name = StringConstants.EMP_CODE, unique = true)
    private String empCode;
}
