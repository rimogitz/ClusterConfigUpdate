


	1. Update hazelcast.xml and hazelcast-lite.xml with the license-key
	  <hazelcast>
	…
	<license-key>ENTERPRISE#[N]Nodes#xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</license-key>
	…
	</hazelcast> 
	 This is required because ClientSecurityPermissions is an enterprise feature.
	2. Start Standard Server Member by launching Member.java  . 
	3. Hazelcast.xml configuration does not include permission for "admin" user to access map names starting with 'testMap.*' nor it includes MapConfig for 'testMap.*'
	4. Launch Client.java.  It will  connect to the cluster as user admin and try to create map 'testMap.one' and try to put data 
	5. Check log file for exception.  Hazelcast will not allow admin user to create the map 'testMap.one' as permission is not configured.  Below exceptions is thrown when it tries to execute the line 
	        Map<String,String> adminClientsImportantMap = adminClient.getMap("testMap.one"); 
	     
	 
	INFO: hz.client_1 [dev] [3.12] 
	
	Members [1] {
		Member [127.0.0.1]:5701 - b80a65b1-0eb7-49ad-aeec-45a1a1b3b487
	}
	
	Sep 21, 2019 12:27:03 PM com.hazelcast.core.LifecycleService
	INFO: hz.client_1 [dev] [3.12] HazelcastClient 3.12 (20190409 - f68a315, 915d83a) is CLIENT_CONNECTED
	Sep 21, 2019 12:27:03 PM com.hazelcast.internal.diagnostics.Diagnostics
	INFO: hz.client_1 [dev] [3.12] Diagnostics disabled. To enable add -Dhazelcast.diagnostics.enabled=true to the JVM arguments.
	Exception in thread "main" java.security.AccessControlException: Permission ("com.hazelcast.security.permission.MapPermission" "testMap.one" "create") denied!
		at com.hazelcast.security.SecurityContextImpl.checkPermission(SecurityContextImpl.java:159)
		at com.hazelcast.client.impl.protocol.task.AbstractMessageTask.checkPermissions(AbstractMessageTask.java:186)
		at com.hazelcast.client.impl.protocol.task.AbstractMessageTask.initializeAndProcessMessage(AbstractMessageTask.java:128)
		……
		at com.rlab.quicktest.mapconfig.Client.adminUserCanPutIntotestMap(Client.java:68)
		at com.rlab.quicktest.mapconfig.Client.main(Client.java:27)
		
	6. To update the configuration without the need to restart the cluster, start a lite member which will connect and  set the mapconfig and client security permissions.
	7. Start the lite member by launching MemberLite.java which uses hazelcast-lite.xml config  to connect to the cluster.
	8. Hazelcast-lite.xml includes the MapConfig and ClientSercurityPemissions config which grants admin access to testMap.* data structures in the cluster.
	 
	   <map name="testMap.*"/>  
	    &
	   
	<security enabled="true">
	        <client-login-modules>
	                <login-module class-name="com.rlab.quicktest.mapconfig.IMDGCustomLoginModule" usage="REQUIRED">
	                <properties>
	                    <property name="lookupFilePath">value3</property>
	                </properties>
	            </login-module>
	        </client-login-modules>
	        <client-permissions>
	            ...
	             <map-permission name="testMap.*" principal="adminGroup">
	                <actions>
	                    <action>create</action>
	                    <action>destroy</action>
	                    <action>put</action>
	                    <action>read</action>
	                </actions>
	            </map-permission>  
	            
	        </client-permissions>
	    </security>
	
	
	9.  Start the client again . This time the client can connect and create 'testMap.one' and put data .
	
