package com.git.cs309.mmoclient.NPC;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import serverTest.NPC;

public class GetNPC {
	public static HashMap<Integer, NPC> map = new HashMap<Integer, NPC>();
	
	public static void getnpc(int num){
		String numstr = String.valueOf(num);
		String path = "npc\\npc"+numstr+".xml";
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			setNPC(doc,num);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void setNPC(Node doc,int num){
		String name = "";
		int hp=0;
		int level=0;
		int exp=0;
		int money=0;
		int islive=0;
		String talk="";
		Node node = doc.getFirstChild();
		System.out.println(node.getNodeName());
		NodeList nodes = node.getChildNodes();
		for(int i=0;i<nodes.getLength();i++){
			Node n = nodes.item(i);
			String str = n.getNodeName();
			if(n instanceof Element){
				if(str.equals("name")){
					name = n.getTextContent();
				}else if(str.equals("hp")){
					hp = Integer.parseInt(n.getTextContent());
				}else if(str.equals("level")){
					level = Integer.parseInt(n.getTextContent());
				}else if(str.equals("exp")){
					exp = Integer.parseInt(n.getTextContent());
				}else if(str.equals("money")){
					money = Integer.parseInt(n.getTextContent());
				}else if(str.equals("islive")){
					islive = Integer.parseInt(n.getTextContent());
				}else if(str.equals("talk")){
					talk = n.getTextContent();
				}
				
			}
		}
		NPC npc = new NPC(name, hp, level, exp, money, islive, talk);
		
		map.put(num, npc);
		System.out.println(npc.getNPCName());
	}
}