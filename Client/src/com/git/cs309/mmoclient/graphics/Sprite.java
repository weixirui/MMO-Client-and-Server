package com.git.cs309.mmoclient.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;

public final class Sprite {
	private final BufferedImage spriteImage;
	private final String spriteName;
	
	public Sprite(String spriteName, BufferedImage spriteImage) {
		this.spriteImage = spriteImage;
		this.spriteName = spriteName;
	}
	
	public final Image getImage() {
		return spriteImage;
	}
	
	public final String getName() {
		return spriteName;
	}
}
