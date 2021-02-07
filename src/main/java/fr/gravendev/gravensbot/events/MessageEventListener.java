/*
 *
 * MIT License
 *
 * Copyright (c) 2021 Graven - Développement
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.gravendev.gravensbot.events;

import fr.gravendev.gravensbot.commands.misc.PingCommand;
import fr.gravendev.gravensbot.commands.moderation.WarnCommand;
import fr.gravendev.gravensbot.events.commands.types.UserMentionSubArgumentType;
import fr.gravendev.gravensbot.utils.database.DatabaseConnection;
import fr.gravendev.gravensbot.utils.database.repositories.RepositoryManager;
import net.feedthemadness.glib.command.Command;
import net.feedthemadness.glib.command.dispatcher.CommandDispatcher;
import net.feedthemadness.glib.command.dispatcher.ICommandDispatcher;
import net.feedthemadness.glib.command.sub.SubArgument;
import net.feedthemadness.glib.command.sub.SubArgumentText;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import static fr.gravendev.gravensbot.Main.PREFIX;

public class MessageEventListener implements MessageCreateListener, ICommandDispatcher {

    private final DatabaseConnection connection;
    private final RepositoryManager repos;
    private final CommandDispatcher dispatcher;

    public MessageEventListener(DiscordApi api, DatabaseConnection connection) {
        this.dispatcher = new CommandDispatcher();
        this.connection = connection;
        this.repos = new RepositoryManager(connection);
        setup(api);
    }

    private void setup(DiscordApi api) {
        addCommand(new Command()
            .setPrefix(PREFIX)
            .setLabelAndAliases("ping", "pong", "latency")
            .addExecutor(new PingCommand(), "ping")
        );
        addCommand(new Command()
            .setPrefix(PREFIX)
            .setLabelAndAliases("warn")
            .addSubElement(
                new SubArgument()
                    .setType(new UserMentionSubArgumentType(api))
                    .addSubElement(new SubArgumentText()
                        .addExecutor(new WarnCommand(repos), "warn")
                    )
            )
        );
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        // Ignore bot messages
        if (event.getMessageAuthor().isBotUser()) return;

        // Ignore DM messages
        if (!event.isServerMessage()) {
            event.getChannel().sendMessage(":x: Les messages privés ne sont pas pris en compte par ce bot !");
        }

        // Check if command
        if (!event.getMessageContent().trim().startsWith(PREFIX)) return;

        // Dispatch command
        dispatch(this, event.getMessageContent(), event);
    }

    @Override
    public ICommandDispatcher addCommand(Command command) {
        return this.dispatcher.addCommand(command);
    }

    @Override
    public void dispatch(ICommandDispatcher dispatcher, String parsableCommand, Object... dispatchContext) {
        this.dispatcher.dispatch(dispatcher, parsableCommand, dispatchContext);
    }
}
