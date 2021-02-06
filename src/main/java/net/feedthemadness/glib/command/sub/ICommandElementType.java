package net.feedthemadness.glib.command.sub;

public interface ICommandElementType {
	
	boolean validate(String parsableArgument);
	
	Object parse(String parsableArgument);
	
}
