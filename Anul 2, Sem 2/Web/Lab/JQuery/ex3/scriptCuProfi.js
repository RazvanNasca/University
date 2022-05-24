var lista = ['1','1','2','2','3','3','4','4','5','5','6','6','7','7','8','8','9','9','10','10','11','11','12','12'];
var valoriAfisate = [];
var idValori = [];
var celuleIntoarse = 0;

Array.prototype.randomizareLista = function(){
	var i = this.length, j, temp;
	while(--i > 0){
		j = Math.floor(Math.random() * (i+1));
		temp = this[j];
		this[j] = this[i];
		this[i] = temp;
	}
}

function jocNou(){
	celuleIntoarse = 0;
	var output = '';
	lista.randomizareLista();
	for(var i = 0; i < lista.length; i++){			
		output += '<div id= "tile_'+ i +'" onclick = "memorare(this,\'' +lista[i]+ '\')"></div>'
	}
	$('#memory_board').html(output);
}

function getPhoto(val)
{
	if (val == 1)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Vancea-Alexandru.jpg";
	if (val == 2)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Czibula-Istvan.jpg";
	if (val == 3)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Grigoreta-Cojocar.jpg";
	if (val == 4)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Diosan-Laura.jpg";
	if (val == 5)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Bufnea-Darius.jpg";
	 if (val == 6)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Boian-Rares-133x100.jpg";
	if (val == 7)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Mihis-Andreea.jpg";
	if (val == 8)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Serban-Camelia.jpg";
	if (val == 9)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Suciu-Dan.jpg";
	if (val == 10)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Czibula-Gabriela.jpg";
	if (val == 11)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Coroiu-Adriana.jpg";
	if (val ==12)
		return "http://www.cs.ubbcluj.ro/wp-content/uploads/Diana-Miholca-100x133.jpg";
}


function memorare(tile, val){
	if(tile.innerHTML == "" && valoriAfisate.length < 2)
    {
		tile.style.background = "url("+getPhoto(val)+") no-repeat";
		tile.innerHTML = val;
		if(valoriAfisate.length == 0)
        {
			valoriAfisate.push(val);
			idValori.push(tile.id)
		}
        else
        if(valoriAfisate.length == 1)
        {
			valoriAfisate.push(val);
			idValori.push(tile.id);
			if(valoriAfisate[0] == valoriAfisate[1])
            {
				celuleIntoarse += 2;
				valoriAfisate = [];
				idValori = [];
				if(celuleIntoarse == lista.length)
                {
					alert("Play again!");
					$('#memory_board').html("");
					jocNou();
				}
			}
            else
            {
				function flip2Back()
                {
					//var tile1 = document.getElementById(idValori[0]);
					//var tile2 = document.getElementById(idValori[1]);
					//tile1.style.background='grey';
					//tile1.innerHTML = "";
					//tile2.style.background = 'grey';
					//tile2.innerHTML = "";
					
					$('#' + idValori[0]).html("");
					$('#' + idValori[1]).html("");
					$('#' + idValori[0]).css('background', 'grey');
					$('#' + idValori[1]).css('background', 'grey');
					
					valoriAfisate = [];
					idValori = [];
				}
				setTimeout(flip2Back, 700);
			}
		}
	}
}