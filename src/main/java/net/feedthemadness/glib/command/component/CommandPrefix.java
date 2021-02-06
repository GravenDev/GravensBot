package net.feedthemadness.glib.command.component;

import net.feedthemadness.glib.command.dispatcher.CommandContext;

public class CommandPrefix {

	private String prefix = "";

	public CommandPrefix() {
	}

	public CommandPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public CommandPrefix setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public boolean compare(CommandContext context) {
		return compare(context.getParsableArg(0).substring(0, prefix.length()));
	}
	
	public boolean compare(String prefix) {
		return prefix.equals(prefix);
	}
	
}
