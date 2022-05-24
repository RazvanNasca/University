<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Un alt exemplu cu AJAX si PHP</title>
    <link href="style.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="ajaxCalls.js">
    </script>
</head>
<?php
    error_reporting(0);
    require_once("connect.php");
?>
<body>
Statie de plecare:
<select id="plecare" name="plecare" onChange="getArrivals()">
<option selected="selected">Plecare</option>
<?php
    $result = mysql_query("SELECT plecare FROM trenuri group by plecare order by plecare");
    while ($row = mysql_fetch_array($result)) {
        printf("<option value=\"%s\">%s</option>\n", $row['plecare'], $row['plecare']);
    }
?>
</select>
<br><br>
Statie de sosire:
<select id="sosire" name="sosire">
</select>
<br><br>
<input type="button" id="afiseaza" value="Afiseaza trenurile" disabled="disabled" onClick="getTrains()"/>

<br><br>

<div id="mersulTrenurilor">
</div>

</body>
</html>
