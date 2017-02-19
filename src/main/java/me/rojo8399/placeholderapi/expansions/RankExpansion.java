package me.rojo8399.placeholderapi.expansions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import me.rojo8399.placeholderapi.PlaceholderAPIPlugin;

public class RankExpansion implements Expansion {

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.rojo8399.placeholderapi.expansions.Expansion#canRegister()
	 */
	@Override
	public boolean canRegister() {
		return PlaceholderAPIPlugin.getInstance().getConfig().expansions.rank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.rojo8399.placeholderapi.expansions.Expansion#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return "rank";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.rojo8399.placeholderapi.expansions.Expansion#getAuthor()
	 */
	@Override
	public String getAuthor() {
		return "Wundero";
	}

	@Override
	public String getDescription() {
		return "Rank information for a player.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.rojo8399.placeholderapi.expansions.Expansion#getVersion()
	 */
	@Override
	public String getVersion() {
		return "1.0";
	}

	/*
	 * Get the bottommost parent in the inheritance tree.
	 */
	private static Subject getParentGroup(Subject subject) {
		List<Subject> parents = subject.getParents();
		return parents.stream().filter(parent -> {
			for (Subject s : parents) {
				if (s.equals(parent) || s.getIdentifier().equals(parent.getIdentifier())) {
					continue;
				}
				if (parent.isChildOf(s)) {
					continue;
				}
				return false;
			}
			return true;
		}).findFirst().orElse(subject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.rojo8399.placeholderapi.expansions.Expansion#onPlaceholderRequest(org.
	 * spongepowered.api.entity.living.player.Player, java.lang.String)
	 */
	@Override
	public Text onPlaceholderRequest(Player player, Optional<String> token) {
		if (!token.isPresent()) {
			return Text.of(getParentGroup(player).getIdentifier());
		}
		Subject rank = getParentGroup(player);
		String t = token.get();
		switch (t) {
		case "prefix":
		case "suffix":
			return TextSerializers.FORMATTING_CODE.deserialize(rank.getOption(t).orElse(""));
		case "name":
			return Text.of(rank.getIdentifier());
		}
		if (t.contains("_") && t.indexOf("_") < t.length()) {
			if (t.startsWith("option")) {
				String opt = t.substring(t.indexOf("_") + 1);
				return TextSerializers.FORMATTING_CODE.deserialize(rank.getOption(opt).orElse(""));
			}
			// this also covers "permission_..."
			// return whether the rank has a permission
			if (t.startsWith("perm")) {
				String perm = t.substring(t.indexOf("_") + 1);
				return Text.of(rank.getPermissionValue(rank.getActiveContexts(), perm).toString());
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.rojo8399.placeholderapi.expansions.Expansion#getSupportedTokens()
	 */
	@Override
	public List<String> getSupportedTokens() {
		return Arrays.asList(null, "prefix", "suffix", "name", "option_[option]", "permission_[permission]");
	}

}
