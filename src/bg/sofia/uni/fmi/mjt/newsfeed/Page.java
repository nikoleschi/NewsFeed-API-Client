package bg.sofia.uni.fmi.mjt.newsfeed;
 
import bg.sofia.uni.fmi.mjt.newsfeed.news.Article;
 
import java.util.List;
 
public class Page {
    private final List<Article> page;
    private final int pageNumber;
 
    public Page(List<Article> pages, int pageNumber) {
        this.page = pages;
        this.pageNumber = pageNumber;
    }
 
    public int getPageNumber() {
        return pageNumber;
    }
 
    public int getArticlesCount() {
        return page.size();
    }
 
    public Article getArticle(int index) {
        if (index < 0 || index >= page.size()) {
            throw new IllegalArgumentException("Index should not be negative or exceed page size!");
        }
        return page.get(index);
    }
 
    @Override
    public String toString() {
        return "Page " + pageNumber + ": " + page + System.lineSeparator();
    }
}
