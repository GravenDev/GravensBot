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

package fr.gravendev.gravensbot.events.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CommandRegistry {

    private Command[] commands;

    public CommandRegistry() {
        this.commands = new Command[0];
    }

    public Command[] getCommands() {
        return commands;
    }

    public List<Command> getCommandList() {
        return Arrays.asList(commands);
    }

    public Command addCommand(Command command) {
        if (command.getAliases() == null || command.getAliases().getAliases().size() == 0) {
            throw new RuntimeException("The command " + command.getClass().getSimpleName() + " does not have any alias");
        }
        commands = Arrays.copyOf(commands, commands.length + 1);
        commands[commands.length - 1] = command;
        return command;
    }

    public CompletableFuture<Optional<Command>> getCommandByAlias(String alias) {
        CompletableFuture<Optional<Command>> future = new CompletableFuture<>();
        Executors.newCachedThreadPool().execute(() -> {
            for (Command command : commands) {
                if (command.getAliases().getAliases().contains(alias)) {
                    future.complete(Optional.of(command));
                }
            }
            future.complete(Optional.empty());
        });
        return future;
    }

}
