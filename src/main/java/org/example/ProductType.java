package org.example;

public enum ProductType {
    Ebook("Ebook",Category.BOOK),
    PaperBook("Paper book", Category.BOOK),
    AudioBook("Audiobook", Category.BOOK),
    Cd("CD",  Category.MUSIC),
    Digital("Digital",  Category.MUSIC),
    Vinyl("Vinyl",  Category.MUSIC);

    private final String displayName;
    private final Category category;

    ProductType(String displayName, Category category) {
        this.displayName = displayName;
        this.category = category;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Category getCategory() {
        return category;
    }

    public enum Category {
        BOOK, MUSIC
    }

    @Override
    public String toString() {
        return displayName;
    }
}
