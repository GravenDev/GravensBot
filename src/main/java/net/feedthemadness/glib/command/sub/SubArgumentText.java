package net.feedthemadness.glib.command.sub;

import net.feedthemadness.glib.command.ACommandElement;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public class SubArgumentText extends ACommandElement {

    public SubArgumentText() {
    }

    @Override
    public SubArgumentText addSubElement(ACommandElement subElement) {
        System.out.println("WARNING : Could not add SubElement in SubArgumentText");
        //TODO proper error
        return this;
    }

    @Override
    public SubArgumentText addExecutor(ICommandExecutor executor, String id) {
        super.addExecutor(executor, id);
        return this;
    }

    @Override
    protected void dispatch(CommandContext context, int depth) {
        depth = context.parsableArgsSize() - 1;
        super.dispatch(context, depth);
    }

    @Override
    public boolean checkDispatch(CommandContext context, int depth) {

        context.setArg(depth - 1, parseArgs(context, depth));

        return true;
    }

    private String parseArgs(CommandContext context, int depth) {
        StringBuilder builder = new StringBuilder();

        for (int i = depth; i < context.parsableArgsSize(); i++) {
            builder.append(context.getParsableArg(i));
            if (i < context.parsableArgsSize() - 1) builder.append(' ');
        }

        return builder.toString();
    }

}
