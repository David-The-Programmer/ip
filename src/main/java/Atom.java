import java.util.Scanner;
import java.util.ArrayList;

public class Atom {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String logo = "          :::        :::::::::::    ::::::::          :::   ::: \n" +
                "       :+: :+:          :+:       :+:    :+:        :+:+: :+:+: \n" +
                "     +:+   +:+         +:+       +:+    +:+       +:+ +:+:+ +:+ \n" +
                "   +#++:++#++:        +#+       +#+    +:+       +#+  +:+  +#+  \n" +
                "  +#+     +#+        +#+       +#+    +#+       +#+       +#+   \n" +
                " #+#     #+# #+#    #+# #+#   #+#    #+# #+#   #+#       #+# #+#\n" +
                "###     ### ###    ### ###    ########  ###   ###       ###  ###\n";
        System.out.println(logo);
        System.out.println("Hello, I am an Assistive Task Organisation Manager, or A.T.O.M.");
        System.out.println("How can I help you?");
        ArrayList<Task> tasks = new ArrayList<>();
        while (true) {
            try {
                System.out.print("\n> ");
                String userInput = scanner.nextLine();
                System.out.println();
                if (userInput.equals("bye")) {
                    System.out.println("Goodbye! Exiting...");
                    break;
                }
                if (userInput.equals("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    continue;
                }
                String[] subcommands = userInput.split(" ", 2);
                if (subcommands[0].equals("mark")) {
                    if(subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'mark' command:\n\n" +
                        "   mark <number>\n\n" +
                        "Please try again.";
                        throw new InvalidMarkCommandException("'mark' command is missing a number", null, remedy);
                    }
                    int taskId = -1;
                    try {
                        taskId = Integer.parseInt(subcommands[1]);
                    } catch(Exception exception) {
                        String remedy = "Please follow the following format for the 'mark' command:\n\n" +
                        "   mark <number>\n\n" +
                        "Please try again.";
                        throw new InvalidMarkCommandException("'" + subcommands[1] + "'" + " is not a number", exception, remedy);
                    }
                    if (taskId - 1 < 0 || taskId - 1 >= tasks.size()) {
                        String remedy = "If you are unsure of the number of the task you want to mark as complete,\n" + 
                        "type 'list' to show all tasks and corresponding task number.\n" +
                        "Please try again.";
                        throw new InvalidMarkCommandException("task " + taskId + " does not exist.", null, remedy);
                    } else {
                        tasks.get(taskId - 1).markAsComplete();
                        System.out.println("Great Job! This task is marked as complete:");
                        System.out.println(tasks.get(taskId - 1));
                    }
                    continue;
                }
                if (subcommands[0].equals("unmark")) {
                    if(subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'unmark' command:\n\n" +
                        "   unmark <number>\n\n" +
                        "Please try again.";
                        throw new InvalidUnmarkCommandException("'unmark' command is missing a number", null, remedy);
                    }
                    int taskId = -1;
                    try {
                        taskId = Integer.parseInt(subcommands[1]);
                    } catch(Exception exception) {
                        String remedy = "Please follow the following format for the 'unmark' command:\n\n" +
                        "   unmark <number>\n\n" +
                        "Please try again.";
                        throw new InvalidUnmarkCommandException("'" + subcommands[1] + "'" + " is not a number", exception, remedy);
                    }
                    if (taskId - 1 < 0 || taskId - 1 >= tasks.size()) {
                        String remedy = "If you are unsure of the number of the task you want to mark as incomplete,\n" + 
                        "type 'list' to show all tasks and corresponding task number.\n" +
                        "Please try again.";
                        throw new InvalidUnmarkCommandException("task " + taskId + " does not exist.", null, remedy);
                    } else {
                        tasks.get(taskId - 1).markAsComplete();
                        System.out.println("Alright, This task is marked as incomplete:");
                        System.out.println(tasks.get(taskId - 1));
                    }
                    continue;
                }
                if (subcommands[0].equals("todo")) {
                    tasks.add(new ToDo(subcommands[1]));
                    System.out.println("Noted. The following task has been added: ");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("So, in total, you have " + tasks.size() + " task(s) remaining");
                    continue;
                }
                if (subcommands[0].equals("deadline")) {
                    String[] details = subcommands[1].split("/by");
                    tasks.add(new Deadline(details[0], details[1]));
                    System.out.println("Noted. The following task has been added: ");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("So, in total, you have " + tasks.size() + " task(s) remaining");
                    continue;
                }
                if (subcommands[0].equals("event")) {
                    int idxOfFrom = subcommands[1].indexOf("/from");
                    int idxOfTo = subcommands[1].indexOf("/to");
                    String description = subcommands[1].substring(0, idxOfFrom);
                    String fromDateTime = subcommands[1].substring(idxOfFrom + 5, idxOfTo).trim();
                    String toDateTime = subcommands[1].substring(idxOfTo + 3).trim();
                    tasks.add(new Event(description, fromDateTime, toDateTime));
                    System.out.println("Noted. The following task has been added: ");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("So, in total, you have " + tasks.size() + " task(s) remaining");
                    continue;
                }
                String remedy = "The following are the only valid commands:\n\n" + 
                "   todo <description>\n\n" +
                "   deadline <description> /by <date and/or time of deadline>\n\n" +
                "   event <description> /from <date and/or time that event starts> /to <date and/or time that event ends>\n\n" +
                "   list\n\n" +
                "   mark <task number shown after entering the list command>\n\n" +
                "   unmark <task number shown after entering the list command>\n\n" +
                "   bye\n\n" +
                "Please try again.";
                throw new UnknownCommandException("Unknown command '" + subcommands[0] + "' is given.", null, remedy);

            } catch (AtomException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            }
        }
    }
}
