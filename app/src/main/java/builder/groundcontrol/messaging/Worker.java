package builder.groundcontrol.messaging;

import android.os.Bundle;
import android.util.Log;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpallagolla on 5/15/2016.
 */
public class Worker {

    boolean loginSuccess = false;
    private Chat chat;
    private ArrayList<String> chatMessageIds;
    boolean CUSTOMER = true;
    QBChatService chatService;
    QBPrivateChatManager privateChatManager;
    int i =0;
    String tag = "messaging,worker";


    void loginAsPilot()
    {
        login("Raghu","17291729");
    }

    void loginAsCustomer()
    {
        login("Customer1","31423142");
    }

    void sendMessageToCustomer(String msg)
    {
        sendMessage(12677424,msg);
    }

    void sendMessageToPilot(String msg)
    {
        sendMessage(12677400,msg);
    }


    QBMessageListener<QBPrivateChat> privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
        @Override
        public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {

            Log.d(tag, "Chat Listener : " + chatMessage.getBody().toString());
        }

        @Override
        public void processError(QBPrivateChat privateChat, QBChatException error, QBChatMessage originMessage) {
            Log.d(tag, "Chat Listener : Process Error");
        }
    };

    QBPrivateChatManagerListener privateChatManagerListener = new QBPrivateChatManagerListener() {
        @Override
        public void chatCreated(final QBPrivateChat privateChat, final boolean createdLocally) {
            if (!createdLocally) {
                privateChat.addMessageListener(privateChatMessageListener);
                Log.d(tag, "privateChatManagerListener : Not created Locally");
            }
        }
    };

    void sendMessage(Integer opponentId,String msg) {
        privateChatManager.createDialog(opponentId, new QBEntityCallback<QBDialog>() {
            @Override
            public void onSuccess(QBDialog dialog, Bundle args) {
                Log.d(tag, "Successful Dialog Created");
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.d(tag, "Dialog Creating Dialog");
                errors.printStackTrace();
            }
        });

        try

        {
            QBChatMessage chatMessage = new QBChatMessage();
            chatMessage.setBody(msg);
            //chatMessage.setProperty("save_to_history", "1"); // Save a message to history

            QBPrivateChat privateChat = privateChatManager.getChat(opponentId);
            if (privateChat == null) {
                privateChat = privateChatManager.createChat(opponentId, privateChatMessageListener);
            }
            privateChat.sendMessage(chatMessage);
        }
/*
    catch(XMPPException e)

    {

    }
*/ catch (
                SmackException.NotConnectedException e
                )

        {
            e.printStackTrace();
        }

    }

    void login(String userName, String pass) {
        final QBUser user = new QBUser(userName, pass);
        QBAuth.createSession(user, new QBEntityCallback<QBSession>() {


            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                user.setId(qbSession.getUserId());

                Log.d(tag, "Successfully Logged In");
                chatService.login(user, new QBEntityCallback() {

                    @Override
                    public void onSuccess(Object o, Bundle bundle) {

                        Log.d(tag, "Successfully chatService Login");
                        privateChatManager = chatService.getPrivateChatManager();
                        QBChatService.getInstance().getPrivateChatManager().addPrivateChatManagerListener(privateChatManagerListener);

                    }

                    @Override
                    public void onError(QBResponseException errors) {
                        Log.d(tag, "chatService Login failed");
                        errors.printStackTrace();
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(tag, " LoggIn Error");
                e.printStackTrace();
            }
        });
    }

    private void showUserList() {
        List<String> tags = new ArrayList<>();

        QBUsers.getUsersByTags(tags, null, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> result, Bundle params) {
                Log.d(tag, result.toString());
            }

            @Override
            public void onError(QBResponseException e) {

                e.printStackTrace();
            }
        });
    }

    private void createSession() {
        QBAuth.createSession(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession result, Bundle params) {
                Log.d(tag, "Sesssion Succesfully Created");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(tag, "Sesssion Creation Failed");
                e.printStackTrace();
                /*showSnackbarError(null, R.string.splash_create_session_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createSession();
                    }
                });*/
            }
        });
    }


}
