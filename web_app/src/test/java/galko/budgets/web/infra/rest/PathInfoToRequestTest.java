package galko.budgets.web.infra.rest;

import galko.budgets.web.dto.Point;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import static galko.budgets.web.infra.rest.PathInfoToRequest.toInteger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PathInfoToRequestTest {

    @Test
    public void test() {

        PathInfoToRequest<Point> pathInfoToPointMapper = new PathInfoToRequest<>(Point.class)
                .parseArg(Index.of(2), toInteger())
                .parseArg(Index.of(3), toInteger());

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn("/myApp/createPoint/3/7");

        Point result = pathInfoToPointMapper.apply(request);

        assertThat(result.getX(), equalTo(3));
        assertThat(result.getY(), equalTo(7));
    }
}
