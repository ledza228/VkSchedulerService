var host = "http://0.0.0.0:8080";

var getProfileUrl = "/api/user/me";
var getOrdersUrl = "/api/order/"
var getConversationsUrl = "/api/vk/conversations";
var getTokenEditUrl = "/api/user/edit/token";
var getCreateOrderUrl = "/api/order/"
var getTokenCheckUrl = "/api/user/token";

var jwt = localStorage.getItem("jwt");


authorization_header = {"Authorization": jwt};


async function doGetRequest(url){
    var result;

    await $.ajax({
        url: url,
        headers: authorization_header,

        success: function(data){
            result = data;
        },

        error: function(request){unauthorizedResultFunc(request)}
    })

    return result;
}


async function getProfileDataAPI(){
    url = host + getProfileUrl;
    return await doGetRequest(url);
}


async function getOrdersAPI(){
    url = host + getOrdersUrl;
    return await doGetRequest(url);
}


async function getConversationAPI(length, offset){
    url = host + getConversationsUrl + "?length=" + length + "&offset=" + offset;
    return await doGetRequest(url);  
}



async function editVkTokenAPI(token){
    url = host + getTokenEditUrl;
    var result;
    
    await $.ajax({
        url: url,
        type: "Patch",
        headers: authorization_header,
        contentType: "text/plain; charset=utf-8",
        data: token,

        success: function(data){
            result = data;
        },
        error: function(data){
            console.log("error");
            result = data;
        }
    });

    return result;
}


async function createOrder(date, convId, text){
    
    order = {"conversationId": convId, "dateTime": date, "text": text};
    var result;

    await $.ajax({
        url: host + getCreateOrderUrl,
        type: "Post",
        headers: authorization_header,
        contentType: "application/json",
        data: JSON.stringify(order),

        success: function(data){
            result = data;
        },
        error: function(data){
            result = data;
        }
    });

    return result;
}


async function isThereToken(){
    url = host + getTokenCheckUrl;
    return await doGetRequest(url);
} 

async function deleteOrder(id){
    url = host + getCreateOrderUrl + id;
    console.log(url);
    var result;

    await $.ajax({
        url: url,
        headers: authorization_header,
        type: "Delete",

        success: function(data){
            result = data;
        },
        error: function(data){
            result = data;
        }
    });
    return result;
}


function unauthorizedResultFunc(request){
    if (request.status === 401){
        console.log("Unauthorized");
        location.href = "/login.html";
    }
}


function logout(){
    localStorage.removeItem("jwt");
}