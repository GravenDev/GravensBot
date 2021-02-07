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
import org.javacord.api.entity.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

public class EndingSanction extends BasicSanction {

    private Instant endedAt;

    public EndingSanction(
        int id,
        SanctionType type,
        String reason,
        User applier,
        User target,
        Message sanctionMsg,
        Duration duration
    ) {
        super(id, type, reason, applier, target, sanctionMsg);
        this.endedAt = Instant.now().plus(duration);
    }


    protected EndingSanction(
        int id,
        SanctionType type,
        String reason,
        User applier,
        User target,
        Message sanctionMsg,
        Instant createdAt,
        Instant updatedAt,
        Instant endedAt
    ) {
        super(id, type, reason, applier, target, sanctionMsg, createdAt, updatedAt);
        this.endedAt = endedAt;
    }

    public static EndingSanction fromMongoEndingSanction(DiscordApi api, MongoEndingSanction sanction) {
        AtomicReference<User> applier = new AtomicReference<>();
        api.getUserById(sanction.getApplier()).thenAccept(applier::set);
        AtomicReference<User> target = new AtomicReference<>();
        api.getUserById(sanction.getTarget()).thenAccept(applier::set);
        AtomicReference<Message> message = new AtomicReference<>(null);
        api.getMessageByLink(sanction.getSanctionMessage()).ifPresent(potMsg -> potMsg.thenAccept(message::set));

        return new EndingSanction(
            sanction.getSanctionId(),
            sanction.getSanctionType(),
            sanction.getReason(),
            applier.get(),
            target.get(),
            message.get(),
            sanction.getCreatedAt(),
            sanction.getUpdatedAt(),
            sanction.getEndingAt()
        );
    }

    public final Instant getEndedAt() {
        return endedAt;
    }

    public final void setEndedAt(Instant endedAt) {
        this.endedAt = endedAt;
        update();
    }
}
