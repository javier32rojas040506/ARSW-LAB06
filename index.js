var app=(function(){
  
    //private variables
    var canvas = document.getElementById("mycanvas"), 
        context = canvas.getContext("2d");
     
    //returns an object with 'public' functions:
    return {
      
      //function to initialize application
      init:function(){
        let x0, y0;
        //if PointerEvent is suppported by the browser:
        if(window.PointerEvent) {

          canvas.addEventListener("pointerdown", function(event){
            x0 = event.pageX;
            y0 = event.pageY;
            drawPoint(event.pageX, event.pageY, context);
          });
          canvas.addEventListener("pointerup", (event) => {
            console.log(event)
            drawLine(x0, y0, event.clientX, event.clientY,  context);
        });
        }
        else {
          canvas.addEventListener("mousedown", function(event){
              drawPoint(event.pageX, event.pageY, context);
            }
          );
        }
      }    
    };
    
  })(); 

  const drawLine = (x0, y0, x1, y1, context) => {
    context.beginPath();
    context.strokeStyle = "blue";
    context.moveTo(x0 - 8, y0 - 10);
    context.lineTo(x1 - 8, y1 - 10);
    context.stroke();
    drawPoint(x1, y1, context);
  }

  const drawPoint = (ponitX, ponitY, context) => {
    context.beginPath();
    context.strokeStyle = "blue";
    // Iniciar trazo
    context.beginPath(); 
    // Dibujar un punto usando la funcion arc
    context.arc(ponitX - 8, ponitY - 10, 1.8, 0, Math.PI * 2, true); 
    context.fill();
    context.stroke();
  };
  