package kz.pandev.command.service;

import kz.pandev.command.AddChildrenElementCommand;
import kz.pandev.command.AddElementCommand;
import kz.pandev.command.BotCommand;
import kz.pandev.command.HelpCommand;
import kz.pandev.command.RemoveElementCommand;
import kz.pandev.command.TestCommand;
import kz.pandev.command.ViewTreeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHandler {
    private final Map<String, BotCommand> commands = new HashMap<>();
    private final AddElementCommand addElementCommand;
    private final AddChildrenElementCommand addChildrenElementCommand;
    private final RemoveElementCommand removeElementCommand;
    private final HelpCommand helpCommand;
    private final ViewTreeCommand viewTreeCommand;

    @Autowired
    public CommandHandler(ViewTreeCommand viewTreeCommand, HelpCommand helpCommand, AddChildrenElementCommand addChildrenElementCommand, AddElementCommand addElementCommand, RemoveElementCommand removeElementCommand, TestCommand testCommand) {
        this.addElementCommand = addElementCommand;
        this.addChildrenElementCommand = addChildrenElementCommand;
        this.removeElementCommand = removeElementCommand;
        this.helpCommand = helpCommand;
        this.viewTreeCommand = viewTreeCommand;


        commands.put("/viewTree", viewTreeCommand);
        commands.put("/help", helpCommand);
        commands.put("/addChildrenElement", addChildrenElementCommand);
        commands.put("/addElement", addElementCommand);
        commands.put("/removeElement", removeElementCommand);
        commands.put("/test", testCommand);
    }

    /**
     * Обрабатывает входящие команды из обновления {@code Update} и вызывает соответствующие команды
     * для выполнения операций с категориями.
     * <p>
     * Метод проверяет, содержит ли обновление сообщение с текстом, и на основе первой части текста
     * определяет, какую команду необходимо выполнить.
     * <p>
     * Поддерживаются следующие команды:
     * - {@code /addElement} с одним или двумя параметрами для добавления элемента или дочернего элемента.
     * - {@code /removeElement} для удаления элемента.
     * - {@code /help} для отображения помощи.
     * - {@code /viewTree} для отображения иерархии категорий.
     */
    public String handleCommand(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText().split(" ")[0];

            if (command.split(" ")[0].equals("/addElement") && update.getMessage().getText().split(" ").length == 2) {
                return addElementCommand.execute(update);
            }
            if (command.split(" ")[0].equals("/addElement") && update.getMessage().getText().split(" ").length == 3) {

                return addChildrenElementCommand.execute(update);
            }
            if (command.split(" ")[0].equals("/removeElement")) {

                return removeElementCommand.execute(update);
            }
            if (command.split(" ")[0].equals("/help")) {

                return helpCommand.execute(update);
            }
            if (command.split(" ")[0].equals("/viewTree")) {
                return viewTreeCommand.execute(update);
            }

        }
        return "unknown command";
    }
}
