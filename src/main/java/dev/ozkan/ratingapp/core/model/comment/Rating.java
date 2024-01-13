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
}
