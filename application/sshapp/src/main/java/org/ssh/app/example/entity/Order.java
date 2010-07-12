package org.ssh.app.example.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.ssh.app.common.entity.Contact;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "t_orders")
public class Order {

    private Long id;
    private Contact weekdayContact;
    private Contact holidayContact;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL", pkColumnValue = "t_orders", initialValue = 1,
            allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Embedded
    public Contact getWeekdayContact() {
        return weekdayContact;
    }

    public void setWeekdayContact(Contact weekdayContact) {
        this.weekdayContact = weekdayContact;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "recipient", column = @Column(name = "HOLIDAY_RECIPIENT")),
            @AttributeOverride(name = "phone", column = @Column(name = "HOLIDAY_PHONE")),
            @AttributeOverride(name = "address", column = @Column(name = "HOLIDAY_ADDRESS")

            ) })
    public Contact getHolidayContact() {
        return holidayContact;
    }

    public void setHolidayContact(Contact holidayContact) {
        this.holidayContact = holidayContact;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
