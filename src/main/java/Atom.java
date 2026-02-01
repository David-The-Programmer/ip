import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Atom {
    public static void main(String[] args) {
        TaskSerialiser taskSerialiser = new TaskSerialiser();
        TaskDeserialiser taskDeserialiser = new TaskDeserialiser();
        Storage storage = Storage.init("./data/atom.txt", taskSerialiser, taskDeserialiser);
        TaskService taskService = new TaskService(storage.readTasks());
        Scanner scanner = new Scanner(System.in);
        String logo = "          :::        :::::::::::    ::::::::          :::   ::: \n"
                + "       :+: :+:          :+:       :+:    :+:        :+:+: :+:+: \n"
                + "     +:+   +:+         +:+       +:+    +:+       +:+ +:+:+ +:+ \n"
                + "   +#++:++#++:        +#+       +#+    +:+       +#+  +:+  +#+  \n"
                + "  +#+     +#+        +#+       +#+    +#+       +#+       +#+   \n"
                + " #+#     #+# #+#    #+# #+#   #+#    #+# #+#   #+#       #+# #+#\n"
                + "###     ### ###    ### ###    ########  ###   ###       ###  ###\n";
        System.out.println(logo);
        System.out.println("Hello, I am an Assistive Task Organisation Manager, or A.T.O.M.");
        System.out.println("How can I help you?");
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
                    int taskSize = taskService.getTasks().size();
                    for (int i = 1; i <= taskSize; i++) {
                        System.out.println(i + ". " + taskService.getTask(i));
                    }
                    continue;
                }
                String[] subcommands = userInput.split(" ", 2);
                if (subcommands[0].equals("delete")) {
                    if (subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'delete' command:\n\n"
                                + "    delete <number>\n\n" + "Please try again.";
                        throw new InvalidDeleteCommandException("'delete' command is missing a number", null,
                                remedy);
                    }
                    int taskId = -1;
                    try {
                        taskId = Integer.parseInt(subcommands[1]);
                    } catch (Exception exception) {
                        String remedy = "Please follow the following format for the 'delete' command:\n\n"
                                + "    delete <number>\n\n" + "Please try again.";
                        throw new InvalidDeleteCommandException(
                                "'" + subcommands[1] + "'" + " is not a number", exception, remedy);
                    }
                    Task taskToDelete = taskService.getTask(taskId);
                    System.out.println("Understood. This task will be deleted:");
                    System.out.println(taskToDelete);
                    taskService.removeTask(taskId);
                    storage.saveTasks(taskService.getTasks());
                    continue;
                }
                if (subcommands[0].equals("mark")) {
                    if (subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'mark' command:\n\n"
                                + "    mark <number>\n\n" + "Please try again.";
                        throw new InvalidMarkCommandException("'mark' command is missing a number", null,
                                remedy);
                    }
                    int taskId = -1;
                    try {
                        taskId = Integer.parseInt(subcommands[1]);
                    } catch (Exception exception) {
                        String remedy = "Please follow the following format for the 'mark' command:\n\n"
                                + "    mark <number>\n\n" + "Please try again.";
                        throw new InvalidMarkCommandException("'" + subcommands[1] + "'" + " is not a number",
                                exception, remedy);
                    }
                    taskService.markTaskAsComplete(taskId);
                    System.out.println("Great Job! This task is marked as complete:");
                    System.out.println(taskService.getTask(taskId));
                    storage.saveTasks(taskService.getTasks());
                    continue;
                }
                if (subcommands[0].equals("unmark")) {
                    if (subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'unmark' command:\n\n"
                                + "    unmark <number>\n\n" + "Please try again.";
                        throw new InvalidUnmarkCommandException("'unmark' command is missing a number", null,
                                remedy);
                    }
                    int taskId = -1;
                    try {
                        taskId = Integer.parseInt(subcommands[1]);
                    } catch (Exception exception) {
                        String remedy = "Please follow the following format for the 'unmark' command:\n\n"
                                + "    unmark <number>\n\n" + "Please try again.";
                        throw new InvalidUnmarkCommandException(
                                "'" + subcommands[1] + "'" + " is not a number", exception, remedy);
                    }
                    taskService.markTaskAsIncomplete(taskId);
                    System.out.println("Alright, This task is marked as incomplete:");
                    System.out.println(taskService.getTask(taskId));
                    storage.saveTasks(taskService.getTasks());
                    continue;
                }
                if (subcommands[0].equals("todo")) {
                    if (subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'todo' command:\n\n"
                                + "    todo <description>\n\n" + "Please try again.";
                        throw new InvalidTodoCommandException("'todo' command is missing a description", null,
                                remedy);
                    }
                    subcommands[1] = subcommands[1].trim();
                    if (subcommands[1].equals("")) {
                        String remedy = "Please follow the following format for the 'todo' command:\n\n"
                                + "    todo <description>\n\n" + "Please try again.";
                        throw new InvalidTodoCommandException("'todo' command is missing a description", null,
                                remedy);
                    }
                    taskService.addTask(new ToDo(subcommands[1]));
                    storage.saveTasks(taskService.getTasks());
                    System.out.println("Noted. The following task has been added: ");
                    int totalNumTasks = taskService.getTasks().size();
                    System.out.println(taskService.getTask(totalNumTasks));
                    System.out.println("So, in total, you have " + totalNumTasks + " task(s) remaining");
                    continue;
                }
                if (subcommands[0].equals("deadline")) {
                    if (subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'deadline' command:\n\n"
                                + "    deadline <description> /by <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidDeadlineCommandException("'deadline' command has an invalid format",
                                null, remedy);
                    }
                    subcommands[1] = subcommands[1].trim();
                    if (subcommands[1].equals("")) {
                        String remedy = "Please follow the following format for the 'deadline' command:\n\n"
                                + "    deadline <description> /by <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidDeadlineCommandException("'deadline' command has an invalid format",
                                null, remedy);
                    }
                    String[] details = subcommands[1].split("/by", 2);
                    if (details.length < 2) {
                        String remedy = "Please follow the following format for the 'deadline' command:\n\n"
                                + "    deadline <description> /by <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidDeadlineCommandException("'deadline' command has an invalid format",
                                null, remedy);
                    }
                    details[0] = details[0].trim();
                    if (details[0].equals("")) {
                        String remedy = "Please follow the following format for the 'deadline' command:\n\n"
                                + "    deadline <description> /by <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidDeadlineCommandException("'deadline' command has an invalid format",
                                null, remedy);
                    }
                    if (details[1].equals("")) {
                        String remedy = "Please follow the following format for the 'deadline' command:\n\n"
                                + "    deadline <description> /by <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidDeadlineCommandException("'deadline' command has an invalid format",
                                null, remedy);
                    }
                    LocalDateTime deadlineDateTime = DateTimeParser.parse(details[1]);
                    taskService.addTask(new Deadline(details[0], deadlineDateTime));
                    storage.saveTasks(taskService.getTasks());
                    System.out.println("Noted. The following task has been added: ");
                    int totalNumTasks = taskService.getTasks().size();
                    System.out.println(taskService.getTask(totalNumTasks));
                    System.out.println("So, in total, you have " + totalNumTasks + " task(s) remaining");
                    continue;
                }
                if (subcommands[0].equals("event")) {
                    if (subcommands.length < 2) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException("'event' command has an invalid format", null,
                                remedy);
                    }
                    String description = subcommands[1].trim();
                    if (description.equals("")) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException(
                                "'event' command has an invalid format: missing description", null, remedy);
                    }
                    int idxOfFrom = subcommands[1].indexOf("/from");
                    if (idxOfFrom == -1) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException(
                                "'event' command has an invalid format: missing /from", null, remedy);
                    }
                    int idxOfTo = subcommands[1].indexOf("/to");
                    if (idxOfTo == -1) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException(
                                "'event' command has an invalid format: missing /to", null, remedy);
                    }
                    description = subcommands[1].substring(0, idxOfFrom).trim();
                    if (description.equals("")) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException(
                                "'event' command has an invalid format: missing description", null, remedy);
                    }
                    String fromDateTimeStr = subcommands[1].substring(idxOfFrom + 5, idxOfTo).trim();
                    if (fromDateTimeStr.equals("")) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException(
                                "'event' command has an invalid format: missing /from", null, remedy);
                    }
                    String toDateTimeStr = subcommands[1].substring(idxOfTo + 3).trim();
                    if (toDateTimeStr.equals("")) {
                        String remedy = "Please follow the following format for the 'event' command:\n\n"
                                + "    event <description> /from <date and/or time> /to <date and/or time>\n\n"
                                + "Please try again.";
                        throw new InvalidEventCommandException(
                                "'event' command has an invalid format: missing /to", null, remedy);
                    }
                    LocalDateTime fromDateTime = DateTimeParser.parse(fromDateTimeStr);
                    LocalDateTime toDateTime = DateTimeParser.parse(toDateTimeStr);
                    taskService.addTask(new Event(description, fromDateTime, toDateTime));
                    storage.saveTasks(taskService.getTasks());
                    System.out.println("Noted. The following task has been added: ");
                    int totalNumTasks = taskService.getTasks().size();
                    System.out.println(taskService.getTask(totalNumTasks));
                    System.out.println("So, in total, you have " + totalNumTasks + " task(s) remaining");
                    continue;
                }
                String remedy = "The following are the only valid commands:\n\n" + "   todo <description>\n\n"
                        + "   deadline <description> /by <date and/or time of deadline>\n\n"
                        + "   event <description> /from <date and/or time that event starts> /to <date and/or time that event ends>\n\n"
                        + "   list\n\n" + "   mark <task number shown after entering the list command>\n\n"
                        + "   unmark <task number shown after entering the list command>\n\n" + "   bye\n\n"
                        + "Please try again.";
                throw new UnknownCommandException("Unknown command '" + subcommands[0] + "' is given.", null,
                        remedy);

            } catch (AtomException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            } catch (DateTimeParserException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            } catch (TaskNotFoundException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            } catch (TaskAlreadyMarkedCompleteException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            } catch (TaskAlreadyMarkedIncompleteException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            } catch (StorageAccessDeniedException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println(exception.getRemedy());
            } catch (StorageWriteException exception) {
                System.out.println(exception.getMessage() + "\n");
                System.out.println("Fatal Error, exiting...");
                break;
            }
        }
    }
}
