package de.tum.in.icm.NERCoreControllerTest;

import de.tum.in.icm.controllers.NERCoreController;
import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class DummyTest {

    NERCoreController controller = new NERCoreController();

    @Test
    public void run() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.SIMPLE);
        Response re = controller.recognize(inputDTO);
        Assert.assertNotNull(re);
    }

}
