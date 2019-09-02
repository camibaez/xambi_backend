var slideMap = {
    0: "phoneSlide",
    1: "codeSlide"
}
var registrationData = {
    phone: "",
    id : "",
    otp : "",
    name : ""
}
$(function () {
    $("#phoneBtn").click(registerPhone);
    $("#codeBtn").click(confirmCode);
    $("#usernameBtn").click(registerUsername);
    $(".back-button").click(moveSlide(-1));

});

function moveSlide(direction) {
    return function () {
        var originSlide = $(this).parent();
        var targetSlide = direction > 0? originSlide.next() : originSlide.prev();

        originSlide.hide();
        targetSlide.show();
    }

}

function registerPhone(e) {
    registrationData['phone'] = $("#phoneInput").val();
    
    var data = {
        "phone": $("#phoneInput").val(),
        "context": 1
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/registration/initialize",
        data: JSON.stringify(data),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            registrationData['id'] = data['id'];
            registrationData['otp'] = data['otp'];
            
            $("#phoneSlide").hide();
            $("#codeSlide").show();
            $("#codeInput").val(data['otp']); 
        },
        error: function (e, msg, z) {
            alert(e.responseText);
        }
    });

}

function confirmCode(e) {
    var data = {
        "id" : registrationData['id'],
        "otp" : $("#codeInput").val()
    };
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/registration/confirm-otp",
        data: JSON.stringify(data),
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert("Code confirmed");
            $("#codeSlide").hide();
            $("#usernameSlide").show();
        },
        error: function (e, msg, z) {
            alert("Error");
        },
    });
}

var registerUsername = function(e){
    var data = {
        "id" : registrationData['id'],
        "username" : $("#usernameInput").val(),
        "context" : 3
    };
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/registration/complete",
        data: JSON.stringify(data),
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert("Username registered");
            clompleteUserRegister();
            $("#usernameSlide").hide();
            $("#finishSlide").show();
        },
        error: function (e, msg, z) {
            alert("Error");
        },
    });
}

function clompleteUserRegister (){
    data = {
        "id" : registrationData['id'],
        "context" : 4
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/registration/finish",
        data: JSON.stringify(data),
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert("User registered");
        },
        error: function (e, msg, z) {
            alert("Error");
        },
    });
}