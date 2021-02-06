package net.feedthemadness.glib.command.executor;

import java.util.Arrays;

import net.feedthemadness.glib.command.Main;
import net.feedthemadness.glib.command.dispatcher.CommandContext;

public class CommandExecutor {
	
	private final String id;
	private ExecutorReference[] executorReferences = new ExecutorReference[0];
	
	public CommandExecutor(String id) {
		this.id = id;
	}
	
	public CommandExecutor addExecutor(ExecutorReference executorReference) {
		ExecutorReference[] executors = Arrays.copyOf(this.executorReferences, this.executorReferences.length + 1);
		executors[executors.length - 1] = executorReference;

		this.executorReferences = executors;
		return this;
	}
	
	public void dispatch(CommandContext context, int depth) {
		
		Object[] args = new Object[1 + context.dispatchContextSize() + context.argsSize()];
		
		args[0] = context.clone();
		for(int i = 0 ; i < context.dispatchContextSize() ; i++) {
			args[i + 1] = context.getDispatchContext(i);
		}
		for(int i = 0 ; i < context.argsSize() ; i++) {
			args[i + context.dispatchContextSize() + 1] = context.getArg(i);
		}
		
		for(int i = 0 ; i < executorReferences.length ; i++) {
			ExecutorReference executor = executorReferences[i];
			
			if(!id.equals(executor.getId())) continue;
			
			if(!executor.validateType(args)) {
				Main.getTerminal().warning("Mismatch argument type during dispatch");
				//TODO proper error
				continue;
			}
			
			executor.dispatch(args);
		}
	}
	
}
