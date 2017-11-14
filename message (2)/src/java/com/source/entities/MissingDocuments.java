/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.source.entities;

import com.Manoj.framework.AppModel;
import com.mysql.jdbc.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static org.hibernate.type.StandardBasicTypes.BLOB;

/**
 *
 * @author Hp
 */
@Entity
@Table(name="missing_document")
public class MissingDocuments extends AppModel{
    
    @Id
    @GeneratedValue
    
    @Column(name="id")
    long id;
    
    @Column (name="document_name")
    String document_name;
    
    @Column (name="rollno")
    String rollno;
    
    @Column(name="mobilenumber")
    String mobilenumber;
    
    @Column(name="document_image")
    Blob document_image;
    
    @Column(name="certificate_no")
    String certificate_no;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public Blob getDocument_image() {
        return document_image;
    }

    public void setDocument_image(Blob document_image) {
        this.document_image = document_image;
    }


    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }
    
    
    
    
    
}

