package com.example.app.cli.common;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.example.app.cli.utils.Helper;
import com.example.app.cli.utils.Readers;

/**
 * A utility class for paginating a list of items and displaying them in a user-friendly format.
 *
 * This class divides a list of items into pages, each containing a fixed number of items.
 * Users can navigate between pages, select an item, or return to a previous menu.
 *
 * @param <T> The type of items to paginate.
 */
public class PaginatedUI<T> {
    /**
     * The title of the paginated UI.
     */
    private final String title;
    /**
     * A supplier that provides the list of items to paginate.
     */
    private final Supplier<List<T>> itemsSupplier;
    /**
     * A consumer that handles the action to perform when an item is selected.
     */
    private final Consumer<T> onSelect;
    /**
     * The number of items to display per page.
     */
    private final int pageSize;
    /**
     * The message to display when there are no items to show.
     */
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