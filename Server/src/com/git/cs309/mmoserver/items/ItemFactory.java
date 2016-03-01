package com.git.cs309.mmoserver.items;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.combat.CombatStyle;
import com.git.cs309.mmoserver.util.WordUtils;

public final class ItemFactory {
	private static final ItemFactory INSTANCE = new ItemFactory(Config.ITEM_DEFINITION_PATH);
	
	public static final ItemFactory getInstance() {
		return INSTANCE;
	}
	
	private final String definitionPath;
	private final HashMap<String, ItemDefinition> definitionsForName = new HashMap<>();
	private final HashMap<Integer, ItemDefinition> definitionsForID = new HashMap<>();
	
	private ItemFactory(final String definitionPath) {
		this.definitionPath = definitionPath;
	}
	
	public synchronized void loadDefinitions() throws SAXException, IOException, ParserConfigurationException {
		definitionsForName.clear();
		definitionsForID.clear();
		Document definitionsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(definitionPath);
		Node rootNode = definitionsDocument.getFirstChild();
		NodeList baseNodes = rootNode.getChildNodes();
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			switch (baseNode.getNodeName().toLowerCase()) {
			case "item":
				int id = Integer.MAX_VALUE;
				String name = "Null";
				boolean stackable = true;
				int price = 0;
				CombatStyle style = CombatStyle.NON_COMBAT;
				EquipmentSlot slot = EquipmentSlot.NO_SLOT;
				int strength = 0;
				int defence = 0;
				int level = 0;
				NodeList definitionNodes = baseNode.getChildNodes();
				for (int j = 0; j < definitionNodes.getLength(); j++) {
					Node definitionNode = definitionNodes.item(j);
					try {
						switch (definitionNode.getNodeName().toLowerCase()) {
						case "name":
							name = definitionNode.getTextContent();
							break;
						case "id":
							id = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "stackable":
							stackable = Boolean.parseBoolean(definitionNode.getTextContent());
							break;
						case "style":
							switch (definitionNode.getTextContent().toLowerCase()) {
							case "melee":
								style = CombatStyle.MELEE;
								break;
							case "ranged":
								style = CombatStyle.RANGED;
								break;
							case "magic":
								style = CombatStyle.MAGIC;
								break;
							}
							break;
						case "slot":
							switch (definitionNode.getTextContent().toLowerCase()) {
							case "helmet":
								slot = EquipmentSlot.HELMET;
								break;
							case "chest":
								slot = EquipmentSlot.CHEST;
								break;
							case "legs":
								slot = EquipmentSlot.LEGS;
								break;
							case "boots":
								slot = EquipmentSlot.BOOTS;
								break;
							case "gloves":
								slot = EquipmentSlot.GLOVES;
								break;
							case "right hand":
								slot = EquipmentSlot.RIGHT_HAND;
								break;
							case "left hand":
								slot = EquipmentSlot.LEFT_HAND;
								break;
							case "cape":
								slot = EquipmentSlot.CAPE;
								break;
							case "ammo":
								slot = EquipmentSlot.AMMO;
								break;
							case "both hands":
								slot = EquipmentSlot.BOTH_HANDS;
								break;
							}
							break;
						case "price":
							price = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "strength":
							strength = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "defence":
							defence = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "level":
							level = Integer.parseInt(definitionNode.getTextContent());
							break;
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				ItemDefinition definition = new ItemDefinition(WordUtils.capitalizeText(name), id, price, strength, defence, level, style, slot, stackable);
				definitionsForName.put(definition.getItemName().toLowerCase(), definition);
				definitionsForID.put(definition.getId(), definition);
				break;
			}
		}
		System.out.println("Loaded " + definitionsForName.size() + " Item definitions.");
	}
	
	public synchronized final ItemStack createItemStack(final String itemName) {
		ItemDefinition definition = definitionsForName.get(itemName);
		if (definition == null) {
			throw new RuntimeException("No item definition for item: "+itemName);
		}
		return new ItemStack(definition);
	}
	
	public synchronized final ItemStack createItemStack(final String itemName, final int count) {
		ItemDefinition definition = definitionsForName.get(itemName);
		if (definition == null) {
			throw new RuntimeException("No item definition for item: "+itemName);
		}
		return new ItemStack(definition, count);
	}
	
	public synchronized final ItemStack createItemStack(final int itemId) {
		ItemDefinition definition = definitionsForID.get(itemId);
		if (definition == null) {
			throw new RuntimeException("No item definition for item: "+itemId);
		}
		return new ItemStack(definition);
	}
	
	public synchronized final ItemStack createItemStack(final int itemId, final int count) {
		ItemDefinition definition = definitionsForID.get(itemId);
		if (definition == null) {
			throw new RuntimeException("No item definition for item: "+itemId);
		}
		return new ItemStack(definition, count);
	}
}
