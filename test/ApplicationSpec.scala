package api.controllers
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.MultipartFormData._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  val  unitApp = () => FakeApplication()

  "Application" should {
    "send 404 on a bad request" in new WithApplication(app=unitApp()) {
      val result = route(FakeRequest(GET, "/boum")) must beNone
    }
    "Get a login success with a valid user" in new WithApplication(app=unitApp()) {
      val json = Json.obj("email" -> "jsmith@example.com", "password" -> "secret123")
      val result = route(FakeRequest(POST, "/login").withJsonBody(json)).get
      status(result) must equalTo(OK)
    }
    "Get a login success with a valid user second time" in new WithApplication(app=unitApp()) {
      val json = Json.obj("email" -> "jsmith@example.com", "password" -> "secret123")
      val result = route(FakeRequest(POST, "/login").withJsonBody(json)).get
      status(result) must equalTo(OK)
    }
    "Get a login failure with a invalid password" in new WithApplication(app=unitApp()) {
      val json = Json.obj("email" -> "a@x.com", "password" -> "password")
      val result = route(FakeRequest(POST, "/login").withJsonBody(json)).get
      status(result) must equalTo(NOT_FOUND)
    }
  }
}
