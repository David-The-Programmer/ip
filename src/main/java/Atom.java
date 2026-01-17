import java.util.Scanner;

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
        while(true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();
            if(userInput.equals("bye")) {
                System.out.println("Goodbye! Exiting...");
                break;
            }
            System.out.println(userInput);
        }
    }
}
