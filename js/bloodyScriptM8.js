var SERVER_URL = "http://127.0.0.1:8000/receiveData";

function processRequest(){

  var getProjectNames = function(callback){
    $.ajax({
        type: "POST",
        url: SERVER_URL,
        headers: {"Accept": "",
             "Content-Type":""},
        data: JSON.stringify(data),
        cache: false,
        dataType:"text",
        success: function(data){
          callback(data);
        },
        error:function(xhr,err,msg){
          console.log("Failed POST Query");
          console.log(xhr);
          console.log(err);
          console.log(msg);
        },
      });
}
