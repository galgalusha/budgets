package galko.budgets.business.model;

import galko.budgets.business.api.os.ITimeService;
import org.junit.Test;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonthlyTest {

    private ITimeService timeService = mock(ITimeService.class);

    private void setupCurrentTime(ZonedDateTime value) {
        when(timeService.getCurrent()).thenReturn(value);
    }

    @Test
    public void mayEndDayIs31() {

        setupCurrentTime(date(2000, 5, 15));

        Monthly monthly = new Monthly(timeService);

        assertThat(monthly.beginningOfcurrent(), equalTo(date(2000, 5, 1)));
        assertThat(monthly.endOfcurrent(), equalTo(date(2000, 5, 31)));
    }

    @Test
    public void juneEndDayIs30() {

        setupCurrentTime(date(2000, 6, 15));

        Monthly monthly = new Monthly(timeService);

        assertThat(monthly.beginningOfcurrent(), equalTo(date(2000, 6, 1)));
        assertThat(monthly.endOfcurrent(), equalTo(date(2000, 6, 30)));
    }

    @Test
    public void februaryEndDayIs29() {

        setupCurrentTime(date(2000, 2, 15));

        Monthly monthly = new Monthly(timeService);

        assertThat(monthly.beginningOfcurrent(), equalTo(date(2000, 2, 1)));
        assertThat(monthly.endOfcurrent(), equalTo(date(2000, 2, 29)));
    }

    private ZonedDateTime date(int year, int month, int day) {
        return ZonedDateTime.of(year, month, day, 0, 0, 0, 0, ZoneId.systemDefault());
    }
}
