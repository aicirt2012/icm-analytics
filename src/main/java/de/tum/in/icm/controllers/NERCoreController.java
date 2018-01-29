package de.tum.in.icm.controllers;

import de.tum.in.icm.dtos.HtmlSourceDTO;
import de.tum.in.icm.dtos.PlainTextDTO;
import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.services.NERCoreService;
import de.tum.in.icm.services.NERPostProcessorService;
import de.tum.in.icm.services.NERPreProcessorService;
import de.tum.in.icm.services.TaskService;

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
    private TaskService taskService = new TaskService();

    @POST
    @Path("/recognize/html")
    public Response recognize(HtmlSourceDTO sourceDTO) {
        if (sourceDTO.getHtmlSource() == null) {
            sourceDTO.setHtmlSource("");
        }
        TextNodeMap textNodeMap = NERPreProcessorService.getTextNodeMap(sourceDTO.getHtmlSource());
        ResultDTO resultDto = nerCoreService.doRecognize(textNodeMap.toPlainText());
        if (!sourceDTO.getRegexPatterns().isEmpty())
            resultDto.addAnnotations(taskService.Search(textNodeMap.toPlainText(), sourceDTO.getRegexPatterns(), resultDto.getAnnotations()));
        resultDto.setEmailId(sourceDTO.getEmailId());
        resultDto = NERPostProcessorService.calculateRanges(resultDto, textNodeMap);
        return Response.status(200).entity(resultDto).build();
    }

    @POST
    @Path("/recognize/text")
    public Response recognize(PlainTextDTO textDTO) {
        if (textDTO.getPlainText() == null) {
            textDTO.setPlainText("");
        }

        ResultDTO resultDto = nerCoreService.doRecognize(textDTO.getPlainText());
        resultDto.setEmailId(textDTO.getEmailId());
        if (!textDTO.getRegexPatterns().isEmpty())
            resultDto.addAnnotations(taskService.Search(textDTO.getPlainText(), textDTO.getRegexPatterns(), resultDto.getAnnotations()));
        resultDto = NERPostProcessorService.calculateRangesPlainText(resultDto);
        return Response.status(200).entity(resultDto).build();
    }

}