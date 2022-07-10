var mainOrdersDiv = document.getElementsByClassName("all-orders").item(0); 
var parrentOrder = document.getElementsByClassName("order").item(0);


var parrentChat = document.getElementsByClassName("parrent-chat").item(0);
var chatExample = document.getElementsByClassName("chat").item(0);

chatExample.remove();


var mainOffset = 0;

parrentOrder.remove();

getOrdersAPI().then(o => {

    o.forEach(order => {
        getUIOrder(order)
    });
})

createLogout();


function getUIOrder(order){
    var id = order["id"];
    var date = order["dateTime"];
    var text = order["text"];
    var conversationName = order["conversationName"];
    var isActive = order["isActive"];

    newChild = parrentOrder.cloneNode(true);

    divName = newChild.querySelector(".conv-name");
    divName.innerText = conversationName;

    divDate = newChild.querySelector(".date");
    divDate.innerText = (new Date(date)).toISOString();

    divText = newChild.querySelector(".text");
    divText.innerText = text;
    
    if (isActive === false){
        divIsActive = newChild.querySelector(".is-active");
        divIsActive.innerText = "finished"; 
        divIsActive.classList.remove("text-success");
        divIsActive.classList.add("text-secondary");
    }

    deleteButton = newChild.querySelector(".delete-order-button");
    deleteButton.addEventListener("click", function(){
        deleteOrder(id);
        location.href = "/messages.html";
    })

    mainOrdersDiv.appendChild(newChild);
}



function createUINewOrder(offset){

    console.log(offset);
    if (offset == 0)
        mainOffset = 0


    document.getElementsByClassName("create-new-order").item(0).hidden = false;
    deleteAllChats();

    getConversationAPI(10, offset).then(cs => {
        cs.forEach(c => {

            if (c["peer"]["type"] === "CHAT"){
                chatName = c["chatSettings"]["title"];
                chatId = c["peer"]["localId"];

                if (c["chatSettings"]["photo"] == null)
                    chatImg = "https://vk.com/images/community_50.png";
                else
                    chatImg = c["chatSettings"]["photo"]["photo50"];
                
                createNewChat(chatName, chatImg, chatId);
            }
            else if (c["peer"]["type"] === "USER"){
                userName = c["chatSettings"]["firstName"] + " " + c["chatSettings"]["lastName"];
                userId = c["peer"]["localId"];
                userImg = c["chatSettings"]["photo50"];

                if (userImg == null)
                    userImg = "https://vk.com/images/camera_50.png";

                createNewChat(userName, userImg, userId);
            }
        });
    }).catch(e => {
        deleteAllChats()
        createNewChat("Error with token!", "/static/error.png", "0");

    });
}

function createNewChat(name, img, id){
    var newChat = chatExample.cloneNode(true);
    
    var nameChat = newChat.querySelector(".conv-name");
    var imageChat = newChat.querySelector(".conv-img");

    newChat.id = id;
    nameChat.innerText = name;
    imageChat.src = img;

    parrentChat.appendChild(newChat);
}


function deleteAllChats(){
    parrentChat.textContent = "";
}

function chooseConversation(el){
    deleteConversationSelection();

    el.classList.add("selected");
    el.classList.add("btn-primary");
}



function deleteConversationSelection(){
    var selected = document.getElementsByClassName("selected").item(0);

    if (selected === null )
        return;

    selected.classList.remove("btn-primary");
    selected.classList.remove("selected");
}

function hideCreateUIOrder(){
    document.getElementsByClassName("create-new-order").item(0).hidden = true;
}

function createLogout(){
    document.getElementsByClassName("login-href")[0].href = "/logout.html";
    document.getElementsByClassName("login-href")[0].onclick = logout;
    document.getElementsByClassName("login-href")[0].innerText = "Logout";
}


function addNewChats(){
    mainOffset += 10;
    createUINewOrder(mainOffset);
}



function sendOrderCreation(){
    dateTimeText = document.getElementsByClassName("orderTime").item(0).value;
    dateTime = new Date(dateTimeText);

    if (dateTime.toJSON() === null){
        document.getElementsByClassName("alert-date-error").item(0).hidden = false;
        return;
    }

    if (dateTime <= new Date()){
        document.getElementsByClassName("alert-date-past").item(0).hidden = false;
        return;
    }
    
    message = document.getElementsByClassName("message-text-input").item(0).value;

    if (message === ""){
        document.getElementsByClassName("alert-message").item(0).hidden = false;
        return;
    }

    chat = document.getElementsByClassName("selected").item(0);
    if (chat === null){
        document.getElementsByClassName("alert-chat-id").item(0).hidden = false;
        return;
    }    
    chatId = chat.id;

    createOrder(dateTime.toISOString(), chatId, message).then(m => { 
        document.getElementsByClassName("alert-success").item(0).hidden = false;
        location.href = "/messages.html";
    }).catch(c => {
        console.log(c);
        document.getElementsByClassName("alert-error-server").item(0).hidden = false;
    });

}