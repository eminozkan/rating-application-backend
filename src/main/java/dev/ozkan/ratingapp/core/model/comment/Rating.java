package dev.ozkan.ratingapp.core.model.comment;

public enum Rating {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public static Rating getValue(int value){
        return switch (value){
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            default -> null;
        };
    }

    public static int value(Rating rating) {
        return switch (rating) {
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
        };
    }
}
