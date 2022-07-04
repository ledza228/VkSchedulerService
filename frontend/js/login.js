

searchParams = new URLSearchParams(window.location.search);

if (searchParams.has("code")){
    let code = searchParams.get("code");
    let state = searchParams.get("state");

    oauthLogin(code, state);

}

console.log(searchParams.get("code"));

function login(){
    $.ajax({

    }).done(function(data){
        console.log(data);
    });
}



function oauthLogin(code, state){

    let backUrl = "http://localhost:8080/login/oauth2/code/vk?code=" + code + "&state=" + state;

    $.ajax({
        url: backUrl
    }).done(function(data){
        console.log(data);
    });
}