package net.feedthemadness.glib.command.sub.argument;

public class SubArgumentTypeInt extends ASubArgumentType {
	
	@Override
	public boolean validate(String parsableArgument) {
		try {
			Integer.parseInt(parsableArgument);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return Integer.parseInt(parsableArgument);
	}
	
}
