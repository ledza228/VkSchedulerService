
var host = "http://0.0.0.0:8080";
var backendAuthUrl = host + "/oauth2/authorization/vk";
var backendCodeUrl = host + "/login/oauth2/code/vk?code=";


var oauthHeader = "X-AUTH-TOKEN";

searchParams = new URLSearchParams(window.location.search);

if (searchParams.has("code")){
    let code = searchParams.get("code");
    let state = searchParams.get("state");

    oauthLogin(code, state);

}

console.log(searchParams.get("code"));

function login(){
    $.ajax({
        url: backendAuthUrl
    }).done(function(data, textStatus, request){
        
        token = request.getResponseHeader(oauthHeader);
        localStorage.setItem("token", token);

        location.href = data["urlRedirect"];
    });
}



function oauthLogin(code, state){

    let backUrl = backendCodeUrl + code + "&state=" + state;
    let token = localStorage.getItem("token");

    $.ajax({
        url: backUrl,
        headers: {"X-AUTH-TOKEN": token}
    }).done(function(data){
        jwt = data;
        localStorage.setItem("jwt", jwt);
        localStorage.removeItem("token");
        location.href = "/me.html";
    });

    //location.href = "/me.html";
}


