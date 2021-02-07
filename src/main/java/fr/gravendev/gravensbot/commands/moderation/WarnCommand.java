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

package fr.gravendev.gravensbot.commands.moderation;

import fr.gravendev.gravensbot.utils.database.repositories.BasicSanctionRepository;
import fr.gravendev.gravensbot.utils.database.repositories.RepositoryManager;
import fr.gravendev.gravensbot.utils.sanctions.BasicSanction;
import fr.gravendev.gravensbot.utils.sanctions.MongoBasicSanction;
import fr.gravendev.gravensbot.utils.sanctions.SanctionType;
import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.ICommandExecutor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class WarnCommand implements ICommandExecutor {


    private final RepositoryManager repos;

    public WarnCommand(RepositoryManager repos) {
        this.repos = repos;
    }

    @CommandListener("warn")
    public void run(
        CommandContext context,
        MessageCreateEvent event,
        User mention,
        String reason
    ) {

        BasicSanctionRepository repository = repos.getBasicSanctionRepository();

        BasicSanction sanction = new BasicSanction(
            repository.getLastSanctionId() + 1,
            SanctionType.WARN,
            reason,
            event.getMessageAuthor().asUser().get(),
            mention,
            event.getMessage()
        );
        repository.save(MongoBasicSanction.fromBasicSanction(sanction));

        event.getChannel().sendMessage("L'utilisateur " + mention.getMentionTag() + " a été averti pour `" + reason + "` (id : " + sanction.getId() + ")");
        mention.sendMessage("Vous avez été averti pour `" + reason + "`");

    }

}
