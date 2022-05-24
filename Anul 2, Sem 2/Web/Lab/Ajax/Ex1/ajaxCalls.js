function getArrivals() {
    var source = document.getElementById('plecare');
    var destination = document.getElementById('sosire');

    var orasPlecare = source.options[source.selectedIndex].value;
    if (orasPlecare == 'Plecare') {
        clear(destination);
        return;
    }

    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState == 4) { // apel AJAX complet
            if (request.status == 200) { // raspuns Ok

                clear(destination);

                var array = eval('(' + request.responseText + ')');

                for (var i = 0; i < array.sosiri.length; i++) {
                    var o = document.createElement('option');
                    o.text = array.sosiri[i];
                    o.value = array.sosiri[i];
                    destination.add(o, null);
                }

                document.getElementById('afiseaza').disabled = false;
            }
            else
                alert('Eroare request.status: ' + request.status);
        }
    };

    request.open('GET', 'cautaDestinatii.php?plecare=' + orasPlecare);
    request.send('');
}

function clear(destination) {
    destination.innerHTML = '';
    document.getElementById('afiseaza').disabled = true;
    document.getElementById('mersulTrenurilor').innerHTML = '';
}

function getTrains() {
    var source = document.getElementById('plecare');
    var destination = document.getElementById('sosire');

    var orasPlecare = source.options[source.selectedIndex].value;
    var orasSosire = destination.options[destination.selectedIndex].value;

    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState == 4) { // apel AJAX complet
            if (request.status == 200) { // raspuns Ok
                var container = document.getElementById('mersulTrenurilor');
                var tabel = '<table class="tabel"><tr><th>Oras Plecare</th><th>Oras Sosire</th><th>Ora Plecare</th></tr>';
                var array = eval('(' + request.responseText + ')');
 for (var i = 0; i < array.trenuri.length; i++) {
                    tabel += '<tr><td>' + array.trenuri[i].plecare + '</td><td>' + array.trenuri[i].sosire + '</td><td>' + array.trenuri[i].ora + '<sup>' + array.trenuri[i].minut + '</sup></td></tr>';
                }
                tabel += '</table>';
                container.innerHTML = tabel;
            }
            else
                alert('Eroare request.status: ' + request.status);
        }
    };

    request.open('GET', 'cautaTrenuri.php?plecare=' + orasPlecare + '&sosire=' + orasSosire);
    request.send('');
}

