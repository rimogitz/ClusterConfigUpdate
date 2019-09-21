package com.rlab.quicktest.mapconfig;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.security.UsernamePasswordCredentials;
import com.rlab.quicktest.login.common.IMDGLoginCredentials;

import java.security.AccessControlException;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by dbrimley on 19/05/2014.
 */
public class Client {

    private final ILogger logger = Logger.getLogger(getClass().getName());

    public static void main(String args[]){

        Client client = new Client();

        client.adminUserCanPutIntoImportantMap();
        client.adminUserCanPutIntotestMap();

      // client.readOnlyUserCannotPutIntoImportantMap();


    }

    private void readOnlyUserCannotPutIntoImportantMap() {

        HazelcastInstance readOnlyClient = getClientConnection("chris", "password2", "127.0.0.1");

        Map<String,String> readOnlyClientsImportantMap = readOnlyClient.getMap("importantMap");

        // This will pass
        logger.log(Level.INFO,"Chris is performing get on the ImportantMap");
        readOnlyClientsImportantMap.get("1");

        // This will fail as chris is not a member of the admin group
        try{
            logger.log(Level.INFO,"Chris is performing put on the ImportantMap");
            readOnlyClientsImportantMap.put("2","2");
        } catch (AccessControlException e){
            logger.log(Level.SEVERE,"Could not perform put operation, access denied",e);
        }
    }

    private void adminUserCanPutIntoImportantMap() {

        HazelcastInstance adminClient = getClientConnection("david", "password1", "127.0.0.1");

        Map<String,String> adminClientsImportantMap = adminClient.getMap("importantMap");

        // This will pass
        logger.log(Level.INFO,"David is performing put on the ImportantMap");
        adminClientsImportantMap.put("1","ONE");
    }
    
    private void adminUserCanPutIntotestMap() {

        HazelcastInstance adminClient = getClientConnection("david", "password1", "127.0.0.1");

        Map<String,String> adminClientsImportantMap = adminClient.getMap("testMap.one");

        // This will pass
        logger.log(Level.INFO,"################# David is performing put on the testMap.one");
        adminClientsImportantMap.put("1","1");
    }

    private HazelcastInstance getClientConnection(String username, String password, String thisClientIP) {

        ClientConfig clientConfig = new ClientConfig();
         
         //clientConfig.setCredentials(new UsernamePasswordCredentials(username, password));
         //clientConfig.getCredentials().setEndpoint(thisClientIP);
       
       IMDGLoginCredentials login_cred= new IMDGLoginCredentials(username,password,thisClientIP);
       clientConfig.setCredentials(login_cred);
       
        clientConfig.setLicenseKey("ENTERPRISE_HD#10Nodes#AqZdS9uJOQlyn0gB82CXEGmkwTHUY15iPK6fbjWMDN55109109020001000900910900010009111300110190");
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
