package me.rojo8399.placeholderapi.configs;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Messages {
	
	@ConfigSerializable
	public static class Message {
		
		@Setting
		public String value;
		
		public Message() {
		}
		
		public Message(String s) {
			this.value = s;
		}
		
		public Text t(Object... args) {
			return Messages.t(value, args);
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	private static Message of(String v) {
		return new Message(v);
	}

	public static final TypeToken<Messages> type = TypeToken.of(Messages.class);

	private static Messages inst;

	public static void init(Messages inst) {
		Messages.inst = inst;
	}

	public static Messages get() {
		return inst == null ? new Messages() : inst;
	}

	public static Text t(String m, Object... args) {
		return TextSerializers.FORMATTING_CODE.deserialize(String.format(m, args));
	}

	@Setting
	public Misc misc = new Misc();

	@ConfigSerializable
	public static class Misc {
		@Setting("no-permission")
		public Message noPerm = of("&cYou are not allowed to do that!");
		@Setting("no-value")
		public Message noValue = of("&cNo value present.");
		@Setting
		public Message by = of("by");
		@Setting
		public Message version = of("version");
		@Setting
		public Direction directions = new Direction();

		@ConfigSerializable
		public static class Direction {
			@Setting
			public Message south = of("South");
			@Setting
			public Message southwest = of("Southwest");
			@Setting
			public Message west = of("West");
			@Setting
			public Message northwest = of("Northwest");
			@Setting
			public Message north = of("North");
			@Setting
			public Message northeast = of("Northeast");
			@Setting
			public Message east = of("East");
			@Setting
			public Message southeast = of("Southeast");
		}
	}

	@Setting
	public Placeholders placeholder = new Placeholders();

	@ConfigSerializable
	public static class Placeholders {
		@Setting("javascript-description")
		public Message jsdesc = of("Execute JavaScripts.");
		@Setting("currency-description")
		public Message curdesc = of("View information about the server's economy.");
		@Setting("time-description")
		public Message timedesc = of("View the current date and time.");
		@Setting("player-description")
		public Message playerdesc = of("View information about a player.");
		@Setting("rank-description")
		public Message rankdesc = of("View information about a player's rank.");
		@Setting("server-description")
		public Message serverdesc = of("View information about the server.");
		@Setting("sound-description")
		public Message sounddesc = of("Play sounds to players.");
		@Setting("statistics-description")
		public Message statdesc = of("View a player's statistics.");
		@Setting("click-to-reload")
		public Message clickReload = of("&bClick to reload:");
		@Setting("reload-button-hover")
		public Message reloadButtonHover = of("&bClick to reload this placeholder!");
		@Setting("reload-button")
		public Message reloadButton = of("&c[RELOAD]");
		@Setting("supported-placeholders")
		public Message supportedPlaceholders = of("&6Supported placeholders:");
		@Setting("parse-button-hover")
		public Message parseButtonHover = of("&bClick to parse this placeholder for you!");
		@Setting("info-button-hover")
		public Message infoButtonHover = of("&bClick to get more info!");
		@Setting("available-placeholders")
		public Message availablePlaceholders = of("&aAvailable placeholders:");
		@Setting("must-specify")
		public Message mustSpecify = of("&cYou must specify a placeholder!");
		@Setting("invalid-placeholder")
		public Message invalidPlaceholder = of("&cThat is not a valid placeholder!");
		@Setting("reload-success")
		public Message reloadSuccess = of("&aPlaceholder reloaded successfully!");
		@Setting("reload-failed")
		public Message reloadFailed = of("&cPlaceholder failed to reload!");
	}

	@Setting
	public Plugins plugin = new Plugins();

	@ConfigSerializable
	public static class Plugins {
		@Setting("service-unavailable")
		public Message serviceUnavailable = of("&cPlaceholders are unavailable!");
		@Setting("reload-success")
		public Message reloadSuccess = of("&aPlaceholderAPI reloaded successfully!");
		@Setting("reload-failed")
		public Message reloadFailed = of("&cPlaceholderAPI failed to reload!");
	}

}
