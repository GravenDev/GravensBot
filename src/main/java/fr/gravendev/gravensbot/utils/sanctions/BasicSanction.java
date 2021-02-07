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

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

import java.time.Instant;

public class BasicSanction {

    private final int id;
    private final Instant createdAt;
    private final Message sanctionMessage;
    private SanctionType sanctionType;
    private String reason;
    private User applier, target;
    private Instant updatedAt;

    public BasicSanction(
        int id,
        SanctionType type,
        String reason,
        User applier,
        User target,
        Message sanctionMessage
    ) {
        this.id = id;
        this.sanctionType = type;
        this.reason = reason;
        this.applier = applier;
        this.sanctionMessage = sanctionMessage;
        this.target = target;
        this.createdAt = this.updatedAt = Instant.now();
    }

    public final int getId() {
        return id;
    }

    public final SanctionType getSanctionType() {
        return sanctionType;
    }

    public final void setSanctionType(SanctionType sanctionType) {
        this.sanctionType = sanctionType;
        update();
    }

    public final String getReason() {
        return reason;
    }

    public final void setReason(String reason) {
        this.reason = reason;
        update();
    }

    public final User getApplier() {
        return applier;
    }

    public final void setApplier(User applier) {
        this.applier = applier;
        update();
    }

    public final User getTarget() {
        return target;
    }

    public final void setTarget(User target) {
        this.target = target;
        update();
    }

    public final Message getSanctionMessage() {
        return sanctionMessage;
    }

    public final Instant getCreatedAt() {
        return createdAt;
    }

    public final Instant getUpdatedAt() {
        return updatedAt;
    }

    protected final void update() {
        this.updatedAt = Instant.now();
    }
}
