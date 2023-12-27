package controller.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.Message;
import model.User;

public class Helpers {

    public static String convertArrIntToStringComma(Integer[] arr) {

        return Arrays.stream(arr)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    public static String usersToJson(List<User> users) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < users.size(); i++) {
            jsonBuilder.append(users.get(i).toString());
            if (i < users.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    public static String messagesToJson(List<Message> messages) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < messages.size(); i++) {
            jsonBuilder.append(messages.get(i).toString());
            if (i < messages.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
