var SERVER_URL = "http://127.0.0.1:8000/receiveData";

function processRequest(){
	$("#output").html("Working....");
  var username = document.getElementById("user").value;
    $.ajax({
        type: "POST",
        url: SERVER_URL,
        data: username,
        cache: false,
        dataType:"text",
        success: function(data){
          $("#output").html(data);
          console.log("Success! "+data);
        },
        error:function(xhr,err,msg){
          $("#output").html("Oh no!!! You broke it!!");
          console.log("Failed POST Query");
          console.log(xhr);
          console.log(err);
          console.log(msg);
        },
      });
}
