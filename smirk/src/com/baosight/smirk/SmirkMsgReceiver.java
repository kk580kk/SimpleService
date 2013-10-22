package com.baosight.smirk;

import java.util.HashMap;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class SmirkMsgReceiver implements PacketListener {
	
	private SmirkCallBack scb = null;
	private PacketFilter packetFilter = null;
	public SmirkMsgReceiver()
	{
		
	}
	
	/**
	 * init a receiver
	 * @param type
	 */
	public void init(String type,SmirkCallBack smirkCallBack)
	{
		
		SmirkManager.getInstance().addReceiver(this);
		this.packetFilter = createFilter(type);
		scb = smirkCallBack;
	}
	
	
	private PacketTypeFilter createFilter(String type)
	{
		if("message".equals(type.toLowerCase()))
		{
			return new PacketTypeFilter(Message.class);
		}else if("presence".equals(type.toLowerCase()))
		{
			return new PacketTypeFilter(Message.class);
		}else if("iq".equals(type.toLowerCase()))
		{
			return new PacketTypeFilter(IQ.class);
		}else{
			System.out.println("PacketFilter type error");
			return null;
		}
	}
	
	/**
	 * TODO other stanza
	 */
	public void processPacket(Packet packet) {
		HashMap params = new HashMap();
		//general deal with
		String from = packet.getFrom();
		if(packet instanceof Message)
		{
			params.put("body", ((Message) packet).getBody());
			params.put("subject",((Message) packet).getSubject());
			for(String name : packet.getPropertyNames())
			{
				params.put(name, packet.getProperty(name));
			}
			
		}else if (packet instanceof Presence)
		{
			
			
		}else if(packet instanceof IQ)
		{
			
		}
		
		scb.callback(this, from ,params);
	}
	
	public void close()
	{
		SmirkManager.getInstance().removeReceiver(this);
		scb = null;
	}

	public PacketFilter getPacketFilter() {
		return packetFilter;
	}

	public void setPacketFilter(PacketFilter packetFilter) {
		this.packetFilter = packetFilter;
	}

}
