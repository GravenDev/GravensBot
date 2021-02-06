package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.argument.ISubArgumentType;
import net.feedthemadness.glib.command.sub.argument.SubArgumentTypeString;

public class SubArgument extends ACommandElement {
	
	protected ISubArgumentType type = new SubArgumentTypeString();
	
	public SubArgument() {}
	
	@Override
	public SubArgument addSubElement(ACommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public SubArgument addExecutor(ICommandExecutor listener, String id) {
		super.addExecutor(listener, id);
		return this;
	}
	
	public ISubArgumentType getType() {
		return type;
	}
	
	public SubArgument setType(ISubArgumentType type) {
		this.type = type;
		return this;
	}
	
	@Override
	public boolean checkDispatch(CommandContext context, int depth) {
		
		if(!type.validate(context.getParsableArg(depth))) {
			return false;
		}
		
		context.setArg(depth - 1, type.parse(context.getParsableArg(depth)));
		
		return true;
	}
	
}
