package bg.sofia.uni.fmi.mjt.newsfeed.news;
 
import java.util.Objects;
 
public class Source {
    private final String id;
    private final String name;
 
    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }
 
    @Override
    public String toString() {
        return "{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
 
    public String getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
}
