package com.example.app.cli;

import java.util.List;
import java.util.function.Consumer;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;

/**
 * Paginates any List<T>:
 *   1..pageSize => items
 *   pageSize+1 => Previous Page
 *   pageSize+2 => Next Page
 *   pageSize+3 => Back
 */
public class PaginatedUI<T> {
    private final String title;
    private final List<T> items;
    private final Consumer<T> onSelect;
    private final int pageSize;

    public PaginatedUI(String title, List<T> items, Consumer<T> onSelect, int pageSize) {
        this.title = title;
        this.items = items;
        this.onSelect = onSelect;
        this.pageSize = pageSize;
    }

    public void run() {
        int page = 0;
        int total = (int) Math.ceil(items.size() / (double) pageSize);
        while (true) {
            System.out.println("== " + title + " ==\n");
            int start = page * pageSize;
            int end = Math.min(start + pageSize, items.size());
            int count = end - start;
            for (int i = 0; i < count; i++) {
                System.out.printf("%d. %s%n", i + 1, items.get(start + i));
            }
            System.out.printf("%d. Previous Page%n", pageSize + 1);
            System.out.printf("%d. Next Page%n", pageSize + 2);
            System.out.printf("%d. Back%n", pageSize + 3);

            int choice = Readers.readIntRange(1, pageSize + 3);
            Helper.wipeScreen();

            if (choice <= count) onSelect.accept(items.get(start + choice - 1));
            else if (choice <= pageSize) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            else if (choice == pageSize + 1) page = Math.max(0, page - 1);
            else if (choice == pageSize + 2) page = Math.min(total - 1, page + 1);
            else return;
        }
    }
}