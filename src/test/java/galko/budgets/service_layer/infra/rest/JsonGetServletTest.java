package galko.budgets.service_layer.infra.rest;

import com.google.gson.Gson;
import galko.budgets.service_layer.dto.Point;
import org.junit.Test;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonGetServletTest {

    static class DummyImpl extends JsonGetServlet<Point> {

        public GetRequest lastRequest = null;
        public Point responseToReturn = null;

        @Override
        protected Point doJson(GetRequest request) {
            lastRequest = request;
            return responseToReturn;
        }
    }

    @Test
    public void testThreePartsPathInfo() throws ServletException, IOException {
        DummyImpl dummyImpl = new DummyImpl();

        dummyImpl.doGet(
                setupRequest().withPathInfo("/one/two/three").create(),
                setupResponse(new StringWriter()));

        assertThat(dummyImpl.lastRequest.getPathInfo(), hasSize(3));
        assertThat(dummyImpl.lastRequest.getPathInfo(), contains("one", "two", "three"));
    }

    @Test
    public void testPathInfoNoSlash() throws ServletException, IOException {
        DummyImpl dummyImpl = new DummyImpl();

        dummyImpl.doGet(
                setupRequest().withPathInfo("one").create(),
                setupResponse(new StringWriter()));

        assertThat(dummyImpl.lastRequest.getPathInfo(), hasSize(1));
        assertThat(dummyImpl.lastRequest.getPathInfo(), contains("one"));
    }

    @Test
    public void testNullPathInfo() throws ServletException, IOException {
        DummyImpl dummyImpl = new DummyImpl();

        dummyImpl.doGet(
                setupRequest().withPathInfo(null).create(),
                setupResponse(new StringWriter()));

        assertThat(dummyImpl.lastRequest.getPathInfo(), hasSize(0));
    }

    @Test
    public void testOnlySlashPathInfo() throws ServletException, IOException {
        DummyImpl dummyImpl = new DummyImpl();

        dummyImpl.doGet(
                setupRequest().withPathInfo("/").create(),
                setupResponse(new StringWriter()));

        assertThat(dummyImpl.lastRequest.getPathInfo(), hasSize(0));
    }


    @Test
    public void testResponse() throws ServletException, IOException {

        DummyImpl dummyImpl = new DummyImpl() {{
            responseToReturn = new Point(4, 5);
        }};

        StringWriter responseWriter = new StringWriter();

        dummyImpl.doGet(
                setupRequest().withPathInfo("").create(),
                setupResponse(responseWriter));

        Point responsePoint = new Gson().fromJson(responseWriter.toString(), Point.class);

        assertThat(responsePoint.getX(), equalTo(4));
        assertThat(responsePoint.getY(), equalTo(5));
    }

    private RequestBuilder setupRequest() {
        return new RequestBuilder();
    }

    static class RequestBuilder {

        HttpServletRequest mocked = mock(HttpServletRequest.class);

        public RequestBuilder withPathInfo(String pathInfo) {
            when(mocked.getPathInfo()).thenReturn(pathInfo);
            return this;
        }

        public HttpServletRequest create() {
            return mocked;
        }
    }

    private HttpServletResponse setupResponse(StringWriter sw) {
        PrintWriter writer = new PrintWriter(sw);
        HttpServletResponse mocked = mock(HttpServletResponse.class);
        try {
            when(mocked.getWriter()).thenReturn(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mocked;
    }
}
