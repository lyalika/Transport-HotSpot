import './styles/application.scss';

import mapboxgl from 'mapbox-gl';
import MapboxLanguage from '@mapbox/mapbox-gl-language';

import {MDCTextField} from '@material/textfield/index';
const textField = new MDCTextField(document.querySelector('.mdc-text-field'));

mapboxgl.accessToken = 'pk.eyJ1IjoiaWduaXNmIiwiYSI6ImNqcWF6eGxhejNnc2Q0M3BwZTZhem80aHYifQ.yo2d2xJ4sPIWVg-OQl5oig';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v9',
    center: [23.3219, 42.6977],
    zoom: 14
});

mapboxgl.setRTLTextPlugin('https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-rtl-text/v0.1.0/mapbox-gl-rtl-text.js');
map.addControl(new MapboxLanguage({
  defaultLanguage: 'mul'
}));