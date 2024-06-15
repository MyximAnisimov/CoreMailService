//package org.example.mailcoremicroservice.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.antlr.v4.runtime.misc.NotNull;
//import org.example.mailcoremicroservice.roles.Roles;
//
//import java.io.Serializable;
//
//@Entity
//@Data
//@Table(name = "image")
//@NoArgsConstructor
//public class Image implements Serializable {
//    @Id
//    @Column(nullable = false, unique = true, name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column @NotNull
//    private String filename;
//
//    @Column @NotNull
//    private int size;
//
//    @Column @NotNull
//    private String uploadDate;
//
//    @Column @NotNull
//    private String email;
//
//}
