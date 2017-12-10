package de.tum.in.icm.controllers;

import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.entities.IndexedPlainText;
import de.tum.in.icm.services.HtmlToTextService;
import de.tum.in.icm.services.NERCoreService;
import de.tum.in.icm.services.NERPostProcessorService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/ner")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CoreNERController {

    private NERCoreService nerCoreService = new NERCoreService();

    @POST
    @Path("/recognize")
    public Response recognize(NERInputDTO inputDTO) {
        IndexedPlainText indexedPlainText = HtmlToTextService.stripHtmlTags(inputDTO.htmlSource);
        NERResultDTO resultDto = nerCoreService.doRecognize(indexedPlainText.getPlainText());
        resultDto.setEmailId(inputDTO.emailId);
        resultDto = NERPostProcessorService.calculateHtmlIndices(resultDto, indexedPlainText);
        resultDto = NERPostProcessorService.calculateRangeObjects(resultDto, inputDTO.htmlSource);
        return Response.status(200).entity(resultDto).build();
    }

}