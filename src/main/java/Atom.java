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
        ArrayList<Task> tasks = new ArrayList<>();
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
            String[] subcommands = userInput.split(" ", 2);
            if(subcommands[0].equals("mark")) {
                int taskId = Integer.parseInt(subcommands[1]);
                if (taskId - 1 < 0 || taskId - 1 >= tasks.size()) {
                    System.out.println("Sorry, task " + taskId + " does not exists, please try again.");
                } else {
                    tasks.get(taskId - 1).markAsComplete();
                    System.out.println("Great Job! This task is marked as complete:");
                    System.out.println(tasks.get(taskId - 1));
                }
                continue;
            }
            if(subcommands[0].equals("unmark")) {
                int taskId = Integer.parseInt(subcommands[1]);
                if (taskId - 1 < 0 || taskId - 1 >= tasks.size()) {
                    System.out.println("Sorry, task " + taskId + " does not exists, please try again.");
                } else {
                    tasks.get(taskId - 1).markAsIncomplete();
                    System.out.println("Alright, this task is marked as incomplete:");
                    System.out.println(tasks.get(taskId - 1));
                }
                continue;
            }
            if(subcommands[0].equals("todo")) {
                tasks.add(new ToDo(subcommands[1]));
                System.out.println("Noted. The following task has been added: ");
                System.out.println(tasks.get(tasks.size() - 1));
                System.out.println("So, in total, you have " + tasks.size() + " task(s) remaining");
                continue;
            }
            if(subcommands[0].equals("deadline")) {
                String[] details = subcommands[1].split(" /by ");
                tasks.add(new Deadline(details[0], details[1]));
                System.out.println("Noted. The following task has been added: ");
                System.out.println(tasks.get(tasks.size() - 1));
                System.out.println("So, in total, you have " + tasks.size() + " task(s) remaining");
                continue;
            }
            if(subcommands[0].equals("event")) {
                int idxOfFrom = subcommands[1].indexOf("/from");
                int idxOfTo = subcommands[1].indexOf("/to");
                String description = subcommands[1].substring(0, idxOfFrom);
                String fromDateTime = subcommands[1].substring(idxOfFrom + 5, idxOfTo).trim();
                String toDateTime = subcommands[1].substring(idxOfTo + 3).trim();
                System.out.println(toDateTime);
                tasks.add(new Event(description, fromDateTime, toDateTime));
                System.out.println("Noted. The following task has been added: ");
                System.out.println(tasks.get(tasks.size() - 1));
                System.out.println("So, in total, you have " + tasks.size() + " task(s) remaining");
                continue;
            }
        }
    }
}
