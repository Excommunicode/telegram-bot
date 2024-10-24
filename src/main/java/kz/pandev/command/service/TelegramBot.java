package kz.pandev.command.service;

import kz.pandev.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    final CommandHandler commandHandler;

    public TelegramBot(BotConfig config, CommandHandler commandHandler) {
        super(config.getToken());
        this.config = config;
        this.commandHandler = commandHandler;
    }


    @Override
    public void onUpdateReceived(Update update) {
        String string = commandHandler.handleCommand(update);
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(string);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
}
