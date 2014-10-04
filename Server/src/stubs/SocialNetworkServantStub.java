/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Rehan
 */
public interface SocialNetworkServantStub extends Remote {
    public UserAccountStub createAccount(String userID, String password) throws RemoteException;
    public UserAccountStub login(String userID, String password) throws RemoteException; //TODO: return stub
    public ArrayList<UserProfileStub> searchForFriends(String criteriaName, String criteriaCity, String criteriaCollege) throws RemoteException;
    public String inviteFriend(String userID, String friendID) throws RemoteException;
    public String acceptIgnoreFriend(String userID, String friendID, int decision) throws RemoteException;
    public boolean registerCallBack(String userID, CallBackStub callBack) throws RemoteException;
    public boolean logout(String userID) throws RemoteException;
    public String sendMessage(String userID, String friendID, String text) throws RemoteException;
    public boolean isOnline(String friendID) throws RemoteException;
    public ArrayList<UserProfileStub> getFriends(String userID) throws RemoteException;
}
