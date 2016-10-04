package galko.budgets.web.infra.rest.jwt;

import galko.budgets.web.infra.jwt.Jwt;
import galko.budgets.web.infra.jwt.JwtParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JwtParserTest {

    @Test
    public void test() {
        Jwt result = JwtParser.parse("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2dhbGtvLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJmYWNlYm9va3wxMDIxMDMzNjg1OTYwMDgzMCIsImF1ZCI6Inp0ZnF0SDYwekxpUlZpNnlsd25USkxFSFptRUU0WU0xIiwiZXhwIjoxNDc1NjUzNTk1LCJpYXQiOjE0NzU2MTc1OTV9.cBoEewCD085v_AXe4zTH1MuKBI9_QpJIt_czHl2T6Lc");
        assertThat(result.getSub(), equalTo("facebook|10210336859600830"));
    }
}
