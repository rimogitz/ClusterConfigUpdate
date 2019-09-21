package com.rlab.quicktest.mapconfig;

import java.io.FileNotFoundException;

import com.hazelcast.config.Config;
import com.hazelcast.config.ConfigXmlGenerator;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.PermissionConfig;
import com.hazelcast.config.PermissionConfig.PermissionType;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Client;
import com.hazelcast.core.ClientListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.security.permission.MapPermission;


import com.hazelcast.security.permission.ActionConstants;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singleton;


public class MemberLite {

    public static void main(String args[]){

        //
    	
    	/*Config config = new Config();
    	config.setLicenseKey("ENTERPRISE_HD#10Nodes#xxxxxxxxxxxxxxxs");
    	NetworkConfig network = config.getNetworkConfig();
    	network.setPort(5701).setPortCount(20);
    	network.setPortAutoIncrement(true);
    	
    	//network.setInterfaces(new InterfacesConfig("127.0.0.1"));
    	JoinConfig join = network.getJoin();
    	join.getMulticastConfig().setEnabled(false);
    	join.getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");*/
    	  
    	Config cfg=null;
		try {
			cfg = new XmlConfigBuilder(".\\src\\main\\resource\\hazelcast-lite.xml").build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(cfg);
    	
    	setPermissionsFromXML(hazelcastInstance,cfg);
        
      
        
    }

    static void setPermissionsFromCode(HazelcastInstance hazelcastInstance){
    	//MapConfig testMap1 = new MapConfig("testMap.*").setBackupCount(1);  			    			
        //hazelcastInstance.getConfig().addMapConfig(testMap1);
        
        PermissionConfig newTestMap1Permission = new PermissionConfig(PermissionType.MAP, "testMap.*", "adminGroup")
                .addAction(ActionConstants.ACTION_READ)
                .addAction(ActionConstants.ACTION_CREATE)
                .addAction(ActionConstants.ACTION_PUT);
        
        Set<PermissionConfig> cpcs = hazelcastInstance.getConfig().getSecurityConfig().getClientPermissionConfigs();
        
        Set<PermissionConfig>new_cpcs=new HashSet<PermissionConfig>();
        
        for(PermissionConfig pc:cpcs){
        	new_cpcs.add(pc);
        }
        
        new_cpcs.add(newTestMap1Permission);
        
        hazelcastInstance.getConfig().getSecurityConfig().setClientPermissionConfigs(new_cpcs);
        
         String perm_xml =  new com.hazelcast.config.ConfigXmlGenerator().generate(hazelcastInstance.getConfig());
      
        System.out.println(perm_xml);
    }
    
    // use this method to set the new config on the cluster 
    static void setPermissionsFromXML(HazelcastInstance hazelcastInstance,Config config){
    	hazelcastInstance.getConfig().getSecurityConfig().setClientPermissionConfigs(config.getSecurityConfig().getClientPermissionConfigs());
    	}
}
