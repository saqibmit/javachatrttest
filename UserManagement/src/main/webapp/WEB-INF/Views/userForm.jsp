<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 <head>
       <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.1.1/chart.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.1.1/helpers.esm.min.js"> </script>
       
</head>
<body>
 <table>
       
            <tr>
            <th>
<canvas id="chartId" aria-label="chart" height="250" width="350"></canvas>
</th>
 <th>
<canvas id="chartId2" aria-label="chart" height="250" width="350"></canvas>
</th>
 </tr>
 
        </tbody>
    </table>
<script>
var chrt = document.getElementById("chartId").getContext("2d");
var chartId = new Chart(chrt, {
   type: 'doughnut',
   data: {
    
     labels: [ <c:forEach var="entry" items="${chartData}">
     "<c:out value="${entry.label}"/>",
     </c:forEach> ],
      datasets: [{
         label: "online tutorial subjects",
      
      data: [<c:forEach var="entry" items="${chartData}">
      <c:out value="${entry.y}"/>,
      </c:forEach> ],
  //    backgroundColor: '#ccc',
    //  borderColor: 'rgba(54, 162, 235, 1)',
       //  backgroundColor: ['red', 'aqua', 'pink', 'lightblue', 'blue', 'gold'],
      backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(54, 162, 235)',
      'rgb(255, 205, 86)'
    ],
   
       //  borderColor: ['red', 'blue', 'fuchsia', 'green', 'navy', 'black'],
         borderWidth: 2,
      }],
   },
   options: {
	   elements: {
           arcText: {
               text: 'Text Around Doughnut', // Text to display around the doughnut
               fontSize: 14,                 // Font size
               fontFamily: 'Arial',          // Font family
               color: '#000',                // Text color
               padding: 20,                  // Distance from the doughnut
               startAngle: 0                 // Starting angle in radians
           }
       },
      responsive: false,
   },
});

</script>


<script>
var chrt = document.getElementById("chartId2").getContext("2d");
var chartId = new Chart(chrt, {
   type: 'bar',
   data: {
    
     labels: [ <c:forEach var="entry" items="${chartData}">
     "<c:out value="${entry.label}"/>",
     </c:forEach> ],
      datasets: [{
         label: "bar chart",
      
      data: [<c:forEach var="entry" items="${chartData}">
      <c:out value="${entry.y}"/>,
      </c:forEach> ],
  //    backgroundColor: '#ccc',
    //  borderColor: 'rgba(54, 162, 235, 1)',
      //   backgroundColor: ['red', 'aqua', 'pink', 'lightgreen', 'blue', 'gold'],
      backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(54, 162, 235)',
      'rgb(255, 205, 86)'
    ],
   
       //  borderColor: ['red', 'blue', 'fuchsia', 'green', 'navy', 'black'],
         borderWidth: 2,
      }],
   },
   options: {
      responsive: false,
   },
});

</script>


    <h2>User Entry Form</h2>
    <form action="saveUser" method="post">
        Name: <input type="text" name="name" /><br />
        Email: <input type="text" name="email" /><br />
        Age: <input type="text" name="age" /><br />
        <input type="submit" value="Submit" />
    </form>
    <a href="export">All Data</a>
    <br/>
    <a href="above40">All Data above 40</a>
    <br/>
    <a href="firstreport">Proposal Report</a>
</body>
</html>
