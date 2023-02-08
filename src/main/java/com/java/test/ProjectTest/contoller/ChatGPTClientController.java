package com.java.test.ProjectTest.contoller;

import com.java.test.ProjectTest.dt.AnswerResponse;
import com.java.test.ProjectTest.dt.QuestionRequest;
import com.java.test.ProjectTest.service.ChatGPTClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/chatgpt")
public class ChatGPTClientController {

    private final ChatGPTClientService chatGPTClientService;


    @Autowired
    public ChatGPTClientController(ChatGPTClientService chatGPTClientService) {
        this.chatGPTClientService = chatGPTClientService;
    }

    @PostMapping("/getanswer")
    public ResponseEntity<AnswerResponse> getAnswer(@RequestBody QuestionRequest questionRequest) {
        try {
            AnswerResponse answerResponse = new AnswerResponse(chatGPTClientService.getResponse(questionRequest.getQuestion()));
            return new ResponseEntity<>(answerResponse, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new AnswerResponse("Error while fetching answer"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/getanswerandwrite")
    public ResponseEntity<AnswerResponse> getAnswerAndWriteToCSV(@RequestBody QuestionRequest questionRequest) {
        try {
            chatGPTClientService.getResponseAndWriteToCSV(questionRequest.getQuestion());
            AnswerResponse answerResponse = new AnswerResponse(chatGPTClientService.getResponse(questionRequest.getQuestion()));
            return new ResponseEntity<>(answerResponse, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new AnswerResponse("Error while fetching answer"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}