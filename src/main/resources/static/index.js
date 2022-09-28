let canvasPoints = [];

var app=(function(){
    const URL_API = "http://localhost:8080/blueprints";
  
    //private variables
    var canvas = document.getElementById("mycanvas"), 
        context = canvas.getContext("2d");
    let get_blueprint_btn = document.getElementById("get_blueprint_btn");
    
    //returns an object with 'public' functions:
    return {
      
      //function to initialize application
      init:function(){
        let x0, y0;
        //if PointerEvent is suppported by the browser:
        if(window.PointerEvent) {
          canvas.addEventListener("pointerdown", function(event){
            x0 = event.pageX - event.target.offsetLeft;
            y0 = event.pageY - event.target.offsetTop;
            debugger
            drawPoint(x0, y0, context, canvasPoints);
          });
          canvas.addEventListener("pointerup", (event) => {
            xf = event.pageX - event.target.offsetLeft;
            yf = event.pageY - event.target.offsetTop;
            drawLine(x0, y0, xf, yf,  context);
          });
          get_blueprint_btn.onclick = () => {
            let URL_API_fetch = URL_API;
            currentAuthor = document.getElementById("search_author").value;
            document.querySelector(".table_points h2").innerHTML = `${currentAuthor} blueprints:`;
            list_blueprints(URL_API_fetch, currentAuthor, context, canvasPoints);          
          };

        }
        else {
          canvas.addEventListener("mousedown", function(event){
            x0 = event.pageX - event.target.offsetLeft;
            y0 = event.pageY - event.target.offsetTop;
              drawPoint(x0, y0, context, canvasPoints);
            }
          );
        }
      }    
    };
    
  })(); 


//list blueprints
const list_blueprints = (URL_API_fetch, authorName, context, canvasPoints) => {
  if(authorName){
    URL_API_fetch = `${URL_API_fetch}/${authorName}`
  }
  fetch(URL_API_fetch, {mode:'cors'})
    .then(response => response.json())
    .then(data => buildTable(data, context, canvasPoints))
    .catch(error => {
      alert("Autor no encontrado");
    });
};

//Build a table
const buildTable = (blueprints, context, canvasPoints) => {
  document.getElementById('bp_table').innerHTML = "";
  console.log("Blueprints",blueprints);
  let table = document.createElement('table');
  let thead = document.createElement('thead');
  let tbody = document.createElement('tbody');
  table.appendChild(thead);
  table.appendChild(tbody);

  // Adding the entire table to the body tag
  document.getElementById('bp_table').appendChild(table);
  //HEADING TABLE
  // Creating and adding data to first row of the table
  let row_1 = document.createElement('tr');
  let heading_1 = document.createElement('th');
  heading_1.innerHTML = "Blueprint name";
  let heading_2 = document.createElement('th');
  heading_2.innerHTML = "Number of points";
  let heading_3 = document.createElement('th');
  heading_3.innerHTML = "Actions";
  row_1.appendChild(heading_1);
  row_1.appendChild(heading_2);
  row_1.appendChild(heading_3);
  thead.appendChild(row_1);
  let userTotalpoints = 0;
  //mapping table data
  blueprints.map(bp =>{ 
    let row_2 = document.createElement('tr');
    let row_2_data_1 = document.createElement('td');
    row_2_data_1.innerHTML = bp.name;
    let row_2_data_2 = document.createElement('td');
    row_2_data_2.innerHTML = bp.points.length;
    userTotalpoints += bp.points.length;
    let row_2_data_3 = document.createElement('td');
    let row_2_button = document.createElement('button');
    row_2_button.innerHTML = "Open";
    //button functions
    row_2_button.onclick = () => {
      canvasPoints = drawBlueprint(bp.points, context, canvasPoints);
      debugger
      document.querySelector('.bp_container h3').innerHTML = `Current blueprint: ${bp.name}`;
      let save_update_btn = document.getElementById("save_update_btn"); 
      save_update_btn.onclick = () => {
        let URL_API_fetch = `http://localhost:8080/blueprints/${bp.author}/${bp.name}`;
        console.log("Points", canvasPoints);
        save_update_blueprints(URL_API_fetch, canvasPoints, bp.author, bp.name);
        currentAuthor = document.getElementById("search_author").value;
        URL_API_fetch = `http://localhost:8080/blueprints/`;
        list_blueprints(URL_API_fetch, currentAuthor, context, canvasPoints);
      };
    };
    row_2_data_3.appendChild(row_2_button);


    row_2.appendChild(row_2_data_1);
    row_2.appendChild(row_2_data_2);
    row_2.appendChild(row_2_data_3);
    tbody.appendChild(row_2);
  })
  score = document.getElementById("score");
  score.innerHTML = "Total user points: " + userTotalpoints;



};

// save update blueprints
const save_update_blueprints = (URL_API_fetch, canvasPoints, currentAuthor, currentBlueprintName) => {
  console.log(URL_API_fetch, currentAuthor);
  dataToPut = {
    "author": currentAuthor,
    "points": canvasPoints,
    "name": currentBlueprintName
  }
  console.log(dataToPut);

  fetch(URL_API_fetch, { method:'PUT', mode:'cors', body: JSON.stringify(dataToPut)})
  .then( response => response)
  .catch( error => {
    console.log(error);
    alert("No se puede guardar el blueprint");
    }  
  );
};


//Functions for draw in canvas
  const drawLine = (x0, y0, x1, y1, context) => {
    context.beginPath();
    context.strokeStyle = "blue";
    context.moveTo(x0, y0);
    context.lineTo(x1, y1);
    context.stroke();
    drawPoint(x1, y1, context, canvasPoints);
  }

  const drawPoint = (ponitX, ponitY, context) => {
    //save the points of bluePrint
    canvasPoints.push({"x":Math.floor(ponitX), "y":Math.floor(ponitY)});
    //draw a Point
    context.beginPath();
    context.strokeStyle = "blue";
    // Iniciar trazo
    context.beginPath(); 
    // Dibujar un punto usando la funcion arc
    context.arc(ponitX , ponitY , 1.8, 0, Math.PI * 2, true); 
    context.fill();
    context.stroke();
  };

  const drawBlueprint = (points, context) => {
    canvasPoints = cleanCanvas();
    for (let i = 0; i < points.length; i = +2) {
      if(i <= points.length - 2) {
        drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y, context, canvasPoints);
        drawPoint(points[i + 1].x, points[i + 1].y, context);
      }
    }
    return canvasPoints;
  };

  const cleanCanvas = (canvasPoints) => {
    canvasPoints = [];
    currentBlueprintName = null;
    currentAuthor = null;
    let canvas = document.getElementById('mycanvas');
    let context = canvas.getContext('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
    return canvasPoints
  };





  