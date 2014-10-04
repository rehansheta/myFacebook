/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfacebookserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import stubs.CallBackStub;
import stubs.Post;
import stubs.UserProfile;
import stubs.UserProfileStub;

/**
 *
 * @author Rehan
 */
public class AsyncCallback extends Thread {

    String param1;
    String param2;
    Object param3;
    int callbackType;
    public static int PUSH_POST = 1;
    public static int PUSH_REQUEST = 2;
    public static int PUSH_MESSAGE = 3;

    public AsyncCallback(String param1, String param2, Object param3, int callbackType) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.callbackType = callbackType;
    }

    @Override
    public void run() {
        
        // param2 = friendID, param3 = posts
        if (callbackType == PUSH_POST && SocialNetworkServant.callBackMap.containsKey(param2)) {
            CallBackStub callBack = SocialNetworkServant.callBackMap.get(param2);
            try {
                callBack.pushPost((ArrayList<Post>)param3);
            } catch (RemoteException ex) {
                Logger.getLogger(AsyncCallback.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        // param1 = userID, param2 = friendID
        else if (callbackType == PUSH_REQUEST && SocialNetworkServant.callBackMap.containsKey(param2)) {
            try {
                CallBackStub callBack = SocialNetworkServant.callBackMap.get(param2);

                ArrayList<UserProfileStub> userProfileList = new ArrayList<UserProfileStub>();
                UserProfile profile = DatabaseOperations.getUserProfile(param1);
                UserProfileStub userProfileStub = (UserProfileStub) UnicastRemoteObject.exportObject(profile, 0);
                userProfileList.add(userProfileStub);

                callBack.pushRequest(userProfileList);
            } catch (RemoteException ex) {
                Logger.getLogger(AsyncCallback.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        // param1 = sender, param2 = receiver, param3 = text
        else if (callbackType == PUSH_MESSAGE) {
            try {
                CallBackStub callBack = SocialNetworkServant.callBackMap.get(param2);
                callBack.pushMessage(param1, (String)param3);
            } catch (RemoteException ex) {
                Logger.getLogger(AsyncCallback.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
