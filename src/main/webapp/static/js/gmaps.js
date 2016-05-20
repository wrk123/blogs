var myCenter=new google.maps.LatLng(37.750235, -122.445496);

function initialize()
{
var mapProp = {
  center:myCenter,
  zoom:10,
  mapTypeId:google.maps.MapTypeId.ROADMAP
  };

var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

var marker=new google.maps.Marker({
  position:myCenter,
  });

marker.setMap(map);

var infowindow = new google.maps.InfoWindow({
  content:"You place address"
  });

infowindow.open(map,marker);
}

google.maps.event.addDomListener(window, 'load', initialize);