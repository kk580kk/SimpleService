package com.baosight.smirk;

import java.util.HashMap;

public class SmirkTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	// TODO Auto-generated method stub
		SmirkMsgSender sms = new SmirkMsgSender();
		HashMap ha = new HashMap();
		ha.put("subject", "abc");
		ha.put("body", "yours");
//		sms.sendMsg("admin",MessageFactory.create("Hello"), null);
		HashMap hm = new HashMap();
		hm.put("body", "http://10.10.10.10");
		hm.put("business", "type1");
		hm.put("springBeanId", "abc");
		hm.put("methodName", "hello");
		sms.sendMsg("demo",MessageFactory.create("headline",hm), null);
		final SmirkMsgReceiver smr = new SmirkMsgReceiver();
		smr.init("message", new SmirkCallBack(){

			public void callback(SmirkMsgReceiver ins, String from,
					HashMap params) {
				// TODO Auto-generated method stub
				
			}

	
		});
		smr.close();
		SmirkManager.getInstance().shutdown();
	}

}
