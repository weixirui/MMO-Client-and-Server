package com.git.cs309.mmoserver.entity.characters.user.party;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.git.cs309.mmoserver.Config;
import com.git.cs309.mmoserver.entity.characters.user.User;

public final class Party {
	private final Set<User> partyMembers = new HashSet<>();

	public boolean partyFull() {
		return partyMembers.size() >= Config.MAX_PARTY_MEMBERS;
	}

	protected void addUserToParty(final User user) {
		assert !partyFull();
		partyMembers.add(user);
	}
	
	public Collection<User> getPartyMembers() {
		return partyMembers;
	}
}
