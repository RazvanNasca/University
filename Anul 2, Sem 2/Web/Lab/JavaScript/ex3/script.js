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
		output += '<div id= "tile_'+ i +' " onclick = "memorare(this,\'' +lista[i]+ '\')"></div>'
	}
	document.getElementById('memory_board').innerHTML = output;
}


function memorare(tile,val){
	if(tile.innerHTML == "" && valoriAfisate.length < 2)
    {
		tile.style.background = '#FFF';
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
					document.getElementById('memory_board').innerHTML = "";
					jocNou();
				}
			}
            else
            {
				function flip2Back()
                {
					var tile1 = document.getElementById(idValori[0]);
					var tile2 = document.getElementById(idValori[1]);
					tile1.style.background='grey';
					tile1.innerHTML = "";
					tile2.style.background = 'grey';
					tile2.innerHTML = "";
					valoriAfisate = [];
					idValori = [];
				}
				setTimeout(flip2Back, 700);
			}
		}
	}
}