package org.ssh.app.example.entity;

import java.awt.Color;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "t_cats")
public class Cat {
    private Long id; // identifier
    private Date birthdate;
    private Color color;
    private char sex;
    private float weight;
    private int litterId;
    //private Cat mother;
    //private Set kittens = new HashSet();

    private void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "t_cats", initialValue = 1, allocationSize = 100)
    public Long getId() {
        return id;
    }

    public void setBirthdate(Date date) {
        birthdate = date;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public char getSex() {
        return sex;
    }

    public void setLitterId(int id) {
        this.litterId = id;
    }

    public int getLitterId() {
        return litterId;
    }

//    public void setMother(Cat mother) {
//        this.mother = mother;
//    }
//
//    public Cat getMother() {
//        return mother;
//    }

//    void setKittens(Set kittens) {
//        this.kittens = kittens;
//    }
//
//    public Set getKittens() {
//        return kittens;
//    }
//
//    // addKitten not needed by Hibernate
//    public void addKitten(Cat kitten) {
//        kitten.setMother(this);
//        kitten.setLitterId(kittens.size());
//        kittens.add(kitten);
//    }
}
