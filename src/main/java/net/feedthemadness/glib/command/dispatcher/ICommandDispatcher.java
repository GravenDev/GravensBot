package net.feedthemadness.glib.command.dispatcher;

import net.feedthemadness.glib.command.Command;

public interface ICommandDispatcher {
	
	ICommandDispatcher addCommand(Command command);
	
	void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Object... dispatchContext);
	
}
