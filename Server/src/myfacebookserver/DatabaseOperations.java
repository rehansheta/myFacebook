/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfacebookserver;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import stubs.Post;
import stubs.UserProfile;
import stubs.UserProfileStub;

/**
 *
 * @author Rehan
 */
public class DatabaseOperations {

    private static Connection dbConnection = null;
    static final String SQL_TO_WRITE_OBJECT = "INSERT INTO wall(sender, receiver, post) VALUES (?, ?, ?)";
    static final String SQL_TO_CHECK_USER_ID = "SELECT user_id FROM userproperties WHERE user_id = ?";
    static final String SQL_TO_CHECK_PASSWORD = "SELECT password FROM userproperties WHERE user_id = ?";
    static final String SQL_TO_GET_FRIEND_LIST = "SELECT roster_id FROM roster WHERE user_id = ? and status = ?";

    void openConnection() {
        String url = "jdbc:mysql://Rehan-PC:3306/my_facebook";

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MyFacebookServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            dbConnection = DriverManager.getConnection(url, "root", "sheta");

        } catch (SQLException ex) {
            Logger.getLogger(MyFacebookServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized Connection getConnection() {
        return dbConnection;
    }

    void closeConnection() {
        try {
            getConnection().close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean executeUpdate(String sql) {
        try {
            Statement statement = getConnection().createStatement();
            int rs = statement.executeUpdate(sql);
            if (rs < 0) {
                // may be no row is updated
                return false;
            }
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static ResultSet executeQuery(String sql) {
        ResultSet resultSet = null;

        try {
            Statement statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            //statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    /**
     * This method will write a java object to the database Parameters: connection object and object to be serialized
     */
    public static boolean writeJavaObject(String userID, String receiver, Object object) throws Exception {
        String className = object.getClass().getName();
        PreparedStatement pstmt = getConnection().prepareStatement(SQL_TO_WRITE_OBJECT);

        pstmt.setString(1, userID);
        pstmt.setObject(2, receiver);
        pstmt.setObject(3, object);
        if (pstmt.executeUpdate() != 0) {
            System.out.println("Serialization Successful." + "\nSerialized Class: " + className);
            pstmt.close();
            return true;
        }
        return false;
    }

    /**
     * This class will de-serialize a java object from the database
     */
    public static ArrayList<Post> readJavaObject(String sql) throws Exception {
        ResultSet rs = DatabaseOperations.executeQuery(sql);

        ArrayList<Post> postArray = new ArrayList<Post>();

        while (rs.next()) {
            byte[] buf = rs.getBytes("post");
            ObjectInputStream objectIn = null;

            if (buf != null) {
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            }
            postArray.add((Post) objectIn.readObject());
        }
        rs.close();
        return postArray;
    }

    public boolean userExists(String user_id) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(SQL_TO_CHECK_USER_ID);
            pstmt.setString(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            String userID = null;

            if (rs.next()) {
                userID = rs.getString("user_id");
            }
            rs.close();
            pstmt.close();

            if (userID == null) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean passwordMatches(String user_id, String password) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(SQL_TO_CHECK_PASSWORD);
            pstmt.setString(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            String userPass = null;

            if (rs.next()) {
                userPass = rs.getString("password");
            }
            rs.close();
            pstmt.close();

            if (userPass.equals(password)) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<String> getFrindsList(String userID) {
        ArrayList<String> listFriends = new ArrayList<String>();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(SQL_TO_GET_FRIEND_LIST);
            pstmt.setString(1, userID);
            pstmt.setInt(2, Constants.ROSTER_STATUS_ACCEPTED);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                listFriends.add(resultSet.getString("roster_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFriends;
    }

    public static UserProfile getUserProfile(String userID) throws RemoteException {
        UserProfile userProfile = new UserProfile();
        ResultSet resultSet;

        System.out.println("RETRIEVING INFORMATION OF USER: \'" + userID + "\'");
        String sql = "SELECT * FROM userproperties WHERE user_id = \'" + userID + "\'";
        resultSet = executeQuery(sql);
        try {
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                String userProfession = resultSet.getString("profession");
                String userCity = resultSet.getString("city");
                String userCompany = resultSet.getString("company");
                String userCollege = resultSet.getString("college");
                String userGraduationYear = resultSet.getString("graduation_year");

                userProfile.setUserID(userId);
                userProfile.setUserName(userName);
                userProfile.setProfession(userProfession);
                userProfile.setCity(userCity);
                userProfile.setCompany(userCompany);
                userProfile.setCollege(userCollege);
                userProfile.setGraduationYear(userGraduationYear);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocialNetworkServant.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userProfile;
    }

    static String updateProfile(UserProfile profile) {
        try {
            String sql = "update userproperties set user_name='" + profile.getUserName()
                    + "', profession = '" + profile.getProfession()
                    + "', city = '" + profile.getCity()
                    + "', company = '" + profile.getCompany()
                    + "', college = '" + profile.getCollege()
                    + "', graduation_year = '" + profile.getGraduationYear()
                    + "' where user_id='" + profile.getUserID() + "'";
            if (executeUpdate(sql)) {
                return "Profile saved";
            }
        } catch (RemoteException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Error in profile modification";
    }

    // sending the user id's of the requesting friends
    public ArrayList<UserProfileStub> getFriendrequests(String userID) {
        ArrayList<UserProfileStub> listFriendsStub = new ArrayList<UserProfileStub>();
        try {
            String sql = "select user_id from roster where roster_id = '" + userID + "' and status = " + Constants.ROSTER_STATUS_PENDING;
            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                UserProfile newUser = DatabaseOperations.getUserProfile(resultSet.getString("user_id"));
                UserProfileStub t = (UserProfileStub) UnicastRemoteObject.exportObject(newUser, 0);
                listFriendsStub.add(t);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(SocialNetworkServant.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SocialNetworkServant.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listFriendsStub;
    }
    
    // return value 0 = not friend -> database status = no entry
    // return value 1 = friend -> database status = 1
    // return value 2 = pending -> database status = 0
    public int isMyFriend(String userID, String friendID) {
        try {
            String sql = "select * from roster where user_id = '" + userID + "' and roster_id = '" + friendID + "'";
            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                int status = resultSet.getInt("status");
                if (status == Constants.ROSTER_STATUS_PENDING) {
                    return 2;
                } else if (status == Constants.ROSTER_STATUS_ACCEPTED) {
                    return 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocialNetworkServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
