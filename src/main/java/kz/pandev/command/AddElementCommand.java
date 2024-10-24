package kz.pandev.command;

import kz.pandev.model.Category;
import kz.pandev.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class AddElementCommand implements BotCommand {
    private final CategoryRepository categoryRepository;

    /**
     * Выполняет операцию создания и сохранения новой категории на основе текста сообщения.
     * Из объекта {@code update} извлекается текст, где вторая часть (после команды) используется
     * в качестве имени категории.
     *
     * @param update объект обновления, содержащий сообщение. Ожидается, что текст сообщения будет содержать
     *               как минимум два элемента, разделенных пробелом, где второй элемент — имя категории.
     */
    @Override
    @Transactional
    public String execute(Update update) {
        String string = update.getMessage().getText().split(" ")[1];
        Category.CategoryBuilder category = Category.builder();

        category.name(string);
        categoryRepository.save(category.build());
        return string;

    }
}
