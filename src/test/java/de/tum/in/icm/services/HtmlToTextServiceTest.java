package de.tum.in.icm.services;

import de.tum.in.icm.entities.IndexedPlainText;
import org.junit.Assert;
import org.junit.Test;

public class HtmlToTextServiceTest {

    @Test
    public void stripHtmlTagsMinimal() {
        String htmlSource = "<div><h1>Test</h1>text Test</div><div>Te<b>st</b></div>";
        IndexedPlainText indexedPlainText = HtmlToTextService.stripHtmlTags(htmlSource);
        Assert.assertNotNull(indexedPlainText);
    }

    @Test
    public void stripHtmlTagsSimple() {
        String htmlSource = "<html><head></head><body><h1>Lorem ipsum</h1><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.</p><div><span>At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></body></html>";
        IndexedPlainText indexedPlainText = HtmlToTextService.stripHtmlTags(htmlSource);
        Assert.assertNotNull(indexedPlainText);
    }

    @Test
    public void stripHtmlTagsComplex() {
        String htmlSource = "<html><head></head><body><div><div><div><div><h1>Lorem ipsum</h1><table><tbody><tr><p></p><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut laboreetdolore magna aliquyam erat, sed diam voluptua.</p></tr><tr><div><span>At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Lorem ipsum dolor sit amet, consetetur sadipscing elitr,sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></tr></tbody></table></div><div><span>This is <a>the</a> ugly Goo<i>gle</i>...</span></div></div></div></div></body></html>";
        IndexedPlainText indexedPlainText = HtmlToTextService.stripHtmlTags(htmlSource);
        Assert.assertNotNull(indexedPlainText);
    }

}