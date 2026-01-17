import java.util.Scanner;
import java.util.ArrayList;

public class Atom {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String logo = 
        "          :::        :::::::::::    ::::::::          :::   ::: \n" +
        "       :+: :+:          :+:       :+:    :+:        :+:+: :+:+: \n" +
        "     +:+   +:+         +:+       +:+    +:+       +:+ +:+:+ +:+ \n" +
        "   +#++:++#++:        +#+       +#+    +:+       +#+  +:+  +#+  \n" +
        "  +#+     +#+        +#+       +#+    +#+       +#+       +#+   \n" +
        " #+#     #+# #+#    #+# #+#   #+#    #+# #+#   #+#       #+# #+#\n" +
        "###     ### ###    ### ###    ########  ###   ###       ###  ###\n";
        System.out.println(logo);
        System.out.println("Hello, I am an Assistive Task Organisation Manager, or A.T.O.M.");
        System.out.println("How can I help you?\n");
        ArrayList<String> tasks = new ArrayList<>();
        while(true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();
            if(userInput.equals("bye")) {
                System.out.println("Goodbye! Exiting...");
                break;
            }
            if(userInput.equals("list")) {
                for(int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                continue;
            }
            tasks.add(userInput);
            System.out.println("added: " + userInput);
        }
    }
}
