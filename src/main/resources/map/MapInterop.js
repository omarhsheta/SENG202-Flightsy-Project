let googleMap;

let airportMarks = [];
let polyLines = [];

function InitializeMap() {
    googleMap = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -43.5420, lng: 172.54},
        zoom: 6
    });
    googleMap.setOptions({ minZoom: 2, maxZoom: 19, streetViewControl: false });
}

function ClearMapMarkers() {
    for (let i = 0; i < airportMarks.length; i++) {
        airportMarks[i].setMap(null);
    }

    for (let i = 0; i < polyLines.length; i++) {
        polyLines[i].setMap(null);
    }
}

function PlaceAirportMarkers(airportList) {
    for (let i = 0; i < airportList.length; i++) {
        var mark = new google.maps.Marker( {
            position: airportList[i],
            map: googleMap
        });
        airportMarks.push(mark);
    }
}

function PlaceRouteLines(lineList) {
    var line = new google.maps.Polyline( {
        path: lineList,
        map: googleMap,
        geodesic: true,
        strokeColor: '#ff8b00',
        strokeOpacity: 1.0,
        strokeWeight: 2
    })
    polyLines.push(line);
}