package kz.pandev.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TestCommand implements BotCommand {
    @Override
    public String execute(Update update) {
        return update.getMessage().getText();
    }
}
