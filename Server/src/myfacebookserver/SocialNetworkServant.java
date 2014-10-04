/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfacebookserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import stubs.*;

/**
 *
 * @author Rehan
 */
public class SocialNetworkServant implements stubs.SocialNetworkServantStub {

    DatabaseOperations databaseOperations;
    public static Map<String, CallBackStub> callBackMap = Collections.synchronizedMap(new HashMap<String, CallBackStub>());

    public SocialNetworkServant() {
        databaseOperations = new DatabaseOperations();
        databaseOperations.openConnection();
        callBackMap = new HashMap<String, CallBackStub>();
    }

    @Override
    public UserAccountStub createAccount(String userID, String password) throws RemoteException {
        UserAccountStub stub = null;

        if (!databaseOperations.userExists(userID)) {
            System.out.println("<<NEW USER SIGN UP>>");
            String sql = "insert into userproperties (user_id, password) values('" + userID + "', '" + password + "')";
            if (DatabaseOperations.executeUpdate(sql)) {
                UserAccount newUser = new UserAccount();

                // add profile to an account
                UserProfile userProfile = new UserProfile();
                userProfile.setUserID(userID);  // this is the only profile info
                UserProfileStub userProfileStub = (UserProfileStub) UnicastRemoteObject.exportObject(userProfile, 0);
                newUser.setUserProfile(userProfileStub);

                // add friend profile ROR
                ArrayList<UserProfileStub> friendsList = getFriends(userID);
                newUser.setFriendsList(friendsList);

                stub = (UserAccountStub) UnicastRemoteObject.exportObject(newUser, 0);

                System.out.println("USER: \'" + userID + "\' IS ADDED SUCCESSFULLY.");

                callBackMap.put(userID, null);  // user logged in but still no call back registered

                // we can check for online users with this hashmap testing
                if (callBackMap.containsKey(userID)) {
                    System.out.println("USER: \'" + userID + "\' IS ONLINE....");
                }

            } else {
                System.err.println("Can't update database !!!");
            }
        } else {
            System.out.println("USER: \'" + userID + "\' ALREADY EXISTS.");
        }
        return stub;
    }

    @Override
    public UserAccountStub login(String userID, String password) throws RemoteException {
        UserAccountStub stub = null;

        if (databaseOperations.userExists(userID)) {
            if (databaseOperations.passwordMatches(userID, password)) {
                System.out.println("USER: \'" + userID + "\' HAS LOGGED IN.");
                UserAccount newUser = new UserAccount();

                // add profile to an account and get all profile info
                UserProfile userProfile = DatabaseOperations.getUserProfile(userID);
                UserProfileStub userProfileStub = (UserProfileStub) UnicastRemoteObject.exportObject(userProfile, 0);
                newUser.setUserProfile(userProfileStub);

                // add friend profile ROR
                ArrayList<UserProfileStub> friendsList = getFriends(userID);
                newUser.setFriendsList(friendsList);

                // creation of user account stub
                stub = (UserAccountStub) UnicastRemoteObject.exportObject(newUser, 0);
            } else {
                System.out.println("USER: \'" + userID + "\' ENTERED A WRONG PASSWORD.");
            }

            callBackMap.put(userID, null);  // user logged in but still no call back registered

            // we can check for online users with this hashmap testing
            if (callBackMap.containsKey(userID)) {
                System.out.println("USER: \'" + userID + "\' IS ONLINE....");
            }
        } else {
            System.out.println("USER: \'" + userID + "\' DOES NOT EXIST.");
        }
        return stub;
    }

    @Override
    public ArrayList<UserProfileStub> searchForFriends(String criteriaName, String criteriaCity, String criteriaCollege) throws RemoteException {
        ArrayList<UserProfileStub> userProfileList = new ArrayList<UserProfileStub>();

        String sql = "SELECT * FROM userproperties WHERE ";

        if (criteriaName != null && criteriaName.length() > 0) {
            sql += "user_name like '%" + criteriaName + "%'";
        }
        if (criteriaCity != null && criteriaCity.length() > 0) {
            if (criteriaName != null && criteriaName.length() > 0) {
                sql += " and ";
            }
            sql += "city like '%" + criteriaCity + "%'";
        }
        if (criteriaCollege != null && criteriaCollege.length() > 0) {
            if ((criteriaName != null && criteriaName.length() > 0) || (criteriaCity != null && criteriaCity.length() > 0)) {
                sql += " and ";
            }
            sql += "college like '%" + criteriaCollege + "%'";
        } else if ((criteriaName == null || criteriaName.length() < 1) && (criteriaCity == null || criteriaCity.length() < 1)) {
            // no criteria selected, return empty array
            return userProfileList;
        }
        //sql = "SELECT * FROM userproperties WHERE user_name like '%" + criteriaName + "%' or city like '%" + criteriaCity + "%'or college like '%" + criteriaCollege + "%'";

        ResultSet resultSet = DatabaseOperations.executeQuery(sql);

        try {
            System.out.println("<<POTENTIAL FRIEND SEARCH>> :");
            while (resultSet.next()) {
                UserProfile newProfile = new UserProfile();
                String userID = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                String userProfession = resultSet.getString("profession");
                String userCity = resultSet.getString("city");
                String userCompany = resultSet.getString("company");
                String userCollege = resultSet.getString("college");
                String userGraduationYear = resultSet.getString("graduation_year");

                newProfile.setUserID(userID);
                newProfile.setUserName(userName);
                newProfile.setProfession(userProfession);
                newProfile.setCity(userCity);
                newProfile.setCompany(userCompany);
                newProfile.setCollege(userCollege);
                newProfile.setGraduationYear(userGraduationYear);

                UserProfileStub t = (UserProfileStub) UnicastRemoteObject.exportObject(newProfile, 0);
                System.out.print(t.getUserID() + " " + t.getUserName() + " " + t.getProfession() + " " + t.getCity());
                System.out.println(" " + t.getCompany() + " " + t.getCollege() + " " + t.getGraduationYear());
                userProfileList.add(t);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SocialNetworkServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userProfileList;
    }

    @Override
    public String inviteFriend(String userID, String friendID) throws RemoteException {
        String response = null;
        AsyncCallback asyncCallback;

        if (databaseOperations.isMyFriend(userID, friendID) == 1) {
            response = friendID + " is already in your friend list";
        } else if (databaseOperations.isMyFriend(userID, friendID) == 2) {
            response = "A friend request is already sent to " + friendID;
        } else if (databaseOperations.userExists(friendID)) {
            String sql = "insert into roster (user_id, roster_id, status) values('" + userID + "', '" + friendID + "', '" + Constants.ROSTER_STATUS_PENDING + "')";
            if (DatabaseOperations.executeUpdate(sql)) {
                response = "A friend request is sent to the friend :)";
                System.out.println("<<FRIEND REQUEST>> FROM: \'" + userID + "\' TO: \'" + friendID + "\'");
            } else {
                System.err.println("Can't update database !!!");
            }

            asyncCallback = new AsyncCallback(userID, friendID, null, AsyncCallback.PUSH_REQUEST);
            asyncCallback.start();


        } else {
            response = "This username does not exists";
            System.out.println("USER: \'" + friendID + "\' DOES NOT EXIST.");
        }
        return response;
    }

    @Override
    public String acceptIgnoreFriend(String userID, String friendID, int decision) throws RemoteException {
        String response = null;
        String sql = null;
        AsyncCallback asyncCallbackUser;
        AsyncCallback asyncCallbackFriend;

        if (databaseOperations.isMyFriend(friendID, userID) == 1) {
            response = friendID + " is already in your friend list";
            return response;
        } else if (databaseOperations.isMyFriend(friendID, userID) == 0) {
            response = "you already ignored the request from " + friendID;
            return response;
        } else if (decision == 1) {
            sql = "update roster set status = " + Constants.ROSTER_STATUS_ACCEPTED + " where user_id = '" + friendID + "' and roster_id = '" + userID + "'";
            System.out.println("<<REQUEST ACCEPTED>> USER: \'" + userID + "\' ... FRIEND: \'" + friendID + "\'");
            response = "You are now friend with " + friendID;
        } else if (decision == 2) {
            sql = "delete from roster where user_id = \'" + friendID + "\' and roster_id = \'" + userID + "\'";
            System.out.println("<<REQUEST IGNORED>> USER: \'" + userID + "\' FRIEND: \'" + friendID + "\'");
            response = "You ignored the friend request from " + friendID;
        }

        if (DatabaseOperations.executeUpdate(sql)) {

            if (decision == 1) {
                // insert information in the user roster who have accepted the request
                sql = "insert into roster (user_id, roster_id, status) values('" + userID + "', '" + friendID + "', '" + Constants.ROSTER_STATUS_ACCEPTED + "')";
                if (DatabaseOperations.executeUpdate(sql)) {
                    // add this friend history as wall post
                    Post post = new Post();
                    post.setSender(userID);
                    post.setReceiver(friendID);
                    post.setType(2);
                    post.setDate(System.currentTimeMillis());
                    try {
                        DatabaseOperations.writeJavaObject(userID, friendID, post);
                    } catch (Exception ex) {
                        Logger.getLogger(SocialNetworkServant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // push back this history as they are online
                    ArrayList<Post> posts = new ArrayList<Post>();
                    posts.add(post);

                    asyncCallbackUser = new AsyncCallback(null, userID, posts, AsyncCallback.PUSH_POST);
                    asyncCallbackFriend = new AsyncCallback(null, friendID, posts, AsyncCallback.PUSH_POST);
                    asyncCallbackUser.start();
                    asyncCallbackFriend.start();
                }
            } else {
                System.err.println("Can't update database !!!");
            }
        } else {
            System.err.println("Can't update database !!!");
        }
        return response;
    }

    @Override
    public ArrayList<UserProfileStub> getFriends(String userID) throws RemoteException {
        ArrayList<UserProfileStub> listFriendsStub = new ArrayList<UserProfileStub>();
        ArrayList<String> listFriends;

        System.out.println("USER: \'" + userID + "\' RETRIEVING FRIENDLIST.");
        listFriends = databaseOperations.getFrindsList(userID);

        ListIterator<String> listIterator = listFriends.listIterator();

        System.out.println("<<FRIEND LIST>> :");
        while (listIterator.hasNext()) {
            String friendID = listIterator.next();
            System.out.println(friendID);

            UserProfile newUser = DatabaseOperations.getUserProfile(friendID);

            UserProfileStub t = (UserProfileStub) UnicastRemoteObject.exportObject(newUser, 0);
            System.out.print(t.getUserID() + " " + t.getUserName() + " " + t.getProfession() + " " + t.getCity());
            System.out.println(" " + t.getCompany() + " " + t.getCollege() + " " + t.getGraduationYear());
            listFriendsStub.add(t);
        }

        return listFriendsStub;
    }

    @Override
    public boolean registerCallBack(String userID, CallBackStub callBack) throws RemoteException {
        callBackMap.put(userID, callBack);

        // send this users notification (friend requests)
        callBack.pushRequest(databaseOperations.getFriendrequests(userID));

        return true;
    }

    @Override
    public boolean logout(String userID) throws RemoteException {
        callBackMap.remove(userID);
        System.out.println("USER: \'" + userID + "\' IS LOGGED OUT.");
        return true;
    }

    @Override
    public String sendMessage(String sender, String receiver, String text) throws RemoteException {
        CallBackStub callBack = callBackMap.get(receiver);
        String response;
        AsyncCallback asyncCallback;

        if (callBack == null) {
            response = "User is offline now, offline chatting is not available";
        } else {
            //callBack.pushMessage(sender, text);
            asyncCallback = new AsyncCallback(sender, receiver, text, AsyncCallback.PUSH_MESSAGE);
            asyncCallback.start();
            response = "success";
        }

        return response;
    }

    @Override
    public boolean isOnline(String friendID) throws RemoteException {
        // a online user always register a callback object
        CallBackStub callBack = callBackMap.get(friendID);
        if (callBack == null) {
            return false;
        } else {
            return true;
        }
    }
}
