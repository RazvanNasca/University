 function validare()
{
    var nume = $('#nume').val();
    var dataNasterii = $('#dataNasterii').val();
    var varsta = $('#varsta').val();
    var email = $('#email').val();
    
    $('#nume').css("border", " solid 1px black");
    $('#dataNasterii').css("border", " solid 1px black");
    $('#varsta').css("border", " solid 1px black");
    $('#email').css("border", " solid 1px black");
    
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
         $('#nume').css("border", "thick solid #FF0010");
    }
    if(eroare % 3 == 0)
    {
        mesaj += "Varsta invalida!\n";
        $('#varsta').css("border", "thick solid #FF0010");
    }
    if(eroare % 5 == 0)
    {
        mesaj += "Email invalid!\n";
        $('#email').css("border", "thick solid #FF0010");
    }
    if(eroare % 7 == 0)
    {
        mesaj += "Data nasterii invalida!\n";
        $('#datanasterii').css("border", "thick solid #FF0010");
    }
    
    if(mesaj.length == 0)
        $('#mesaj').html("Toate campurile sunt completate bine!");
    else
        $('#mesaj').html(mesaj);
}