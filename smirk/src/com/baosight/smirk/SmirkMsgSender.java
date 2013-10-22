package com.baosight.smirk;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class SmirkMsgSender {
	
	//sender
	
	
	public SmirkMsgSender()
	{
		
	}
	
	/**
	 * easy message sender
	 * @param name cid
	 * @param msgToSend Message to send
	 * @param bussniessListener your own listener can be null if for only send condition
	 */
	public void sendMsg(String name,Message msgToSend,MessageListener bussniessListener)
	{
		SmirkManager smirkManager = SmirkManager.getInstance();
		String jid = name + "@" +SmirkConfiguration.getDomain();
		if(smirkManager.getGlobalConnection().isConnected() && smirkManager.getGlobalConnection().isAuthenticated()){
			Chat tempChat = smirkManager.getGlobalConnection().getChatManager().createChat(jid,bussniessListener);
			if(msgToSend.getTo() == null || "".equals(msgToSend.getTo()))
			{
				msgToSend.setTo(jid);
			}
	    	try {
				tempChat.sendMessage(msgToSend);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("XMPP connection is not connected or authenticated");
		}
	}
}
