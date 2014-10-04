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
 * @author Riyad
 */
public interface CallBackStub extends Remote {
    public int pushRequest(ArrayList<UserProfileStub> userProfileList) throws RemoteException;
    public void pushPost(ArrayList<Post> posts) throws RemoteException;
    public void pushMessage(String friendID, String text) throws RemoteException;
}
