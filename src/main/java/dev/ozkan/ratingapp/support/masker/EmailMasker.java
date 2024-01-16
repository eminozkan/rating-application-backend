package dev.ozkan.ratingapp.support.masker;

public class EmailMasker {
    public static String maskEmail(String email) {
        StringBuilder maskedEmailBuilder = new StringBuilder();
        for (int i = 0; i < 3;i++){
            maskedEmailBuilder.append(email.charAt(i));
        }

        for (int i = 0; i < email.indexOf('@') - 3;i++){
            maskedEmailBuilder.append("*");
        }

        maskedEmailBuilder.append(email.substring(email.indexOf('@')));
        return maskedEmailBuilder.toString();
    }
}
