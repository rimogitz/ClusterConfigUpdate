package com.rlab.quicktest.mapconfig;

import java.io.FileNotFoundException;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Client;
import com.hazelcast.core.ClientListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Member {

    public static void main(String args[]){

    	
    	
    	Config cfg=null;
		try {
			cfg = new XmlConfigBuilder(".\\src\\main\\resource\\hazelcast.xml").build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(cfg);

        hazelcastInstance.getClientService().addClientListener(new ClientListener() {

            public void clientConnected(Client client) {
              //  ClientEndpoint clientEndpoint = (ClientEndpoint) client;
                System.out.println("Connected");
            }

            public void clientDisconnected(Client client) {

            }
        });
    }

}
