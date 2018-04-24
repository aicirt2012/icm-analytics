package de.tum.in.icm.services;


import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.dtos.TextOrigin;
import org.junit.Assert;
import org.junit.Test;

public class NERCoreServiceTest {

    private NERCoreService nerCoreService = new NERCoreService();

    @Test
    public void oldTest() {
        String input = "Gespräch  Cable News Network is an American basic cable  Atmosphäre and? satellite television news channel owned by" +
                " the Turner Broadcasting System, a division of Time Warner." +
                " CNN was founded in 1980 by American media proprietor Ted !Turner as a Weihnachtsmarkt  24-hour cable news channe";
        nerCoreService.doRecognize(input,TextOrigin.BODY);
    }

    @Test
    public void recognizeSimple() {
        String input = "Lorem ipsum\n" +
                "dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.\n" +
                "Google";
        ResultDTO nerResultDTO = nerCoreService.doRecognize(input, TextOrigin.BODY);
        Assert.assertNotNull(nerResultDTO);
    }

    @Test
    public void recognizeComplex() {
        String input = "Lorem ipsum\n" +
                "dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut laboreetdolore magna aliquyam erat, sed diam voluptua.\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Lorem ipsum dolor sit amet, consetetur sadipscing elitr,sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.\n" +
                "Google\n" +
                "This is the ugly Google...\n";
        ResultDTO nerResultDTO = nerCoreService.doRecognize(input,TextOrigin.BODY);
        Assert.assertNotNull(nerResultDTO);
    }

}
