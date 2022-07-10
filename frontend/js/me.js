var me = getProfileDataAPI().then(data => {
    setName(data["first_name"], data["last_name"]);
    setAvatar(data["photo_max"])
    createLogout();
});


isThereToken().then(data => {
    setTokenSavedCheckBox(data);
})


function setName(firstName, secondName){
    var name = document.getElementsByClassName("name");
    name[0].innerText = firstName + " " + secondName;
}

function setAvatar(src){
    var img = document.getElementsByClassName("avatar");
    img[0].src = src;
}

function setTokenSavedCheckBox(isSaved){
    if (isSaved){
        document.getElementsByClassName("is-token-saved")[0].hidden = false; 
        return;
    }
    document.getElementsByClassName("is-token-saved")[0].hidden = true;
}

async function saveToken(){
    var token = document.getElementsByClassName("token-form")[0].value;
    res = await editVkTokenAPI(token);

    if (res === "Success")
        document.getElementsByClassName("is-token-saved")[0].hidden = false;
    else
        alert("error");

    document.getElementsByClassName("token-form")[0].value = "";
}

function createLogout(){
    document.getElementsByClassName("login-href")[0].href = "/logout.html";
    document.getElementsByClassName("login-href")[0].onclick = logout;
    document.getElementsByClassName("login-href")[0].innerText = "Logout";
}