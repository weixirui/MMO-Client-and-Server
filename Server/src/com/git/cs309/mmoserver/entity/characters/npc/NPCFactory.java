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
import com.git.cs309.mmoserver.combat.CombatStyle;
import com.git.cs309.mmoserver.util.DefinitionMissingException;
import com.git.cs309.mmoserver.util.WordUtils;

public final class NPCFactory {
	private static final NPCFactory INSTANCE = new NPCFactory(Config.NPC_DEFINITION_PATH);

	public static final NPCFactory getInstance() {
		return INSTANCE;
	}

	private final Map<String, NPCDefinition> definitionsByName = new HashMap<>();
	private final Map<Integer, NPCDefinition> definitionsByID = new HashMap<>();
	private final String definitionPath;

	private NPCFactory(final String definitionFile) {
		this.definitionPath = definitionFile;
	}

	public synchronized final NPC createNPC(final int id, final int x, final int y, final int z,
			final int instanceNumber) {
		NPCDefinition definition = definitionsByID.get(id);
		if (definition == null) {
			throw new DefinitionMissingException("No definition could be found for NPC with id: " + id + "");
		}
		return new NPC(x, y, z, definition, instanceNumber);
	}

	public synchronized final NPC createNPC(final int id, final int x, final int y, final int z,
			final int instanceNumber, final boolean autoRespawn) {
		NPCDefinition definition = definitionsByID.get(id);
		if (definition == null) {
			throw new DefinitionMissingException("No definition could be found for NPC with id: " + id + "");
		}
		return new NPC(x, y, z, definition, instanceNumber, autoRespawn);
	}

	public synchronized final NPC createNPC(final String npcName, final int x, final int y, final int z,
			final int instanceNumber) {
		NPCDefinition definition = definitionsByName.get(npcName.toLowerCase());
		if (definition == null) {
			throw new DefinitionMissingException("No definition could be found for NPC \"" + npcName + "\"");
		}
		return new NPC(x, y, z, definition, instanceNumber);
	}

	public synchronized final NPC createNPC(final String npcName, final int x, final int y, final int z,
			final int instanceNumber, final boolean autoRespawn) {
		NPCDefinition definition = definitionsByName.get(npcName.toLowerCase());
		if (definition == null) {
			throw new DefinitionMissingException("No definition could be found for NPC \"" + npcName + "\"");
		}
		return new NPC(x, y, z, definition, instanceNumber, autoRespawn);
	}

	public synchronized final void loadDefinitions() throws SAXException, IOException, ParserConfigurationException {
		definitionsByName.clear();
		definitionsByID.clear();
		Document definitionsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(definitionPath);
		Node rootNode = definitionsDocument.getFirstChild();
		NodeList baseNodes = rootNode.getChildNodes();
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
				CombatStyle style = CombatStyle.NON_COMBAT;
				boolean autoRespawn = true;
				boolean canWalk = true;
				boolean aggressive = false;
				int respawnTimer = 1;
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
						case "walks":
							canWalk = Boolean.parseBoolean(definitionNode.getTextContent());
							break;
						case "aggressive":
							aggressive = Boolean.parseBoolean(definitionNode.getTextContent());
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
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				NPCDefinition definition = new NPCDefinition(WordUtils.capitalizeText(name), id, health, strength,
						accuracy, defence, level, autoRespawn, respawnTimer, canWalk, aggressive, style);
				definitionsByName.put(name.toLowerCase(), definition);
				definitionsByID.put(id, definition);
				break;
			}
		}
		System.out.println("Loaded " + definitionsByName.size() + " NPC definitions.");
	}
}
