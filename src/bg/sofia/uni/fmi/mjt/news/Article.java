package bg.sofia.uni.fmi.mjt.newsfeed.news;
 
import java.util.Objects;
 
public class Article {
 
    private final Source source;
    private final String author;
    private final String title;
    private final String description;
    private final String url;
    private final String publishedAt;
    private final String content;
 
    public Article(Source source, String author, String title, String description, String url,
                   String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.content = content;
    }
 
    public Source getSource() {
        return source;
    }
 
    public String getAuthor() {
        return author;
    }
 
    public String getTitle() {
        return title;
    }
 
    public String getDescription() {
        return description;
    }
 
    public String getUrl() {
        return url;
    }
 
    public String getPublishedAt() {
        return publishedAt;
    }
 
    public String getContent() {
        return content;
    }
 
    @Override
    public String toString() {
        return "Article: {" +
                "source=" + getSource() +
                ", author='" + getAuthor() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", url='" + getUrl() + '\'' +
                ", publishedAt='" + getPublishedAt() + '\'' +
                ", content='" + getContent() + '\'' +
                '}' + System.lineSeparator();
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article article)) {
            return false;
        }
        return url.equals(article.url);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
