package com.azakamu.attendencemanager.domain.values;

/**
 * Vacation is an immutable object that stores a time frame and a reason to provide context.
 *
 * @param timeframe the time frame in which the vacation takes place
 * @param reason    the reason for taking a vacation
 */
public record Vacation(Timeframe timeframe, String reason) {
}
