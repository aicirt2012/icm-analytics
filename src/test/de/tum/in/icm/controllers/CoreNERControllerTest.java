package de.tum.in.icm.controllers;


import de.tum.in.icm.controllers.CoreNERController;
import de.tum.in.icm.dtos.NERInputDTO;
import org.junit.Test;

public class CoreNERControllerTest {

    private CoreNERController testObject = new CoreNERController();

    @Test
    public void Test() {
        String input = "Gespräch  Cable News Network is an American basic cable  Atmosphäre and? satellite television news channel owned by" +
                " the Turner Broadcasting System, a division of Time Warner." +
                " CNN was founded in 1980 by American media proprietor Ted !Turner as a Weihnachtsmarkt  24-hour cable news channe";
        NERInputDTO objectDTO = new NERInputDTO();
        objectDTO.htmlSource = "<div>test1, test2</div>";
        testObject.recognize(objectDTO);
        testObject.doRecognize(input);
    }
}
