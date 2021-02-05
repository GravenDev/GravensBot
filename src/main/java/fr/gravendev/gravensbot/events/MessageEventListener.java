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

import fr.gravendev.gravensbot.Main;
import fr.gravendev.gravensbot.commands.misc.PingCommand;
import fr.gravendev.gravensbot.events.commands.CommandRegistry;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;

public class MessageEventListener implements MessageCreateListener {

    private final CommandRegistry registry;

    public MessageEventListener() {
        registry = new CommandRegistry();
        setup();
    }

    private void setup() {
        registry.addCommand(new PingCommand());
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
        if (!event.getMessageContent().trim().startsWith(Main.PREFIX)) return;

        // Parse
        String[] args = event.getMessageContent().trim().substring(Main.PREFIX.length()).split(" ");
        String cmdName = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        String[] finalArgs = args;
        registry.getCommandByAlias(cmdName)
            .thenAccept(
                presumedCommand
                    -> presumedCommand.ifPresent(command
                    -> command.run(event, finalArgs)
                )
            );
    }

}
