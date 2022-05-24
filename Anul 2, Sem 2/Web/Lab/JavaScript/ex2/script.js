 function validare()
{
    var nume = document.getElementById("nume").value;
    var dataNasterii = document.getElementById("dataNasterii").valueAsDate;
    var varsta = document.getElementById("varsta").value;
    var email = document.getElementById("email").value;
    
    document.getElementById("nume").style.border = " solid 1px black";
    document.getElementById("dataNasterii").style.border = " solid 1px black";
    document.getElementById("varsta").style.border = " solid 1px black";
    document.getElementById("email").style.border = " solid 1px black";
    
    var eroare = 1;
    if(nume == "" || nume.match(/\d/g) != null)
        eroare = eroare * 2;
    
    if(varsta == "" || varsta.match(/^[0-9]+$/) == null)
        eroare = eroare * 3;
        
    if(email == "" || email.match(/@/g) == null)
        eroare = eroare * 5;
        
    if(dataNasterii == null || dataNasterii > Date.now())
        eroare = eroare * 7;
    
    var mesaj = "";
    if(eroare % 2 == 0)
    {
        mesaj += "Nume invalid!\n";
        document.getElementById("nume").style.border = "thick solid #FF0010";
    }
    if(eroare % 3 == 0)
    {
        mesaj += "Varsta invalida!\n";
        document.getElementById("varsta").style.border = "thick solid #FF0010";
    }
    if(eroare % 5 == 0)
    {
        mesaj += "Email invalid!\n";
        document.getElementById("email").style.border = "thick solid #FF0010";
    }
    if(eroare % 7 == 0)
    {
        mesaj += "Data nasterii invalida!\n";
        document.getElementById("dataNasterii").style.border = "thick solid #FF0010";
    }
    
    if(mesaj.length == 0)
        document.getElementById("mesaj").innerText = "Toate campurile sunt completate bine!";
    else
        document.getElementById("mesaj").innerText = mesaj;
}