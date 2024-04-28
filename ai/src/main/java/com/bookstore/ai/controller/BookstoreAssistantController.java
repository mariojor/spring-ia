package com.bookstore.ai.controller;


import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

    private final OpenAiChatClient openAiChatClient;

    public BookstoreAssistantController(OpenAiChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    @GetMapping("/informations")
    public String bookstoreChat (@RequestParam(value = "mensagem" , defaultValue = "Quais são os 5 livros mais procurados do mundo?") String mensagem) {
        return openAiChatClient.call(mensagem);
    }

    @GetMapping("/prompt")
    public ChatResponse bookstoreChat2 (@RequestParam(value = "mensagem" , defaultValue = "Quais são os 5 livros mais procurados do mundo?") String mensagem) {
        return openAiChatClient.call(new Prompt(mensagem));
    }

    @GetMapping("/review")
    public String bookstoreReview(@RequestParam(value = "book", defaultValue = "A Cabana") String book){
        PromptTemplate promptTemplate = new PromptTemplate(
                """
                Me forneca um resumo sobre o livro {book} 
                e me traga a biolgrafia do autor?
                """);

        promptTemplate.add("book", book);
        return this.openAiChatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    @GetMapping("/stream/informations")
    public Flux<String> bookstoreChatStream (@RequestParam(value = "mensagem" , defaultValue = "Quais são os 5 livros mais procurados do mundo?") String mensagem) {
        return openAiChatClient.stream(mensagem);
    }

    @GetMapping("/prompt/informations")
    public Flux<ChatResponse> bookstoreChatStreamPrompt (@RequestParam(value = "mensagem" , defaultValue = "Quais são os 5 livros mais procurados do mundo?") String mensagem) {
        return openAiChatClient.stream(new Prompt(mensagem));
    }
}
