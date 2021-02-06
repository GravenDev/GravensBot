package net.feedthemadness.glib.command;

import net.feedthemadness.glib.command.component.CommandLabel;
import net.feedthemadness.glib.command.component.CommandPrefix;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public class Command extends ACommandElement {

//	private Optional<String> name = Optional.empty();

	private CommandPrefix prefix = new CommandPrefix();
	private CommandLabel label = new CommandLabel();

	public Command() {
	}

//	public Command(String name) {
//		this.name = Optional.ofNullable(name);
//	}
//
//	public String getName() {
//		return name.orElse("");
//	}
//
//	public boolean hasName() {
//		return name.isPresent();
//	}
//
//	public Command setName(String name) {
//		this.name = Optional.ofNullable(name);
//		return this;
//	}

	public CommandPrefix getPrefix() {
		return prefix;
	}

	public Command setPrefix(String prefix) {
		this.prefix = new CommandPrefix(prefix);
		return this;
	}

	public Command setPrefix(CommandPrefix prefix) {
		this.prefix = prefix;
		return this;
	}

	public CommandLabel getLabel() {
		return label;
	}

	public Command setLabel(String label) {
		this.label.setLabel(label);
		return this;
	}
	
	public Command addAliases(String...aliases) {
		this.label.addAliases(aliases);
		return this;
	}
	
	public Command setLabelAndAliases(String label, String...aliases) {
		this.label = new CommandLabel(label, aliases);
		return this;
	}

	public Command setLabel(CommandLabel label) {
		this.label = label;
		return this;
	}
	
	@Override
	public Command addSubElement(ACommandElement subElement) {
		super.addSubElement(subElement);
		return this;
	}
	
	@Override
	public Command addExecutor(ICommandExecutor listener, String id) {
		super.addExecutor(listener, id);
		return this;
	}
	
	public void dispatch(CommandContext context) {
		if(!checkDispatch(context, 0)) return;
		dispatch(context, 0);
	}
	
	@Override
	public boolean checkDispatch(CommandContext context, int depth) {
		if(!prefix.compare(context)) return false;
		if(!label.compare(context, prefix.getPrefix().length())) return false;
		return true;
	}
	
}
