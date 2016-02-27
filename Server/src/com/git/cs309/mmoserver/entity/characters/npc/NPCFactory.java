package com.git.cs309.mmoserver.entity.characters.npc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.util.DefinitionMissingException;
import com.git.cs309.mmoserver.util.WordUtils;

public final class NPCFactory {
	public static final NPCFactory SINGLETON = new NPCFactory(Config.NPC_DEFINITION_PATH);

	public static final NPCFactory getInstance() {
		return SINGLETON;
	}

	public static final NPCFactory newInstance(final String definitionFile) {
		return new NPCFactory(definitionFile);
	}

	private final Map<String, NPCDefinition> nameMap = new HashMap<>();
	private final Map<Integer, NPCDefinition> idMap = new HashMap<>();

	private NPCFactory(final String definitionFile) {
		try {
			loadDefinitions(definitionFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private final void loadDefinitions(final String definitionFile)
			throws SAXException, IOException, ParserConfigurationException {
		Document definitionsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(definitionFile);
		NodeList baseNodes = definitionsDocument.getChildNodes();
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			switch (baseNode.getNodeName().toLowerCase()) {
			case "npc":
				int accuracy = 0;
				int strength = 0;
				int defence = 0;
				int health = 1;
				int level = 1;
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
						case "health":
							health = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "level":
							level = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "accuracy":
							accuracy = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "strength":
							strength = Integer.parseInt(definitionNode.getTextContent());
							break;
						case "defence":
							defence = Integer.parseInt(definitionNode.getTextContent());
							break;
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				NPCDefinition definition = new NPCDefinition(WordUtils.capitalizeText(name), id, health, strength, accuracy, defence, level);
				nameMap.put(name.toLowerCase(), definition);
				idMap.put(id, definition);
				break;
			default:
				System.err.println("No case for node with name: "+baseNode.getNodeName().toLowerCase());
				break;
			}
		}
	}

	public final NPC createNPC(final String npcName, final int x, final int y, final int z, final int instanceNumber) {
		NPCDefinition definition = nameMap.get(npcName.toLowerCase());
		if (nameMap == null) {
			throw new DefinitionMissingException("No definition could be found for NPC \""+npcName+"\"");
		}
		return new NPC(x, y, z, definition, instanceNumber);
	}
	
	public final NPC createNPC(final int id, final int x, final int y, final int z, final int instanceNumber) {
		NPCDefinition definition = idMap.get(id);
		if (nameMap == null) {
			throw new DefinitionMissingException("No definition could be found for NPC with id: "+id+"");
		}
		return new NPC(x, y, z, definition, instanceNumber);
	}
}
