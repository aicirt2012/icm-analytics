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
        if (sourceDTO.getSubjectSource() == null) {
            sourceDTO.setSubjectSource("");
        }
        NERPreProcessorService preProcessorService = new NERPreProcessorService();

        // recognize body
        logger.info("Analyzing body text.");
        TextNodeMap bodyTextNodeMap = preProcessorService.getTextNodeMap(sourceDTO.getBodySource());
        String body = bodyTextNodeMap.toPlainText();
        ResultDTO bodyResultDto = nerCoreService.doRecognize(body, TextOrigin.BODY);
        bodyResultDto = NERPostProcessorService.calculateRanges(bodyResultDto, bodyTextNodeMap);

        // recognize subject
        logger.info("Analyzing subject line.");
        TextNodeMap subjectTextNodeMap = preProcessorService.getTextNodeMap(sourceDTO.getSubjectSource());
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
            resultDTO.addAnnotations(taskService.findByUserPatterns(body, sourceDTO.getPatterns(), TextOrigin.BODY));
            resultDTO = NERPostProcessorService.calculateRanges(resultDTO, bodyTextNodeMap);
            resultDTO.addAnnotations(taskService.findByUserPatterns(subject, sourceDTO.getPatterns(), TextOrigin.SUBJECT));
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
        if (textDTO.getSubjectSource() == null) {
            textDTO.setSubjectSource("");
        }

        // recognize body
        String body = textDTO.getBodySource();
        ResultDTO resultDTO = nerCoreService.doRecognize(body, TextOrigin.BODY);

        //recognize subject
        String subject = textDTO.getSubjectSource();
        ResultDTO subjectResult = nerCoreService.doRecognize(subject, TextOrigin.SUBJECT);

        resultDTO.addAnnotations(subjectResult.getAnnotations());
        resultDTO.setEmailId(textDTO.getEmailId());
        if (!textDTO.getPatterns().isEmpty()) {
            logger.info("Detecting user patterns.");
            resultDTO.addAnnotations(taskService.findByUserPatterns(body, textDTO.getPatterns(), TextOrigin.BODY));
            resultDTO.addAnnotations(taskService.findByUserPatterns(subject, textDTO.getPatterns(), TextOrigin.SUBJECT));
        }
        resultDTO = NERPostProcessorService.calculateRangesPlainText(resultDTO);
        logger.info("Detected " + resultDTO.getAnnotations().size() + " entities in email '" + resultDTO.getEmailId() + "'.");
        return Response.status(200).entity(resultDTO).build();
    }

}