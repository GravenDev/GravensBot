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

import java.time.Instant;

public class MongoBasicSanction {


    private final int sanctionId;
    private final Instant createdAt, updatedAt;
    private final SanctionType sanctionType;
    private final String reason, sanctionMessage;
    private final long applier, target;

    protected MongoBasicSanction(
        int sanctionId,
        SanctionType type,
        String reason,
        long applier,
        long target,
        String sanctionMessage,
        Instant createdAt,
        Instant updatedAt
    ) {
        this.sanctionId = sanctionId;
        this.sanctionType = type;
        this.reason = reason;
        this.applier = applier;
        this.sanctionMessage = sanctionMessage;
        this.target = target;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MongoBasicSanction fromBasicSanction(BasicSanction sanction) {
        return new MongoBasicSanction(
            sanction.getId(),
            sanction.getSanctionType(),
            sanction.getReason(),
            sanction.getApplier().getId(),
            sanction.getTarget().getId(),
            sanction.getSanctionMessage().getLink().toString(),
            sanction.getCreatedAt(),
            sanction.getUpdatedAt()
        );
    }

    public final int getSanctionId() {
        return sanctionId;
    }

    public final SanctionType getSanctionType() {
        return sanctionType;
    }

    public final String getReason() {
        return reason;
    }

    public final long getApplier() {
        return applier;
    }

    public final long getTarget() {
        return target;
    }

    public final String getSanctionMessage() {
        return sanctionMessage;
    }

    public final Instant getCreatedAt() {
        return createdAt;
    }

    public final Instant getUpdatedAt() {
        return updatedAt;
    }
}
