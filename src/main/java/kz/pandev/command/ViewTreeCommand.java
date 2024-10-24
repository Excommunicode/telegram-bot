package kz.pandev.command;

import kz.pandev.model.Category;
import kz.pandev.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class ViewTreeCommand implements BotCommand {
    private final CategoryRepository categoryRepository;

    /**
     * Выполняет построение иерархического дерева категорий и возвращает его в виде строки.
     * Извлекает все категории из репозитория и строит древовидную структуру с использованием
     * отступов для указания уровня вложенности каждой категории.
     *
     * @param update объект обновления, не используется в данном методе, но необходим для соответствия интерфейсу.
     * @return строковое представление дерева категорий с отступами для визуализации иерархии.
     */
    @Override
    public String execute(Update update) {

        List<Category> categories = categoryRepository.findAll();


        StringBuilder treeBuilder = new StringBuilder();
        buildCategoryTree(null, categories, treeBuilder, 0);
        System.err.println(treeBuilder);
        return treeBuilder.toString();
    }

    private void buildCategoryTree(Category parent, List<Category> categories, StringBuilder treeBuilder, int level) {
        for (Category category : categories) {
            if (Objects.equals(category.getParent(), parent)) {

                treeBuilder.append("  ".repeat(level));
                treeBuilder.append("- ").append(category.getName()).append("\n");


                buildCategoryTree(category, categories, treeBuilder, level + 1);
            }
        }
    }
}