package com.example.app.cli.common;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;

/**
 * Paginates any List<T>: 1..pageSize => items pageSize+1 => Previous Page pageSize+2 => Next Page pageSize+3 => Back
 */
public class PaginatedUI<T> {
    private final String title;
    private final Supplier<List<T>> itemsSupplier;
    private final Consumer<T> onSelect;
    private final int pageSize;
    private final String emptyMessage;

    public PaginatedUI(String title, Supplier<List<T>> itemsSupplier, Consumer<T> onSelect, int pageSize,
            String emptyMessage) {
        this.title = title;
        this.itemsSupplier = itemsSupplier;
        this.onSelect = onSelect;
        this.pageSize = pageSize;
        this.emptyMessage = emptyMessage;
    }

    public void run() {
        int page = 0;
        while (true) {
            List<T> items = itemsSupplier.get();
            if (items.isEmpty()) {
                Helper.wipeScreen();
                System.out.println(emptyMessage);
                Readers.readEnter();
                return;
            }

            int total = (items.size() - 1) / pageSize + 1;
            Helper.wipeScreen();
            System.out.println(title + "\n");
            page = Math.min(page, total - 1);
            int start = page * pageSize;
            int end = Math.min(start + pageSize, items.size());
            int count = end - start;
            for (int i = 0; i < count; i++) {
                System.out.printf("%d.\n%s\n", i + 1, items.get(start + i));
            }
            System.out.printf("%d. Previous Page\n", pageSize + 1);
            System.out.printf("%d. Next Page\n", pageSize + 2);
            System.out.printf("%d. Back\n", pageSize + 3);

            int choice = Readers.readIntRange(1, pageSize + 3);
            Helper.wipeScreen();

            if (choice <= count)
                onSelect.accept(items.get(start + choice - 1));
            else if (choice <= pageSize) {
                System.out.println("Invalid choice. Please try again.");
                Readers.readEnter();
            } else if (choice == pageSize + 1)
                page = Math.max(0, page - 1);
            else if (choice == pageSize + 2)
                page = Math.min(total - 1, page + 1);
            else
                return;
        }
    }
}