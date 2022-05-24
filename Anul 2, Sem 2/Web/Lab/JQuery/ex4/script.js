function sortTableOrizontal(n)
{
      var table, rows, gata, i, x, y, ok, dir, nrSchimbari = 0;
      //table = document.getElementById("table1");
      table = $("#table1");
      gata = true;
      dir = "asc"; 
      while (gata)
      {
        gata = false;
        rows = table.rows;
        
        for (i = 1; i < (rows.length - 1); i++)
        {
          ok = false;
          
          x = rows[i].getElementsByTagName("td")[n];
          y = rows[i + 1].getElementsByTagName("td")[n];
          
          if (dir == "asc")
          {
            if ($(x).html().toLowerCase() > $(y).html().toLowerCase())
            {
              ok = true;
              break;
            }
          }
          else
          if (dir == "desc")
          {
            if ($(x).html().toLowerCase() < $(y).html().toLowerCase())
            {
              ok = true;
              break;
            }
          }
        }
        if (ok)
        {
          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
          gata = true;
          nrSchimbari ++;      
        }
        else
        {
          if (nrSchimbari == 0 && dir == "asc")
          {
            dir = "desc";
            gata = true;
          }
        }
      }
}


function sortTableVertical(n)
{
      var table, cols, gata, i, x, y, ok, dir, nrSchimbari = 0;
      //table = document.getElementById("table2");
      table = $("#table2");
      gata = true;
      dir = "asc"; 
      while (gata)
      {
            
        gata = false;
        cols = table.rows;
        
         var arr = [];
         for (var k = 0; k < cols.length; k++)
            arr[k] = [];
      
         for (i = 0; i < cols.length; i++)
            for (var j = 0; j < cols.length; j++)
                  arr[i].push(cols[j].cells[i + 1]);

        
        for (i = 0; i < (cols.length - 1); i++)
        {
          ok = false;
          
          x = arr[i][n];
          y = arr[i + 1][n];
          
          if (dir == "asc")
          {
            if ($(x).html().toLowerCase() > $(y).html().toLowerCase())
            {
              ok= true;
              break;
            }
          }
          else
          if (dir == "desc")
          {
            if ($(x).html().toLowerCase() < $(y).html().toLowerCase())
            {
              ok = true;
              break;
            }
          }
        }
        if (ok)
        {
          for (let j = 0; j < cols.length; j++)
          {
            const k = cols[j].cells[i + 1].innerHTML;
            cols[j].cells[i + 1].innerHTML = cols[j].cells[i + 2].innerHTML;
            cols[j].cells[i + 2].innerHTML = k;
          }
          gata = true;
          nrSchimbari ++;      
        }
        else
        {
          if (nrSchimbari == 0 && dir == "asc")
          {
            dir = "desc";
            gata = true;
          }
        }
      }
}