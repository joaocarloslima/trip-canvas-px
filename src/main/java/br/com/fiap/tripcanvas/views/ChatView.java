package br.com.fiap.tripcanvas.views;

import br.com.fiap.tripcanvas.reservation.ReservationService;
import com.openai.services.blocking.ChatService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.Instant;

@Route("chat")
public class ChatView extends VerticalLayout {

    private final ReservationService reservationService;
    private MessageList list = new MessageList();


    public ChatView(ReservationService reservationService) {

        add(new H1("Chat"));

        var input = new MessageInput();
        input.setWidthFull();
        input.addSubmitListener(submitEvent -> sendMessage(submitEvent.getValue(), "USER"));

        //sendMessage("O que vamos aprender hoje?", UserType.PROFESSOR);
        list.setMarkdown(true);
        add(input);
        add(list);
        this.reservationService = reservationService;
    }

    private void sendMessage(String messageText, String userType){
        MessageListItem userMessageItem = new MessageListItem(messageText, Instant.now(), userType);
        userMessageItem.setUserColorIndex(1);
        list.addItem(userMessageItem);

        var iaMessageItem = new MessageListItem("", Instant.now(), "IA");
        iaMessageItem.setUserColorIndex(2);
        list.addItem(iaMessageItem);

        reservationService.sendMessageToAI(messageText).subscribe(partialResponse -> {
            getUI().ifPresent(ui -> ui.access(() -> {
                iaMessageItem.appendText(partialResponse);
            }));
        });


    }

}
