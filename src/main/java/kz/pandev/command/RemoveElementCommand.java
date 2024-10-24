package kz.pandev.command;

import kz.pandev.command.exception.NotFoundException;
import kz.pandev.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class RemoveElementCommand implements BotCommand {
    private final CategoryRepository categoryRepository;

    /**
     * Выполняет удаление категории по её имени и идентификатору, извлечённому из сообщения.
     * Из объекта {@code update} извлекается текст, где вторая часть (после команды) используется
     * в качестве имени категории, которую нужно удалить.
     *
     * @param update объект обновления, содержащий сообщение. Ожидается, что текст сообщения будет
     *               содержать как минимум два элемента, разделённых пробелом, где второй элемент — имя категории.
     */
    @Override
    @Transactional
    public String execute(Update update) {
        String string = update.getMessage().getText().split(" ")[1];
        int i = categoryRepository.deleteByNameAndId(string);
        if (i == 0) {
            return String.format("Element with name: %s not found", string);
        }

        return "removed element" + string;
    }
}
