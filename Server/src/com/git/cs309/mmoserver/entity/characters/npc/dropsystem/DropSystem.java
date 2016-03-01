package com.git.cs309.mmoserver.entity.characters.npc.dropsystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.items.ItemStack;

public final class DropSystem {
	private static final DropSystem INSTANCE = new DropSystem(Config.NPC_DROPS_PATH);
	
	public static final DropSystem getInstance() {
		return INSTANCE;
	}
	
	private final String dropDefinitions;
	
	private final List<Rate> rates = new ArrayList<>();
	
	private final Map<String, NPCDrops> npcDropMap = new HashMap<>();
	
	private DropSystem(final String dropDefinitions) {
		this.dropDefinitions = dropDefinitions;
	}
	
	public synchronized final void loadDrops() throws SAXException, IOException, ParserConfigurationException {
		rates.clear();
		npcDropMap.clear();
		Document definitionsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(dropDefinitions);
		Node rootNode = definitionsDocument.getFirstChild();
		NodeList baseNodes = rootNode.getChildNodes();
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			switch (baseNode.getNodeName().toLowerCase()) {
			case "rate":
				rates.add(getRate(baseNode));
				break;
			case "drops":
				NPCDrops drops = getDrops(baseNode);
				npcDropMap.put(drops.getNpcName(), drops);
				break;
			}
		}
		System.out.println("Loaded "+npcDropMap.size()+" NPC drops.");
	}
	
	public synchronized final List<ItemStack> getDropsForNPC(final String npcName) {
		NPCDrops drops = npcDropMap.get(npcName.toLowerCase());
		if (drops == null) {
			return new ArrayList<ItemStack>();
		}
		return drops.getDrops();
	}
	
	private final Rate getRate(Node node) {
		NodeList baseNodes = node.getChildNodes();
		String name = "Null";
		float chance = 0.0f;
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			try {
				switch (baseNode.getNodeName().toLowerCase()) {
				case "name":
					name = baseNode.getTextContent().toLowerCase();
					break;
				case "chance":
					chance = Float.parseFloat(baseNode.getTextContent());
					break;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return new Rate(name, chance);
	}
	
	private final NPCDrops getDrops(Node node) {
		List<RateGroup> groups = new ArrayList<>();
		for (Rate rate : rates) {
			groups.add(new RateGroup(rate));
		}
		String name = "Null";
		NodeList baseNodes = node.getChildNodes();
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			switch (baseNode.getNodeName().toLowerCase()) {
			case "name":
				name = baseNode.getTextContent().toLowerCase();
				break;
			case "item":
				String itemName = "Null";
				int maximum = 0;
				int minimum = 0;
				String rateName = "Null";
				NodeList itemNodes = baseNode.getChildNodes();
				for (int j = 0; j < itemNodes.getLength(); j++) {
					Node itemNode = itemNodes.item(j);
					switch (itemNode.getNodeName().toLowerCase()) {
					case "name":
						itemName = itemNode.getTextContent().toLowerCase();
						break;
					case "rate":
						rateName = itemNode.getTextContent().toLowerCase();
						break;
					case "amount":
						try {
							int amount = Integer.parseInt(itemNode.getTextContent());
							maximum = amount;
							minimum = amount;
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						break;
					case "minimum":
						try {
							minimum = Integer.parseInt(itemNode.getTextContent());
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						break;
					case "maximum":
						try {
							maximum = Integer.parseInt(itemNode.getTextContent());
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						break;
					}
				}
				for (RateGroup group : groups) {
					if(group.getRateName().equalsIgnoreCase(rateName)) {
						group.addToGroup(new Drop(itemName, new Range(minimum, maximum)));
						break;
					}
				}
			}
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).empty()) {
				groups.remove(i--);
				continue;
			}
		}
		return new NPCDrops(name.toLowerCase(), groups);
	}
}
