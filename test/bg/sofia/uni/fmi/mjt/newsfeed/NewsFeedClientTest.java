package bg.sofia.uni.fmi.mjt.newsfeed;
 
import bg.sofia.uni.fmi.mjt.newsfeed.exceptions.ClientRequestException;
import bg.sofia.uni.fmi.mjt.newsfeed.exceptions.NewsFeedClientException;
import bg.sofia.uni.fmi.mjt.newsfeed.news.Article;
import bg.sofia.uni.fmi.mjt.newsfeed.news.NewsFeed;
import bg.sofia.uni.fmi.mjt.newsfeed.news.QueryMetadata;
import bg.sofia.uni.fmi.mjt.newsfeed.news.Source;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
 
@ExtendWith(MockitoExtension.class)
public class NewsFeedClientTest {
 
    private static NewsFeed newsFeed;
    private static Article article1;
    private static Article article2;
    private static String articleString =
            "Article: {source={id='12345', name='Nova'}, author='Kolyo', title='Fake news123', description='Get jebaited FM', url='https://www.tomorrowtides.com/dont-click45.html', publishedAt='23.01.2022', content='Fake content'}" +
                    System.lineSeparator();
    private static String newsfeedJson;
    private static Query query;
 
    @Mock
    private HttpClient newsHttpClientMock;
 
    @Mock
    private HttpResponse<String> newsHttpResponseMock;
 
    private NewsFeedClient client;
 
    @BeforeAll
    public static void setUpClass() {
        Source source = new Source("12345", "Nova");
        QueryMetadata metadata = new QueryMetadata("ok", "Error Message 123", 2);
        article1 = new Article(source, "Kolyo", "Fake news123", "Get jebaited FM",
                "https://www.tomorrowtides.com/dont-click45.html", "23.01.2022", "Fake content");
 
        article2 = new Article(source, "The Weeknd", "Is There Someone Else?", "Best song from Dawn FM album",
                "https://www.youtube.com/watch?v=QgMYdDKVkrw", "07.01.2022", "Song");
 
        Article[] articles = {article1, article2};
        newsFeed = new NewsFeed(metadata, articles);
        newsfeedJson = new Gson().toJson(newsFeed);
 
        query = Query.builder("FM")
                .setCategory("music")
                .setCountry("bg")
                .setPageSize(1)
                .build();
    }
 
    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        when(newsHttpClientMock.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(newsHttpResponseMock);
 
        client = new NewsFeedClient(newsHttpClientMock);
    }
 
    @Test
    public void testGetNewsCorrectArticle() throws NewsFeedClientException {
        when(newsHttpResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(newsHttpResponseMock.body()).thenReturn(newsfeedJson);
 
        int expected = 1;
        List<Page> result = client.getNews(query); 
 
        assertEquals(expected, result.get(result.size() - 1).getPageNumber());
        assertEquals(article1, result.get(0).getArticle(0));
        assertEquals(articleString, result.get(0).getArticle(0).toString());
        assertTrue(result.get(0).toString().contains("Page"));
    }
 
    @Test
    public void testGetNewsIllegalIndex() throws NewsFeedClientException {
        when(newsHttpResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(newsHttpResponseMock.body()).thenReturn(newsfeedJson);
 
        List<Page> result = client.getNews(query);
 
        assertThrows(IllegalArgumentException.class, () -> result.get(result.size() - 1).getArticle(-1));
    }
 
    @Test
    public void testGetNewsNumberOfArticles() throws NewsFeedClientException {
        when(newsHttpResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(newsHttpResponseMock.body()).thenReturn(newsfeedJson);
        int expected = 2;
        List<Page> result = client.getNews(query);
 
        assertEquals(expected, result.get(0).getArticlesCount());
    }
 
    @Test
    public void testGetNewsBadRequest() throws Exception {
        when(newsHttpResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        when(newsHttpResponseMock.body()).thenReturn(newsfeedJson);
 
        assertThrows(ClientRequestException.class, () -> client.getNews(query));
    }
 
}
 
