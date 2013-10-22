package com.baosight.smirk;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jivesoftware.smack.ConnectionConfiguration;

public class SmirkConfiguration {

	private static Properties prop = null;
	private static String domain = null;
	private static String ip = null;
	private static String port = null;
	private static ConnectionConfiguration conf = null;
	private static String userName = null;
	private static String password = null;
	private static String resource = null;
	
	static{
		try {
			//init configuration
			InputStream in  = SmirkManager.class.getResourceAsStream("/smirk.properties");
			prop = new Properties();
			prop.load(in);
			domain = prop.getProperty("xmppDomain", "localhost");
			port = prop.getProperty("serverPort","5222");
			ip = prop.getProperty("serverIP");
			if(ip == null)
			{
				conf = new ConnectionConfiguration(domain,Integer.parseInt(port));
			}else{
				conf = new ConnectionConfiguration(ip,Integer.parseInt(port),domain);
			}
			
			setUserName(prop.getProperty("userName"));
			setPassword(prop.getProperty("password"));
			setResource(prop.getProperty("resource"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static String getDomain() {
		return domain;
	}

	public static void setDomain(String domain) {
		SmirkConfiguration.domain = domain;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		SmirkConfiguration.ip = ip;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		SmirkConfiguration.port = port;
	}

	public static ConnectionConfiguration getConf() {
		return conf;
	}

	public static void setConf(ConnectionConfiguration conf) {
		SmirkConfiguration.conf = conf;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		SmirkConfiguration.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		SmirkConfiguration.password = password;
	}

	public static String getResource() {
		return resource;
	}

	public static void setResource(String resource) {
		SmirkConfiguration.resource = resource;
	}
}
