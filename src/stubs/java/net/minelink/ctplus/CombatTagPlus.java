package net.minelink.ctplus;

import java.util.UUID;

public class CombatTagPlus {
	public TagManager getTagManager() {
		return new TagManager();
	}

	public static class TagManager {
		public Object getTag(UUID id) {
			return null;
		}

		public void untag(UUID id) {
		}
	}
}
