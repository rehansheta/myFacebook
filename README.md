MyFacebook
=========

This project is a prototype of a social network application that consists of a Server program and a Client program implemented using Java RMI. Here, the Server program creates/maintains a SocialNetworkServant remote object. The object provides the following basic functions:
1. createAccount, which creates a UserAccount object based on the provided information and returns its remote object reference (ROR) to client;<br>
2. loginAccount, which return a user's ROR based on the login information;<br>
3. searchForFriends, which allows users to search for potential friends based on living city and/or college and should return a list of users' RORs;<br>
4. inviteFriend, which allows a user X to invite another user Y to be a friend; however, Y should be added to X's friend list only after Y's confirmation.<br>
The UserAccount objects should maintain a list of friends' RORs and an information wall (a list of messages with timestamps). The user objects should have the following functions:<br>
1. viewProfile/updateProfile, which allow users to view and update their profile information. The profile can contain: user name, profession, living city, company, college name and graduation year etc;<br>
2. postUpdates: which allows a user to post new information on the walls of his/her friends;<br>
3. getUpdates, which allows a user to retrieve the new updates from all his/her friends. For the Client program, it should provide an interactive user interface (text or GUI based) that allows users to interact with the Server program (such as accept user's input, invoke corresponding RMIs and display the returned information.)
