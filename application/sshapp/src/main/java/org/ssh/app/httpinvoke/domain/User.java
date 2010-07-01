package org.ssh.app.httpinvoke.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5590768569302443813L;
    private String username;
    private Date birthday;

    /**
     * @param username
     * @param birthday
     */
    public User(String username, Date birthday) {
        super();
        this.username = username;
        this.birthday = birthday;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s\t%s\t", username, birthday);
    }
}
