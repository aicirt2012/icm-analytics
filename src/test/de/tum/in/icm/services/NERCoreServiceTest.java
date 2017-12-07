package de.tum.in.icm.services;


import org.junit.Test;

public class NERCoreServiceTest {

    private NERCoreService testObject = new NERCoreService();

    @Test
    public void Test() {
        String input = "Gespräch  Cable News Network is an American basic cable  Atmosphäre and? satellite television news channel owned by" +
                " the Turner Broadcasting System, a division of Time Warner." +
                " CNN was founded in 1980 by American media proprietor Ted !Turner as a Weihnachtsmarkt  24-hour cable news channe";
        testObject.doRecognize(input);
    }
}
