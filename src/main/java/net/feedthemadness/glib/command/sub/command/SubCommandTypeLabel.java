package net.feedthemadness.glib.command.sub.command;

import net.feedthemadness.glib.command.component.CommandLabel;

public class SubCommandTypeLabel extends CommandLabel implements ISubCommandType {
	
	public SubCommandTypeLabel() {}
	
	public SubCommandTypeLabel(String label) {
		super(label);
	}
	
	public SubCommandTypeLabel(String label, String... aliases) {
		super(label, aliases);
	}
	
	@Override
	public boolean validate(String parsableArgument) {
		return compare(parsableArgument);
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument.toLowerCase();
	}
	
}
