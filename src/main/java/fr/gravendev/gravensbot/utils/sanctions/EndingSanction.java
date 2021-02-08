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

package fr.gravendev.gravensbot.utils.sanctions;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class EndingSanction extends BasicSanction {

    private Instant endedAt;

    public EndingSanction(
        int id,
        SanctionType type,
        String reason,
        Server server,
        User applier,
        User target,
        Message sanctionMsg,
        Duration duration
    ) {
        super(id, type, reason, server, applier, target, sanctionMsg);
        this.endedAt = Instant.now().plus(duration);
    }


    protected EndingSanction(
        int id,
        SanctionType type,
        String reason,
        Server server,
        User applier,
        User target,
        Message sanctionMsg,
        Instant createdAt,
        Instant updatedAt,
        Instant endedAt
    ) {
        super(id, type, reason, server, applier, target, sanctionMsg, createdAt, updatedAt);
        this.endedAt = endedAt;
    }

    public static CompletableFuture<EndingSanction> fromMongoEndingSanction(
        DiscordApi api,
        MongoEndingSanction sanction
    ) {
        CompletableFuture<EndingSanction> future = new CompletableFuture<>();
        api
            .getUserById(sanction.getApplier())
            .thenAcceptBoth(api.getUserById(sanction.getTarget()), (applier, target) -> {
                AtomicReference<Server> server = new AtomicReference<>();
                api
                    .getServerById(sanction.getServer())
                    .ifPresent(server::set);

                api
                    .getMessageByLink(sanction.getSanctionMessage())
                    .ifPresent(potMsg -> potMsg.thenAccept(message -> future.complete(new EndingSanction(
                        sanction.getSanctionId(),
                        SanctionType.valueOf(sanction.getSanctionType()),
                        sanction.getReason(),
                        server.get(),
                        applier,
                        target,
                        message,
                        sanction.getCreatedAt(),
                        sanction.getUpdatedAt(),
                        sanction.getEndingAt()
                    ))));
            });
        return future;
    }

    public final Instant getEndedAt() {
        return endedAt;
    }

    public final void setEndedAt(Instant endedAt) {
        this.endedAt = endedAt;
        update();
    }
}
