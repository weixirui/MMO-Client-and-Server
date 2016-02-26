package com.git.cs309.mmoserver.entity.characters.user.party;

import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.entity.characters.user.User;

public final class Party {
	private final Set<User> partyMemebers = new HashSet<>();

	public void addUserToParty() {

	}

	public boolean partyFull() {
		return partyMemebers.size() >= Config.MAX_PARTY_MEMBERS;
	}
}
