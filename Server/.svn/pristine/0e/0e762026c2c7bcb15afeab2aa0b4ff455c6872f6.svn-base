/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfacebookserver;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import stubs.SocialNetworkServantStub;

/**
 *
 * @author Rehan
 */
public class MyFacebookServer {

    void init() {
        try {
            Registry registry = LocateRegistry.getRegistry();

            SocialNetworkServant obj = new SocialNetworkServant();
            SocialNetworkServantStub stub = (SocialNetworkServantStub) UnicastRemoteObject.exportObject(obj, 0);
            registry.rebind(Constants.SOCIAL_NETWORK_SERVANT_OBJ_NAME, stub);

            System.out.println("Service registration complete");
            System.out.println("Server is running");
        } catch (Exception e) {
            System.out.println("Service registration failed, check RMIREGISTRY");
            System.out.println("Server is not started");
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        MyFacebookServer server = new MyFacebookServer();
        server.init();
    }
}