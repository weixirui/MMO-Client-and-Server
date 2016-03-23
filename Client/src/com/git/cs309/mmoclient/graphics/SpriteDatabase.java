package com.git.cs309.mmoclient.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.git.cs309.mmoclient.Config;

public final class SpriteDatabase {
	
	private static final SpriteDatabase INSTANCE = new SpriteDatabase();
	
	public static final SpriteDatabase getInstance() {
		return INSTANCE;
	}
	
	private final HashMap<String, Sprite> spriteTable = new HashMap<>();
	
	private SpriteDatabase() {
		loadSprites();
	}
	
	public final void loadSprites() {
		spriteTable.clear();
		File spriteFolder = new File(Config.SPRITE_FOLDER);
		for (File file : spriteFolder.listFiles()) {
			if (!(file.getName().endsWith(".png") || file.getName().endsWith(".jpg"))) {
				continue;
			}
			try {
				Sprite newSprite = new Sprite(file.getName().replace(".png", "").replace(".jpg", ""), toBufferedImage(ImageIO.read(new FileInputStream(file))));
				spriteTable.put(newSprite.getName().toLowerCase(), newSprite);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public final Sprite getSprite(final String name) {
		return spriteTable.get(name.toLowerCase());
	}
	
	public static BufferedImage toBufferedImage(Image image)
	{
	    if (image instanceof BufferedImage)
	    {
	        return (BufferedImage) image;
	    }
	    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bGr = bufferedImage.createGraphics();
	    bGr.drawImage(image, 0, 0, null);
	    bGr.dispose();
	    return bufferedImage;
	}

}
