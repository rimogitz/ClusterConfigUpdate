package com.rlab.quicktest.login.common;

import java.io.IOException;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.security.Credentials;

public class IMDGLoginCredentials implements Credentials, Portable {
	
	private String endPoint;
	private String principal;
	private String password;
	final static int ID=1;

	

	public IMDGLoginCredentials() {
		super();
	}

	public IMDGLoginCredentials( String principal, String password,String endPoint) {
		super();
		this.endPoint = endPoint;
		this.principal = principal;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public int getClassId() {
		// TODO Auto-generated method stub
		return this.ID;
	}

	public int getFactoryId() {
		// TODO Auto-generated method stub
		return ImdgPortableFactory.factoryID;
	}

	public void readPortable(PortableReader pReader) throws IOException {
		endPoint=pReader.readUTF("endPoint");
		principal=pReader.readUTF("principal");
		password=pReader.readUTF("password");
	}

	public void writePortable(PortableWriter pWriter) throws IOException {
		pWriter.writeUTF("endPoint",endPoint);
		pWriter.writeUTF("principal",principal);
		pWriter.writeUTF("password",password);

	}

	public String getEndpoint() {
		return this.endPoint;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public void setEndpoint(String _endPoint) {
		this.endPoint=_endPoint;
	}

}
