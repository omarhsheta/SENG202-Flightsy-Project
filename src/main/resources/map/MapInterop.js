var googleMap;

var airportMarks = [];

function InitializeMap() {
    googleMap = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -43.5420, lng: 172.54},
        zoom: 6
    });
    googleMap.setOptions({ minZoom: 2, maxZoom: 19, streetViewControl: false });
}

function ClearMapMarkers() {
    for (i = 0; i < airportMarks.length; i++) {
        airportMarks[i].setMap(null);
    }
}

function PlaceAirportMarkers(airportList) {
    airportMarks = [];

    for (var i = 0; i < airportList.length; i++) {
        mark = new google.maps.Marker( {
            position: airportList[i],
            map: googleMap
        });
        airportMarks.push(mark);
    }
}