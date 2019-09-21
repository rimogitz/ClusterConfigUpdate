package com.rlab.quicktest.login.common;

import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableFactory;

public class ImdgPortableFactory implements PortableFactory {

	final static int factoryID=1;
	public Portable create(int _id) {
		if(IMDGLoginCredentials.ID == _id )
		   return new IMDGLoginCredentials();
		return null;
	}

}
