package de.tum.in.icm.services;


import de.tum.in.icm.dtos.NERInputDTO;
import org.junit.Test;

public class CoreNERServiceTest {

    private CoreNERService testObject = new CoreNERService();

    @Test
    public void Test() {
        String input = "Gespräch  Cable News Network is an American basic cable  Atmosphäre and? satellite television news channel owned by" +
                " the Turner Broadcasting System, a division of Time Warner." +
                " CNN was founded in 1980 by American media proprietor Ted !Turner as a Weihnachtsmarkt  24-hour cable news channe";
        NERInputDTO objectDTO = new NERInputDTO();
        objectDTO.lines = new String[]{"test1", "test2"};
        testObject.recognize(objectDTO);
        testObject.doRecognize(input);
    }
}
