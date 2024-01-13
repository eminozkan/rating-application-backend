package dev.ozkan.ratingapp.core.model.comment;

public enum UsageTime {
    LESS_THAN_THREE_MONTHS,
    THREE_TO_SIX_MONTHS,
    SIX_TO_TWELVE_MONTHS,
    OVER_TWELVE_MONTHS;


    public static UsageTime getUsageTimeByValue(String value) {
        return switch (value) {
            case "0-3" -> LESS_THAN_THREE_MONTHS;
            case "3-6" -> THREE_TO_SIX_MONTHS;
            case "6-12" -> SIX_TO_TWELVE_MONTHS;
            case "12+" -> OVER_TWELVE_MONTHS;
            default -> null;
        };
    }
}
