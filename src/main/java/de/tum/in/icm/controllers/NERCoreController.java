package de.tum.in.icm.controllers;

import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.services.NERCoreService;
import de.tum.in.icm.services.NERPostProcessorService;
import de.tum.in.icm.services.NERPreProcessorService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/ner")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NERCoreController {

    private NERCoreService nerCoreService = new NERCoreService();

    @POST
    @Path("/recognize")
    public Response recognize(NERInputDTO inputDTO) {
        TextNodeMap textNodeMap = NERPreProcessorService.getTextNodeMap(inputDTO.getHtmlSource());
        NERResultDTO resultDto = nerCoreService.doRecognize(textNodeMap.toPlainText());
        resultDto.setEmailId(inputDTO.getEmailId());
        resultDto = NERPostProcessorService.calculateRanges(resultDto, textNodeMap);
        return Response.status(200).entity(resultDto).build();
    }

}