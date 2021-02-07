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

public class MongoEndingSanction extends MongoBasicSanction {

    private final Instant endingAt;

    protected MongoEndingSanction(
        int sanctionId,
        String type,
        String reason,
        long applier,
        long target,
        String sanctionMessage,
        Instant createdAt,
        Instant updatedAt,
        Instant endingAt
    ) {
        super(sanctionId, type, reason, applier, target, sanctionMessage, createdAt, updatedAt);
        this.endingAt = endingAt;
    }

    public static MongoBasicSanction fromEndingSanction(EndingSanction sanction) {
        return new MongoEndingSanction(
            sanction.getId(),
            sanction.getSanctionType().name(),
            sanction.getReason(),
            sanction.getApplier().getId(),
            sanction.getTarget().getId(),
            sanction.getSanctionMessage().getLink().toString(),
            sanction.getCreatedAt(),
            sanction.getUpdatedAt(),
            sanction.getEndedAt()
        );
    }

    public Instant getEndingAt() {
        return endingAt;
    }
}
