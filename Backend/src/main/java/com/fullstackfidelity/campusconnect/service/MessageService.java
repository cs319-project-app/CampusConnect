//package com.fullstackfidelity.campusconnect.service;
//
//import com.fullstackfidelity.campusconnect.chat.controller.MessageRepository;
//import com.fullstackfidelity.campusconnect.chat.model.Message;
//import com.fullstackfidelity.campusconnect.chat.model.Status;
//
//import java.util.List;
//
//public class MessageService {
//
//    private final MessageRepository messageRepository;
//
//    private UserService userService;
//
//    public MessageService(UserService userService, MessageRepository messageRepository)
//    {
//        this.messageRepository = messageRepository;
//        this.userService = userService;
//    }
//    public List<Message> getAllMessages() {
//        return messageRepository.findAll();
//    }
//
//    public Message getMessageById(Long id) {
//        return (Message) messageRepository.findById(id).orElse(null);
//    }
//
//    public Message createMessage(Message message) {
//        return messageRepository.save(message);
//    }
//
//    public Message readMessage(Message message, int val){
//        Message m = (Message) messageRepository.findById(message.getId()).orElse(null);
//        if(val == 0){
//            message.setStatus(Status.UNREAD);
//        }else
//        {
//            message.setStatus(Status.READ);
//        }
//        return messageRepository.save(m);
//    }
//    public void deleteMessage(Long id) {
//        messageRepository.deleteById(id);
//    }
//}
