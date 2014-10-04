/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Riyad
 */
public class UserProfile implements UserProfileStub, Serializable {

    private String userID;
    private String userName;
    private String profession;
    private String city;
    private String company;
    private String college;
    private String graduationYear;

    @Override
    public String getUserID() throws RemoteException {
        return userID;
    }

    @Override
    public String getUserName() throws RemoteException {
        return userName;
    }

    @Override
    public String getProfession() throws RemoteException {
        return profession;
    }

    @Override
    public String getCity() throws RemoteException {
        return city;
    }

    @Override
    public String getCompany() throws RemoteException {
        return company;
    }

    @Override
    public String getCollege() throws RemoteException {
        return college;
    }

    @Override
    public String getGraduationYear() throws RemoteException {
        return graduationYear;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
