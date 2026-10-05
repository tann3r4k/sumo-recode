package com.elmakers.mine.bukkit.api.magic;

import com.elmakers.mine.bukkit.api.wand.Wand;

public interface Mage {
	void deactivate();

	Wand checkWand();

	Wand getOffhandWand();
}
