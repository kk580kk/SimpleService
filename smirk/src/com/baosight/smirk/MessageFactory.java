package com.baosight.smirk;

import java.util.HashMap;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.XMPPError;

public class MessageFactory {
	
	public static Message create(HashMap params)
	{
		return create("normal",params);
	}
	
	public static Message create(String type,HashMap params)
	{
		Message msgObj = new Message();
		for(Object key : params.keySet())
		{
			if("cid".equals(key))
			{
				String to = params.get(key) + "@" + SmirkConfiguration.getDomain();
				msgObj.setTo(to);
			}else if("jid".equals(key))
			{
				msgObj.setTo((String) params.get(key));
			}else if("subject".equals(key))
			{
				msgObj.setSubject((String) params.get(key));
			}else if("body".equals(key))
			{
				msgObj.setBody((String) params.get(key));
			}else if("error".equals(key))
			{
				//assume the error obj is an string 
				msgObj.setError(new XMPPError(0, type));
			}
			else{
				msgObj.setProperty((String) key, params.get(key));
			}
		}
		
		if("chat".equals(type))
		{
			msgObj.setType(Message.Type.chat);
		}else if("groupchat".equals(type))
		{
			msgObj.setType(Message.Type.groupchat);
		}else if("headline".equals(type))
		{
			msgObj.setType(Message.Type.headline);
		}else if("normal".equals(type))
		{
			msgObj.setType(Message.Type.normal);
		}
		
		return msgObj;
	}
	
	public static Message missingError(String message)
	{
		Message error = new Message();
		error.setError(new XMPPError(XMPPError.Condition.item_not_found,message));
		return error;
	}
	
	public static Message forbidError(String message)
	{
		Message error = new Message();
		error.setError(new XMPPError(XMPPError.Condition.forbidden,message));
		return error;
	}
	
	public static Message create(String message)
	{
		HashMap params = new HashMap();
		params.put("body",message);
		return create("chat", params);
	}
}
