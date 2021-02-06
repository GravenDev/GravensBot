package net.feedthemadness.glib.command.dispatcher;

import java.util.Arrays;

import net.feedthemadness.glib.command.Command;

public class CommandDispatcher implements ICommandDispatcher {
	
	private Command[] commands = new Command[0];
	
	public CommandDispatcher() {}
	
	@Override
	public CommandDispatcher addCommand(Command command) {
		Command[] commands = Arrays.copyOf(this.commands, this.commands.length + 1);
		commands[commands.length - 1] = command;
		
		this.commands = commands;
		return this;
	}
	
	@Override
	public void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Object... dispatchContext) {
		
		for (int i = 0; i < commands.length; i++) {
			Command command = commands[i];
			CommandContext context = new CommandContext(dispatcher, command, dispatchContext, parsableCommand);
			
			command.dispatch(context);
		}
	}
	
}
