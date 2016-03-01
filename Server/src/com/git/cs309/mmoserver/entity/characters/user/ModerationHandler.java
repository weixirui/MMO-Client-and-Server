package com.git.cs309.mmoserver.entity.characters.user;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.HashMap;

import com.git.cs309.mmoserver.Config;

/**
 * 
 * @author Group 21
 *
 *         <p>
 *         Holds and maintains each Moderation in the game.
 *         </p>
 */
public final class ModerationHandler {
	public static enum ModerationType {
		BAN, MUTE;
	}

	private static final class Moderation implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2317575644440895450L;
		private int endMonth;
		private int endDay;
		private int endYear;
		private ModerationType type;

		@SuppressWarnings("unused")
		public Moderation() {
			//Empty constructor for deserialization
		}

		public Moderation(final int duration, ModerationType moderationType) { // Duration in days
			this.type = moderationType;
			if (duration == -1) {
				endMonth = -1;
				endYear = -1;
				endDay = -1;
			} else if (YearMonth
					.of(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH))
					.lengthOfMonth() >= Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + duration) {
				endMonth = Calendar.getInstance().get(Calendar.MONTH);
				endDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + duration;
				endYear = Calendar.getInstance().get(Calendar.YEAR);
			} else if (duration < 365) {
				endMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
				endDay = duration % YearMonth
						.of(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH))
						.lengthOfMonth();
				endYear = Calendar.getInstance().get(Calendar.YEAR);
			} else {
				endMonth = Calendar.getInstance().get(Calendar.MONTH);
				endDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				endYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
			}
		}

		public String getExpirationDate() {
			return endMonth + "/" + endDay + "/" + endYear;
		}

		public ModerationType getType() {
			return type;
		}

		public boolean isExpired() {
			if (Calendar.getInstance().get(Calendar.YEAR) < endYear) {
				return false;
			} else if (Calendar.getInstance().get(Calendar.YEAR) > endYear) {
				return true;
			}
			if (Calendar.getInstance().get(Calendar.MONTH) < endMonth) {
				return false;
			} else if (Calendar.getInstance().get(Calendar.MONTH) > endMonth) {
				return true;
			}
			if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < endDay) {
				return false;
			}
			return true;
		}
	}

	private static HashMap<String, Moderation> moderations = new HashMap<String, Moderation>();

	public static void addModeration(String identifier, int duration, ModerationType type) {
		Moderation newModeration = new Moderation(duration, type);
		moderations.put(identifier, newModeration);
		saveModerations();
	}

	public static String getExpirationDate(String identifier) {
		Moderation moderation = moderations.get(identifier);
		if (moderation == null) {
			return "Not currently being moderated";
		}
		return moderation.getExpirationDate();
	}

	public static boolean isBanned(String identifier) {
		Moderation moderation = moderations.get(identifier);
		if (moderation == null) {
			return false;
		}
		boolean expired = moderation.isExpired();
		if (expired) {
			moderations.remove(identifier, moderation);
			saveModerations();
		}
		return !expired && moderation.getType() == ModerationType.BAN;
	}

	public static boolean isMuted(String identifier) { // Could be IP or Username
		Moderation moderation = moderations.get(identifier);
		if (moderation == null) {
			return false;
		}
		boolean expired = moderation.isExpired();
		if (expired) {
			moderations.remove(identifier, moderation);
			saveModerations();
		}
		return !expired;
	}

	@SuppressWarnings("unchecked")
	public static void loadModerations() {
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(Config.MODERATIONS_PATH));
			moderations = (HashMap<String, Moderation>) input.readObject();
			input.close();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Failed to load moderations from file " + Config.MODERATIONS_PATH);
		}
	}

	public static void removeModeration(String identifier) {
		Moderation moderation = moderations.get(identifier);
		if (moderation == null) {
			return;
		}
		moderations.remove(identifier, moderation);
		saveModerations();
	}

	public static void saveModerations() {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(Config.MODERATIONS_PATH));
			output.writeObject(moderations);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
