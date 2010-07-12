package org.ssh.app.example.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "DISC_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Disc_2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5460390447069504895L;
    private Long id;
    private String name;
    private Integer price;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL", pkColumnValue = "Disc_2", initialValue = 1,
            allocationSize = 1)
    @Column(name="disc_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
