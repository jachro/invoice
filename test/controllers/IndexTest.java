package controllers;
import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class IndexTest extends FunctionalTest {

    @Test
    public void when_root_is_requested__ok_response_is_returned() {
    	// given

    	// when
        Response response = GET("/");

        // then
        assertIsOk(response);
    }

}