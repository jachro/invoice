package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

public class Index extends Controller {

    public static void index() {
        render();
    }
}