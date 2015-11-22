var SERVER_URL = "http://127.0.0.1:8000/receiveData";
function search(ele){
  if(event.keyCode==13){
    processRequest();
  }
}
function processRequest(){
  var username = document.getElementById("user").value;
    $.ajax({
        type: "POST",
        url: SERVER_URL,
        data: username,
        cache: false,
        dataType:"text",
        success: function(data){
          console.log(data);
        },
        error:function(xhr,err,msg){
          console.log("Failed POST Query");
          console.log(xhr);
          console.log(err);
          console.log(msg);
        },
      });
}
