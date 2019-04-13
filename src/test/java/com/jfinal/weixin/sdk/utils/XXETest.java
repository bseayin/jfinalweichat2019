package com.jfinal.weixin.sdk.utils;

public class XXETest {
	private static String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"   <!DOCTYPE c [\n" + 
			"       <!ENTITY file SYSTEM \"file:///etc/passwd\">\n" + 
			"   ]>\n" + 
			"   <c>&file;</c>";
	
	public static void main(String[] args) {
		XmlHelper helper = XmlHelper.of(xml);
		System.out.println(helper.getString("c"));
	}
}
