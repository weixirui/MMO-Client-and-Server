package com.git.cs309.mmoclient.entity.character.npc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoserver.packets.ExtensiveCharacterPacket;
import com.git.cs309.mmoserver.util.WordUtils;

public final class NPCFactory {
	private static final NPCFactory INSTANCE = new NPCFactory();

	public static final NPCFactory getInstance() {
		return INSTANCE;
	}

	private final Map<String, NPCDefinition> definitionsByName = new HashMap<>();
	private final Map<Integer, NPCDefinition> definitionsByID = new HashMap<>();

	private NPCFactory() {
		try {
			loadDefinitions();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized final NPC createNPC(ExtensiveCharacterPacket packet) {
		NPCDefinition definition = definitionsByName.get(packet.getCharacterName().toLowerCase());
		if (definition == null) {
			throw new RuntimeException("No definition for NPC: "+packet.getCharacterName());
		}
		return new NPC(packet, definition);
	}

	public synchronized final void loadDefinitions() throws SAXException, IOException, ParserConfigurationException {
		definitionsByName.clear();
		definitionsByID.clear();
		Document definitionsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Config.NPC_CONFIG_PATH);
		Node rootNode = definitionsDocument.getFirstChild();
		NodeList baseNodes = rootNode.getChildNodes();
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			switch (baseNode.getNodeName().toLowerCase()) {
			case "npc":
				int id = Integer.MAX_VALUE;
				String name = "Null";
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
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					
				}
				NPCDefinition definition = new NPCDefinition(WordUtils.capitalizeText(name), id);
				definitionsByName.put(name.toLowerCase(), definition);
				definitionsByID.put(id, definition);
				break;
			}
		}
		System.out.println("Loaded " + definitionsByName.size() + " NPC definitions.");
	}
}
