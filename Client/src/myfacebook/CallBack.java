/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfacebook;

import stubs.Post;
import java.rmi.RemoteException;
import java.util.ArrayList;
import stubs.CallBackStub;
import stubs.UserProfileStub;

/**
 *
 * @author Riyad
 */
public class CallBack implements CallBackStub {
    private final MainFrame mainFrame;

    CallBack(MainFrame aThis) {
        this.mainFrame = aThis;
    }

    @Override
    public int pushRequest(ArrayList<UserProfileStub> userProfiles) throws RemoteException {
        mainFrame.showRequests(userProfiles);
        return 1;
    }

    @Override
    public void pushPost(ArrayList<Post> posts) throws RemoteException {
        mainFrame.showPosts(posts);
    }

    @Override
    public void pushMessage(String friendID, String text) throws RemoteException {
        mainFrame.showMessage(friendID, text);
    }
}
