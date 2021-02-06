package net.feedthemadness.glib.command.sub.argument;

public class SubArgumentTypeString extends ASubArgumentType {
	
	@Override
	public boolean validate(String parsableArgument) {
		try {
			Double.parseDouble(parsableArgument);
		} catch(NumberFormatException e) {
			return true;
		}
		return false;
	}
	
	@Override
	public Object parse(String parsableArgument) {
		return parsableArgument;
	}
	
}
