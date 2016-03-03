package com.git.cs309.mmoserver.entity.objects;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.util.WordUtils;

public final class GameObjectFactory {
	private static final GameObjectFactory INSTANCE = new GameObjectFactory(Config.OBJECT_DEFINITION_PATH);

	public static final GameObjectFactory getInstance() {
		return INSTANCE;
	}

	private final HashMap<String, ObjectDefinition> definitionsByName = new HashMap<>();
	private final HashMap<Integer, ObjectDefinition> definitionsByID = new HashMap<>();
	private final String definitionPath;

	private GameObjectFactory(final String definitionPath) {
		this.definitionPath = definitionPath;
	}

	public synchronized final GameObject createGameObject(final int id, final int x, final int y, final int z,
			final int instanceNumber) {
		ObjectDefinition definition = definitionsByID.get(id);
		if (definition == null) {
			throw new RuntimeException("No definition for object with ID: " + id);
		}
		return new GameObject(definition, x, y, z, instanceNumber);
	}

	public synchronized final GameObject createGameObject(final String name, final int x, final int y, final int z,
			final int instanceNumber) {
		ObjectDefinition definition = definitionsByName.get(name.toLowerCase());
		if (definition == null) {
			throw new RuntimeException("No definition for object with name: " + name);
		}
		return new GameObject(definition, x, y, z, instanceNumber);
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
			case "object":
				int id = Integer.MAX_VALUE;
				String name = "Null";
				boolean walkable = false;
				boolean serverOnly = false;
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
						case "walkable":
							walkable = true;
							break;
						case "serveronly":
							serverOnly = true;
							break;
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				ObjectDefinition definition = new ObjectDefinition(WordUtils.capitalizeText(name), id, walkable, serverOnly);
				definitionsByName.put(definition.getObjectName().toLowerCase(), definition);
				definitionsByID.put(definition.getObjectID(), definition);
				break;
			}
		}
		System.out.println("Loaded " + definitionsByName.size() + " GameObject definitions.");
	}
}
