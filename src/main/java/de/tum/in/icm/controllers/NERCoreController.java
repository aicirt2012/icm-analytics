package de.tum.in.icm.controllers;

import de.tum.in.icm.dtos.InputSourceDTO;
import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.dtos.TextOrigin;
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
import java.util.logging.Logger;

@Path("/ner")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NERCoreController {

    private static final Logger logger = Logger.getLogger(NERCoreController.class.getName());

    private NERCoreService nerCoreService = new NERCoreService();
    private TaskService taskService = new TaskService();

    @POST
    @Path("/recognize/html")
    public Response recognizeHtml(InputSourceDTO sourceDTO) {
        if (sourceDTO.getBodySource() == null) {
            sourceDTO.setBodySource("");
        }
        // recognize body
        logger.info("Analyzing body text.");
        TextNodeMap bodyTextNodeMap = NERPreProcessorService.getTextNodeMap(sourceDTO.getBodySource());
        String body = bodyTextNodeMap.toPlainText();
        ResultDTO bodyResultDto = nerCoreService.doRecognize(body, TextOrigin.BODY);
        bodyResultDto = NERPostProcessorService.calculateRanges(bodyResultDto, bodyTextNodeMap);

        // recognize subject
        logger.info("Analyzing subject line.");
        TextNodeMap subjectTextNodeMap = NERPreProcessorService.getTextNodeMap(sourceDTO.getSubjectSource());
        String subject = subjectTextNodeMap.toPlainText();
        ResultDTO subjectResultDTO = nerCoreService.doRecognize(subject, TextOrigin.SUBJECT);
        subjectResultDTO = NERPostProcessorService.calculateRanges(subjectResultDTO, subjectTextNodeMap);

        //final result
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.addAnnotations(bodyResultDto.getAnnotations());
        resultDTO.addAnnotations(subjectResultDTO.getAnnotations());
        // add tasks
        if (!sourceDTO.getPatterns().isEmpty()) {
            logger.info("Detecting user patterns.");
            resultDTO.addAnnotations(taskService.Search(body, sourceDTO.getPatterns(), bodyResultDto.getAnnotations(), TextOrigin.BODY));
            resultDTO = NERPostProcessorService.calculateRanges(resultDTO, bodyTextNodeMap);
            resultDTO.addAnnotations(taskService.Search(subject, sourceDTO.getPatterns(), subjectResultDTO.getAnnotations(), TextOrigin.SUBJECT));
        }
        resultDTO.setEmailId(sourceDTO.getEmailId());
        logger.info("Detected " + resultDTO.getAnnotations().size() + " entities in email '" + resultDTO.getEmailId() + "'.");
        return Response.status(200).entity(resultDTO).build();
    }

    @POST
    @Path("/recognize/text")
    public Response recognizePlainText(InputSourceDTO textDTO) {
        if (textDTO.getBodySource() == null) {
            textDTO.setBodySource("");
        }

        // recognize body
        String body = textDTO.getBodySource();
        ResultDTO resultDto = nerCoreService.doRecognize(body, TextOrigin.BODY);
        //recognize subject
        String subject = textDTO.getSubjectSource();
        ResultDTO subjectResult = nerCoreService.doRecognize(subject, TextOrigin.SUBJECT);
        resultDto.addAnnotations(subjectResult.getAnnotations());
        resultDto.setEmailId(textDTO.getEmailId());
        if (!textDTO.getPatterns().isEmpty()) {

            resultDto.addAnnotations(taskService.Search(body, textDTO.getPatterns(), resultDto.getAnnotations(), TextOrigin.BODY));
            resultDto.addAnnotations(taskService.Search(subject, textDTO.getPatterns(), subjectResult.getAnnotations(), TextOrigin.SUBJECT));
        }
        resultDto = NERPostProcessorService.calculateRangesPlainText(resultDto);
        return Response.status(200).entity(resultDto).build();
    }

}