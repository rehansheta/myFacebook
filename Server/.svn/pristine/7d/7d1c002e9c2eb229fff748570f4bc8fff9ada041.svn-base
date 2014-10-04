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
public interface UserAccountStub extends Remote {
    public ArrayList<UserProfileStub> getFriendsList() throws RemoteException;
    public UserProfileStub viewProfile() throws RemoteException;
    public String updateProfile(UserProfile profile) throws RemoteException;
    public ArrayList<Post> getUpdates(String userID) throws RemoteException;
    public String postUpdates(Post post) throws RemoteException;
}
