package bg.sofia.uni.fmi.mjt.newsfeed;
 
import bg.sofia.uni.fmi.mjt.newsfeed.exceptions.ClientRequestException;
import bg.sofia.uni.fmi.mjt.newsfeed.exceptions.NewsFeedClientException;
import bg.sofia.uni.fmi.mjt.newsfeed.news.Article;
import bg.sofia.uni.fmi.mjt.newsfeed.news.QueryMetadata;
import bg.sofia.uni.fmi.mjt.newsfeed.news.Source;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
 
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
public class NewsFeedClient {
    private static final String API_KEY = "paste-your-API-key-here";
 
    private static final String API_URL = "http://newsapi.org";
    private static final String API_ENDPOINT_PATH = "/v2/top-headlines?";
    private static final String API_ENDPOINT_API_KEY = "&apiKey=%s";
    private static final String API_ENDPOINT_PAGER = "&pageSize=%o&page=%o";
 
    private static final int LIMIT_PER_PAGE = 20;
 
    private static final Gson GSON = new Gson();
 
    private final HttpClient newsHttpClient;
    private final String apiKey;
 
    public NewsFeedClient(HttpClient newsHttpClient) {
        this(newsHttpClient, API_KEY);
    }
 
    public NewsFeedClient(HttpClient newsHttpClient, String apiKey) {
        this.newsHttpClient = newsHttpClient;
        this.apiKey = apiKey;
    }
 
    public List<Page> getNews(Query query) throws NewsFeedClientException { 
        List<Page> articles = new ArrayList<>();
        HttpResponse<String> response;
        Type type = new TypeToken<List<Article>>() {
        }.getType();
 
        int page = 0;
        int totalResults = 0;
        int pageSizeLimit = query.getPageSize() == 0 ? LIMIT_PER_PAGE : query.getPageSize();
 
        String url = API_URL + API_ENDPOINT_PATH + query.getQueryEndpoint();
 
        do {
            ++page;
 
            try {
                URI uri = URI.create(url + API_ENDPOINT_PAGER.formatted(pageSizeLimit, page) +
                        API_ENDPOINT_API_KEY.formatted(apiKey));
 
                HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
 
                response = newsHttpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new NewsFeedClientException("Could not retrieve the news feed!");
            }
 
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                JsonElement jsonArticles = new JsonParser().parse(response.body()).getAsJsonObject().get("articles");
                totalResults = GSON.fromJson(response.body(), QueryMetadata.class).getTotalResults();
                Page pageArticles = new Page(GSON.fromJson(jsonArticles.toString(), type), page);
                articles.add(pageArticles);
            } else {
                QueryMetadata metadata = GSON.fromJson(response.body(), QueryMetadata.class);
                throw new ClientRequestException(metadata.getMessage());
            }
 
        } while (totalResults > pageSizeLimit * page);
        return articles;
    }
}
