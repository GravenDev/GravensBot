package net.feedthemadness.glib.command.dispatcher;

import java.util.Arrays;

import net.feedthemadness.glib.command.Command;

public class CommandContext implements Cloneable {
	
	private final ICommandDispatcher dispatcher;
	private final Command command;
	private final Object[] dispatchContext;
	private final String[] parsableArgs;
	private Object[] args;
	
	public CommandContext(ICommandDispatcher dispatcher, Command command, Object[] dispatchContext, String parsableCommand) {
		this.dispatcher = dispatcher;
		this.command = command;
		this.dispatchContext = dispatchContext;
		this.parsableArgs = parsableCommand.split(" ");
		this.args = new Object[0];
	}
	
	protected CommandContext(CommandContext context) {
		this.dispatcher = context.dispatcher;
		this.command = context.command;
		this.dispatchContext = Arrays.copyOf(context.dispatchContext, context.dispatchContext.length);
		this.parsableArgs = Arrays.copyOf(context.parsableArgs, context.parsableArgs.length);
		this.args = Arrays.copyOf(context.args, context.args.length);
	}
	
	public ICommandDispatcher getDispatcher() {
		return dispatcher;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public int dispatchContextSize() {
		return dispatchContext.length;
	}
	
	public Object getDispatchContext(int index) {
		return dispatchContext[index];
	}
	
	public int parsableArgsSize() {
		return parsableArgs.length;
	}
	
	public String getParsableArg(int index) {
		return parsableArgs[index];
	}
	
	public int argsSize() {
		return args.length;
	}
	
	public Object getArg(int index) {
		return args[index];
	}
	
	public void setArg(int index, Object arg) {
		Object[] args = Arrays.copyOf(this.args, index + 1);
		args[index] = arg;
		
		this.args = args;
	}
	
	@Override
	public CommandContext clone() {
		return new CommandContext(this);
	}
	
}
