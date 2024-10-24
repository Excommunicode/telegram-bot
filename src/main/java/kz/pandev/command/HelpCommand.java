package kz.pandev.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements BotCommand {
    @Override
    public String execute(Update update) {
        return """
                Доступные команды:
                
                1. /viewTree
                Дерево отображается в структурированном виде.
                
                2. /addElement <название элемента>
                Этот элемент будет корневым, если у него нет родителя.
                
                3. /addElement <родительский элемент> <дочерний элемент>
                Добавление дочернего элемента к существующему элементу. Если родительский элемент не существует, выводить соответствующее сообщение.
                
                4. /removeElement <название элемента>
                При удалении родительского элемента, все дочерние элементы также должны быть удалены. Если элемент не найден, выводить соответствующее сообщение.
                
                5. /help
                Выводит список всех доступных команд и краткое их описание.
                """;
    }
}
