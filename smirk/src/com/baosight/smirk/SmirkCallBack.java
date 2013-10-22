package com.baosight.smirk;

import java.util.HashMap;

public interface SmirkCallBack {
	public void callback(SmirkMsgReceiver ins, String from, HashMap params);
}
