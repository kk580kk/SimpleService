package com.baosight.smirk;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLDigestMD5Mechanism;

public class SmirkManager {

	private XMPPConnection globalConnection = null;
	private static SmirkManager ins= null;
	private Timer timer = new Timer();
	private ArrayList<SmirkMsgReceiver> list = new ArrayList<SmirkMsgReceiver>();
	
	
	private SmirkManager()
	{
	 //start new timer
	 setGlobalConnection(createConnection());
	 
	
    
	}
	
	public static SmirkManager getInstance()
	{
		if(ins == null)
		{
			ins = new SmirkManager();
		}
		return ins;
	}
	
	public  void startTimer()
	{
	  long minute = 1000 * 20;
	  timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!isConnected()) {
                    createConnection();
                }
            }
        }, minute, minute);
	}
	
	public void closeTimer()
	{
		timer.cancel();
	}
	
	//return an main connection
	public  XMPPConnection getGlobalConnection()
	{
		return globalConnection;
	}
	
	public synchronized XMPPConnection createConnection()
	{
		ConnectionConfiguration conf = SmirkConfiguration.getConf();
		conf.setSASLAuthenticationEnabled(true);
		XMPPConnection xmppConn = new XMPPConnection(conf);
		try {
			xmppConn.connect();
			if(SmirkConfiguration.getUserName() == null || SmirkConfiguration.getPassword() == null)
			{
				xmppConn.loginAnonymously();
			}else{
				
				SASLAuthentication.registerSASLMechanism("DIGEST-MD5", SASLDigestMD5Mechanism.class);
				SASLAuthentication.supportSASLMechanism("DIGEST-MD5", 0);
				if(SmirkConfiguration.getResource() == null)
					xmppConn.login(SmirkConfiguration.getUserName(),  SmirkConfiguration.getPassword());
				else
					xmppConn.login(SmirkConfiguration.getUserName(),  SmirkConfiguration.getPassword(),SmirkConfiguration.getResource());
			}
			
			xmppConn.addConnectionListener(new ConnectionListener() {
	                public void connectionClosed() {
	                    System.out.println("Main Connection closed for some reason");
	                }

	                public void connectionClosedOnError(Exception e) {
	                	System.out.println("Connection closed on Error:"+e.getMessage());
	                }

	                public void reconnectingIn(int i) {

	                }

	                public void reconnectionSuccessful() {

	                }

	                public void reconnectionFailed(Exception exception) {

	                }
	            });
		
			for(SmirkMsgReceiver smr : list)
			{
				xmppConn.addPacketListener(smr, smr.getPacketFilter());
			}
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            // If creating the connection failed this time around, make sure it is cleaned up.
            if (xmppConn != null && !xmppConn.isAuthenticated() && xmppConn.isConnected()) {
                try {
                    xmppConn.disconnect();
                }
                catch (Exception e) {
                    System.out.println("Error disconnecting from server."+ e.getMessage());
                }
            }
        }
        return xmppConn;
	}
	
	public boolean isConnected() {
	    XMPPConnection con = getGlobalConnection();
	    return con != null && (con.isConnected() && con.isAuthenticated());
	}
	
	public void setGlobalConnection(XMPPConnection con) {
	        globalConnection = con;
    }
	
	public void shutdown()
	{
		closeTimer();
		list.clear();
		if(globalConnection != null && globalConnection.isConnected())
		{
			globalConnection.disconnect();
		}
		
		globalConnection = null;
	}

	public void addReceiver(SmirkMsgReceiver smirkMsgReceiver) {
		list.add(smirkMsgReceiver);
		globalConnection.addPacketListener(smirkMsgReceiver, smirkMsgReceiver.getPacketFilter());
	}
	
	public void removeReceiver(SmirkMsgReceiver smirkMsgReceiver)
	{
		list.remove(smirkMsgReceiver);
		globalConnection.removePacketListener(smirkMsgReceiver);
	}
}
