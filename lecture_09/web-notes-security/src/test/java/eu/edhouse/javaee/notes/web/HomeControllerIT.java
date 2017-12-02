package eu.edhouse.javaee.notes.web;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

/**
 * @author Frantisek Spacek
 */
@RunWith(Arquillian.class)
public class HomeControllerIT {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(MavenImporter.class, "homeController.war")
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class);
        war.writeTo(System.out, Formatters.VERBOSE);
        return war;
    }

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentUrl;

    @FindBy(id = "addNote")
    private WebElement addButton;

    @Test
    public void shouldOpenIndex_andFoundAddNoteButton() {
        browser.get(deploymentUrl.toExternalForm());
        Assert.assertEquals("Add Note", addButton.getText());
    }

}
