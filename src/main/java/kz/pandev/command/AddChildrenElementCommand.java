package kz.pandev.command;

import kz.pandev.command.exception.NotFoundException;
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
public class AddChildrenElementCommand implements BotCommand {
    private final CategoryRepository categoryRepository;

    /**
     * Сохраняет дочерний элемент с указанным родительским элементом.
     * Из объекта {@code update} извлекается текст сообщения, который должен содержать
     * три части: команду (которая игнорируется в данном методе), имя родительского элемента
     * и имя дочернего элемента.
     *
     * @param update объект обновления, содержащий сообщение с именами родительского и дочернего элементов.
     *               Ожидается, что текст сообщения будет разделен пробелами, и второй элемент будет
     *               именем родительского элемента, а третий — именем дочернего.
     */
    @Override
    @Transactional
    public String execute(Update update) {

        String parentElement = update.getMessage().getText().split(" ")[1];
        String childElement = update.getMessage().getText().split(" ")[2];

        int i = categoryRepository.saveChildWithParent(childElement, parentElement);

        if (i == 0) {
            return String.format("Parent element with name %s was not found", parentElement);
        }

        return childElement;
    }
}
