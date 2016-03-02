package com.git.cs309.mmoclient.entity.object;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.git.cs309.mmoclient.Config;
import com.git.cs309.mmoserver.packets.ExtensiveObjectPacket;
import com.git.cs309.mmoserver.util.WordUtils;

public final class GameObjectFactory {
	private static final GameObjectFactory INSTANCE = new GameObjectFactory();

	public static final GameObjectFactory getInstance() {
		return INSTANCE;
	}

	private final HashMap<String, ObjectDefinition> definitionsByName = new HashMap<>();
	private final HashMap<Integer, ObjectDefinition> definitionsByID = new HashMap<>();

	private GameObjectFactory() {
		try {
			loadDefinitions();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public synchronized final GameObject createGameObject(ExtensiveObjectPacket packet) {
		ObjectDefinition definition = definitionsByName.get(packet.getName().toLowerCase());
		if (definition == null) {
			throw new RuntimeException("No definition for object with name: " + packet.getName());
		}
		return new GameObject(packet, definition);
	}

	public synchronized final void loadDefinitions() throws SAXException, IOException, ParserConfigurationException {
		definitionsByName.clear();
		definitionsByID.clear();
		Document definitionsDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Config.OBJECT_CONFIG_PATH);
		Node rootNode = definitionsDocument.getFirstChild();
		NodeList baseNodes = rootNode.getChildNodes();
		for (int i = 0; i < baseNodes.getLength(); i++) {
			Node baseNode = baseNodes.item(i);
			switch (baseNode.getNodeName().toLowerCase()) {
			case "object":
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
				ObjectDefinition definition = new ObjectDefinition(WordUtils.capitalizeText(name), id);
				definitionsByName.put(definition.getObjectName().toLowerCase(), definition);
				definitionsByID.put(definition.getObjectID(), definition);
				break;
			}
		}
		System.out.println("Loaded " + definitionsByName.size() + " GameObject definitions.");
	}
}
