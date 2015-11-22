var SERVER_URL = "http://127.0.0.1:8000/receiveData";

function processRequest(){
    $.ajax({
        type: "POST",
        url: SERVER_URL,
        data: "Hello World",
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
