package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import net.feedthemadness.glib.command.sub.command.ISubCommandType;
import net.feedthemadness.glib.command.sub.command.SubCommandTypeLabel;
import net.feedthemadness.glib.command.sub.command.SubCommandTypeString;

public class SubCommand extends ACommandElement {
	
	protected ISubCommandType type = new SubCommandTypeString();
	
	public SubCommand() {}
	
	public SubCommand(String label) {
		this.type = new SubCommandTypeLabel(label);
	}
	
	public SubCommand(String label, String... aliases) {
		this.type = new SubCommandTypeLabel(label, aliases);
	}
	
	@Override
	public SubCommand addSubElement(ACommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public SubCommand addExecutor(ICommandExecutor executor, String id) {
		super.addExecutor(executor, id);
		return this;
	}
	
	public ISubCommandType getType() {
		return type;
	}
	
	public SubCommand setType(ISubCommandType type) {
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
