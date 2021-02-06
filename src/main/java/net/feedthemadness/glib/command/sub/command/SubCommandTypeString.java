package net.feedthemadness.glib.command.sub.command;

public class SubCommandTypeString extends ASubCommandType {
	
	@Override
	public boolean validate(String parsableArgument) {
		return true;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument;
	}
	
}
