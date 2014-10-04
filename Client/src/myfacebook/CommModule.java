/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfacebook;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import stubs.SocialNetworkServantStub;

/**
 *
 * @author Riyad
 */
public class CommModule {

    Registry registry;
    SocialNetworkServantStub servantStub;
    
    public CommModule() {
        try {
            registry = LocateRegistry.getRegistry(Constants.REGISTRY_HOST);
            servantStub = (SocialNetworkServantStub) registry.lookup(Constants.SOCIAL_NETWORK_SERVANT_OBJ_NAME);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SERVER IS DOWN", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public SocialNetworkServantStub getServantStub() {
        return servantStub;
    }
}
